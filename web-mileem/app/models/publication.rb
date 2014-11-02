# -*- encoding : utf-8 -*-
# == Schema Information
#
# Table name: publications
#
#  id               :integer          not null, primary key
#  effective_date   :date
#  operation        :string(255)
#  address          :string(255)
#  floor            :integer
#  apartment        :string(255)
#  number_spaces    :integer
#  surface          :integer
#  price            :float(24)
#  expenses         :float(24)
#  antiquity        :integer
#  description      :text
#  additional_info  :text
#  created_at       :datetime
#  updated_at       :datetime
#  currency_id      :integer
#  neighbourhood_id :integer
#  property_type_id :integer
#  user_id          :integer
#  plan_id          :integer
#  lat              :decimal(10, 6)
#  lng              :decimal(10, 6)
#  status           :integer
#  end_date         :date
#

class Publication < ActiveRecord::Base
	
	belongs_to	:neighbourhood
	belongs_to  :currency
	belongs_to  :property_type
	belongs_to  :user
	belongs_to  :plan
	has_many    :uploads
	has_many    :video_uploads

	enum status: [ :finished, :stopped, :available, :republished]

	validates :neighbourhood, presence: true
	validates :plan, presence: true
	validates :price, presence: true, :numericality => { :greater_than_or_equal_to => 0 }
	validates :address, presence: true
	validates :effective_date, presence: true, :on => :create, date: {on_or_after: DateTime.now.change(:hour => 0, :min => 0)}
	validates :antiquity, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :expenses, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :floor, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :number_spaces, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :surface, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	
	validate :user_free_active_publication_limit, :on => :create
	validate :address_inside_bsas

	def user_free_active_publication_limit
		@user = User.find(self.user_id)
		if (not @user.can_have_new_free_publication?) and (self.plan_id == Plan.get_free_plan_id)
      		errors.add(:limite_de_publicaciones_gratuitas_alcanzado, '(5)')
    	end
	end

	# Llamo al geocoder de google para comprobar si existe la direccion
	def address_inside_bsas
		begin
		address_encoded = self.address.gsub(" ","+")

		parameters = {'language' => 'es', 'sensor' => 'false','key' => ENV['GOOGLE_KEY'], "address" => address_encoded}
		uri = URI.parse('https://maps.googleapis.com/maps/api/geocode/json').tap do |uri|
  			uri.query = URI.encode_www_form parameters
		end

		url = uri.to_s
		response = HTTParty.get(url)
		if (response["status"] == "REQUEST_DENIED") or (response["status"] == "ZERO_RESULTS")
      		errors.add(:error_validando_direccion, ', la misma es inválida, vuelva a ingresarla.')
		elsif response["results"][0]["address_components"].size < 6
      		errors.add(:error_validando_direccion, ', la misma está incompleta, debe ser específico con la dirección (incluyendo numeración).')
		else
			in_bs_as = false
			ciudad_bs_as = "Ciudad Autónoma de Buenos Aires"
			response["results"][0]["address_components"].each_with_index do |element,i|
				in_bs_as = true if (element["long_name"] == ciudad_bs_as)
			end

			if not in_bs_as
	      		errors.add(:error_validando_direccion, ", la dirección debe pertenercer a la #{ciudad_bs_as}.")
			end
		end

		rescue Exception => e
			Rails.logger.error("Error validando la dirección serverside")
			Rails.logger.error(e.message)
      		errors.add(:error_validando_direccion, 'Por favor, vuelva a ingresar la dirección.')
		end
	end

	# agrego los nombres de las entidades externas
	def to_json
		result = self.attributes
		result["currency_name"] = (self.currency.nil? ? nil : self.currency.name)
		result["currency_symbol"] = (self.currency.nil? ? nil : self.currency.abreviatura)

		result["property_type"] = (self.property_type.nil? ? nil : self.property_type.name)
		result["neighbourhood_name"] = (self.neighbourhood.nil? ? nil : self.neighbourhood.name)
		
		result["user_phone_number"] =  (self.user.nil? ? nil : self.user.phone_number)
		result["user_email"] = (self.user.nil? ? nil : self.user.email)
		result["plan_priority"] = (self.plan.nil? ? nil : self.plan.priority)

		# Agrego URL absoluta de las imagenes
		result["images"] = []
		self.uploads.each do |u|
			result["images"] << u.upload.to_s
		end

		result["video"] = (self.video_uploads.first.nil? ? nil : self.video_uploads.first.video_url)
		return result
	end

	def to_json_for_index
		return self.to_json

		# Si queremos armar un json propio con otros datos
		# result = {}
		# result[:id] = self.id
		# result[:price] = self.price
		# result[:address] = self.address
		# result[:currency] = self.currency.abreviatura
		# result[:operation] = self.operation
		# result[:neighbourhood] = self.neighbourhood.name
		# return result
	end

	def isActive
		return (self.effective_date <= DateTime.now.strftime("%Y-%m-%d").to_date && self.end_date >= DateTime.now.strftime("%Y-%m-%d").to_date)
	end
	
	def isAvailable
		return (self.isActive && self.available? )
	end

	def get_current_status
		if(self.isActive)
			case self.status
				when "finished" 
					return "Finalizada"
				when "available"
					return "Activa"
				when "republished"
					return "Republicada"
				when "stopped"
					return "Pausada"
			end
		else
			if(self.end_date < DateTime.now)
				return "Vencida"
			else
				return "Creada"	
			end	
		end
	end

  def url_de_pago(plan_id = nil, has_discount = false)
  	plan_id = self.plan_id if plan_id.nil?
  	plan = Plan.find(plan_id)
  	if (has_discount)
  	  # discount = 20%
  	  plan.price = plan.price*0.8
  	end
		data = {
			external_reference: "PUBLICATION-ID-#{self.id}-#{plan_id}",
			payment_methods: {
    			excluded_payment_types: [
		            {
		            	id: "ticket"
		            }
		        ]
			},
			items: [
				{
					id:           "PUBLICATION-ID-#{self.id}-#{plan_id}",
					title:        "Plan #{plan.name}",
					description:  "#{self.address}",
					quantity:     1,
					unit_price:   plan.price,
					currency_id:  "ARS",
					category_id:  self.operation
				}
			],
			payer: {
				name:     (self.user.username.nil? ? self.user.email : self.user.username),
				email:   self.user.email
				},
				back_urls: {
					pending: "#{ENV['url']}/publications/payment_return/#{self.id}/2", #/success",
					success: "#{ENV['url']}/publications/payment_return/#{self.id}/1", #/success",
					failure: "#{ENV['url']}/publications/payment_return/#{self.id}/3" #/failure"
				},
			auto_return: 'approved',
			notification_url: "payment/notification"
		}

		payment = $mp.create_preference(data)
		return payment['sandbox_init_point']
	end
	
	self.per_page = 10

	scope :date_at_gte, lambda { |reference_time|
		where('publications.effective_date >= ?', Date.parse(reference_time).strftime("%Y-%m-%d"))
	}

	scope :date_at_lt, lambda { |reference_time|
  		where('publications.effective_date <= ?', Date.parse(reference_time).strftime("%Y-%m-%d"))
	}

	scope :with_neighbourhood_id, lambda { |neighbourhood_id|
    	where(:neighbourhood_id => [neighbourhood_id])
  	}

	scope :currency_filter_id, lambda { |currency_id|
		if(currency_id != 0)
	    	where(:currency_id => [currency_id])
	    end	
  	}

	scope :price_gte, lambda { |reference_price|
 		where('publications.price  >= ?', reference_price)
	}

	scope :operation_search, lambda { |reference_operation|
		if(reference_operation != "Ambos")
  			where('publications.operation = ?', reference_operation)
  		end	
	}

	filterrific(
  	filter_names: [
	    :date_at_gte,
	    :date_at_lt,
	    :with_neighbourhood_id,
	    :price_gte,
	    :operation_search,
	    :currency_filter_id
	  ]
	)
end

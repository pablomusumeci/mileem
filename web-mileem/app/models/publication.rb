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
#

class Publication < ActiveRecord::Base
	belongs_to	:neighbourhood
	belongs_to  :currency
	belongs_to  :property_type
	belongs_to  :user
	has_many 	:uploads

	validates :neighbourhood, presence: true
	validates :price, presence: true, :numericality => { :greater_than_or_equal_to => 0 }
	validates :address, presence: true
	validates :effective_date, presence: true, date: {on_or_after: DateTime.now.change(:hour => 0, :min => 0)}
	validates :antiquity, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :expenses, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :floor, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :number_spaces, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :surface, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	
	def end_date
	  self.effective_date + 30.days
	  # the amount of days should depend on the plan of the publication
	  # maybe need to substract 1 day if we consider de end_date as included
	end
	
	# agrego los nombres de las entidades externas
	def to_json
		result = self.attributes
		result[:currency_name] = self.currency.name
		result[:currency_symbol] = self.currency.abreviatura

		result[:property_type] = self.property_type.name
		result[:neighbourhood_name] = self.neighbourhood.name
		
		result[:user_phone_number] = self.user.phone_number
		result[:user_email] = self.user.email
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

	self.per_page = 2

	scope :date_at_gte, lambda { |reference_time|
		where('publications.effective_date >= ?', Date.parse(reference_time).strftime("%Y-%m-%d"))
	}

	scope :date_at_lt, lambda { |reference_time|
  		where('publications.effective_date <= ?', Date.parse(reference_time).strftime("%Y-%m-%d"))
	}

	scope :with_neighbourhood_id, lambda { |neighbourhood_id|
    	where(:neighbourhood_id => [neighbourhood_id])
  	}

	scope :price_gte, lambda { |reference_price|
  		where('publications.price >= ?', reference_price)
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
	    :operation_search
	  ]
	)
end




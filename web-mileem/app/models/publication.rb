# -*- encoding : utf-8 -*-
# == Schema Information
#
# Table name: publications
#
#  id              :integer          not null, primary key
#  effective_date  :date
#  operation       :string(255)
#  address         :string(255)
#  floor           :integer
#  apartment       :string(255)
#  number_spaces   :integer
#  surface         :integer
#  price           :float(24)
#  expenses        :float(24)
#  antiquity       :integer
#  description     :text
#  additional_info :text
#  neighbourhood   :neighbourhood
#  property_type   :property_type
#  currency        :currency
#  created_at      :datetime
#  updated_at      :datetime
#

class Publication < ActiveRecord::Base
	belongs_to	:neighbourhood
	belongs_to  :currency
	belongs_to  :property_type
	validates :neighbourhood, presence: true
	validates :price, presence: true, :numericality => { :greater_than_or_equal_to => 0 }
	validates :address, presence: true
	validates :effective_date, presence: true, date: {on_or_after: DateTime.now.change(:hour => 0, :min => 0)}
	validates :antiquity, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :expenses, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :floor, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :number_spaces, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :surface, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	
	# agrego los nombres de las entidades externas
	def to_json
		result = self.attributes
		result[:currency_name] = self.currency.name
		result[:property_type] = self.property_type.name
		result[:neighbourhood_name] = self.neighbourhood.name

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
end


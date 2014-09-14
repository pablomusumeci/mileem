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
	belongs_to   :neighbourhood
	validates :neighbourhood, presence: true
	validates :price, presence: true, :numericality => { :greater_than_or_equal_to => 0 }
	validates :address, presence: true
	validates :effective_date, presence: true, date: {on_or_after: DateTime.now.change(:hour => 0, :min => 0)}
	validates :antiquity, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :expenses, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :floor, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :number_spaces, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	validates :surface, :numericality => { :greater_than_or_equal_to => 0 }, :allow_nil => true
	
end

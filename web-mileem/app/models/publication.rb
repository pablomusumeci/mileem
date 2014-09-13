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
#  created_at      :datetime
#  updated_at      :datetime
#

class Publication < ActiveRecord::Base
	belongs_to :neighbourhood
  validates :price, presence: true, :numericality => true
  validates :effective_date, presence: true, date: {on_or_after: DateTime.now.change(:hour => 0, :min => 0)}
end

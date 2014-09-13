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
  validates :price, presence: true, :numericality => true
  validates :effective_date,
  date: { after_or_equal_to: Proc.new { DateTime.now }, message: "La fecha publicación no puede ser anterior al día de hoy" },
  on: :create
end

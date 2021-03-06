# -*- encoding : utf-8 -*-
# == Schema Information
#
# Table name: plans
#
#  id                    :integer          not null, primary key
#  price                 :float(24)
#  duration              :integer
#  priority              :integer
#  number_images_allowed :integer
#  number_videos_allowed :integer
#  name                  :string(255)
#  created_at            :datetime
#  updated_at            :datetime
#

class Plan < ActiveRecord::Base
  def is_free?
    self.name == "Gratis" # could check by price
  end
  
  def self.get_free_plan_id
    return Plan.where("name = ? ","Gratis").first.id
  end
  
end

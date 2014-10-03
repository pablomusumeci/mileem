# -*- encoding : utf-8 -*-
# == Schema Information
#
# Table name: plans
#
#  id                     :integer          not null, primary key
#  price   				  :float(24)
#  duration               :integer
#  priority               :integer
#  number_images_allowed  :integer
#  number_videos_allowed  :integer 
#  name                   :string(255)
#

class Plan < ActiveRecord::Base
end

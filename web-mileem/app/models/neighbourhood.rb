# == Schema Information
#
# Table name: neighbourhoods
#
#  id         :integer          not null, primary key
#  name       :string(255)
#  created_at :datetime
#  updated_at :datetime
#

class Neighbourhood < ActiveRecord::Base
	has_many :publications
	has_many :near_neighbourhoods
end
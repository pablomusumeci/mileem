# == Schema Information
#
# Table name: currencies
#
#  id          :integer          not null, primary key
#  name        :string(255)
#  abreviatura :string(255)
#  created_at  :datetime
#  updated_at  :datetime
#

class Currency < ActiveRecord::Base
	has_many :publications
end

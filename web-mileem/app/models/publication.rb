class Publication < ActiveRecord::Base
  validates :price, presence: true
  
end

class Publication < ActiveRecord::Base
  validates :price, presence: true, :numericality => true
end

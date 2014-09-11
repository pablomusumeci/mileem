require 'test_helper'

class PublicationTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
  
  def setup
    @publication = Publication.new
  end
  
  def test_empty_creation
    assert_not @publication.save, 'Must not create an empty publication'
  end
  
  def test_price_numeric
    @publication.price = 'cien'
    assert_not @publication.save, 'Validate that price must be numeric'
  end
  
end

# -*- encoding : utf-8 -*-
require 'test_helper'

class PublicationTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
  
  def setup
    @publication = Publication.new
    @publication.price = '3000'
    @publication.effective_date = Date.today
  end
  
  def test_create_publication_ok
    assert @publication.save, 'Validate all fields have values according to its validations'
  end
  
  def test_empty_creation
    @publication = Publication.new
    assert_not @publication.save, 'Must not create an empty publication'
  end
  
  def test_price_required
    @publication.price = ''
    assert_not @publication.save, 'Price must be a required field'
  end
  
  def test_price_numeric
    @publication.price = 'cien'
    assert_not @publication.save, 'Validate that price must be numeric'
  end
  
  # price must be positive
  
  def test_publication_effective_date_on_future
    @publication.effective_date = Date.today.change(year: 2012)
    assert_not @publication.save, 'Validate that effective date is on the future'
  end
  
  # publication date must have date format
  # Validate direction from outside territory (capital) ?
  
end

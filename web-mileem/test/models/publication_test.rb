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
    @publication.address = 'Av Congreso 2420, Buenos Aires, Argentina'
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

  def test_address_required
    @publication.address = ''
    assert_not @publication.save, 'Address must be a required field'
  end
  
  def test_effective_date_required
    @publication.effective_date = ''
    assert_not @publication.save, 'Effective Date must be a required field'
  end

  def test_price_numeric
    @publication.price = 'cien'
    assert_not @publication.save, 'Validate that price must be numeric'
  end
  
  def test_publication_effective_date_on_future
    @publication.effective_date = Date.today.change(year: 2012)
    assert_not @publication.save, 'Validate that effective date is on the future'
  end
  
  def test_antiquity_numeric
    @publication.antiquity = 'tres'
    assert_not @publication.save, 'Validate that antiquity must be numeric'
  end
  
  def test_expenses_numeric
    @publication.expenses = 'mucho'
    assert_not @publication.save, 'Validate that expenses must be numeric'
  end
  
  def test_floor_numeric
    @publication.floor = 'tres'
    assert_not @publication.save, 'Validate that floor must be numeric'
  end
  
  def test_number_spaces_numeric
    @publication.number_spaces = 'dos'
    assert_not @publication.save, 'Validate that number_spaces must be numeric'
  end
  
  def test_surface_numeric
    @publication.surface = 'quinientos mil'
    assert_not @publication.save, 'Validate that surface must be numeric'
  end
  
  
  #direccion fecha y precio son obligatorios
  
  # publication date must have date format
  # Validate direction from outside territory (capital) ?
  
end

class AddPropertyTypeAndCurrencyAndNeighbourhoodToPublications < ActiveRecord::Migration
  def change
  	add_reference :publications, :currency, index: true
  	add_reference :publications, :neighbourhood, index: true
  	add_reference :publications, :property_type, index: true
  end
end

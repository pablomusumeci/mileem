class AddPlanToPublications < ActiveRecord::Migration
  def change
  	  	add_reference :publications, :plan, index: true
  end
end

class AddCoordinatesToPublication < ActiveRecord::Migration
  def change
  	add_column :publications, :lat, :decimal, {:precision=>10, :scale=>6}
	add_column :publications, :lng, :decimal, {:precision=>10, :scale=>6}
  end
end

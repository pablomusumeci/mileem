class CreateNearNeighbourhoods < ActiveRecord::Migration
  def change
    create_table :near_neighbourhoods do |t|
      t.integer :neighbourhood_id_1
      t.integer :neighbourhood_id_2
      t.timestamps
    end
  end
end

class CreatePlans < ActiveRecord::Migration
  def change
    create_table :plans do |t|
      t.float   :price
      t.integer :duration
      t.integer :priority
      t.integer :number_images_allowed
      t.integer :number_videos_allowed
      t.string  :name 
      t.timestamps
    end
  end
end

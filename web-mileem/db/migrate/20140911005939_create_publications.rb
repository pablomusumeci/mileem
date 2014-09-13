# -*- encoding : utf-8 -*-
class CreatePublications < ActiveRecord::Migration
  def change
    create_table :publications do |t|
      t.date :effective_date
      t.string :operation
      t.string :address
      t.integer :floor
      t.string :apartment
      t.integer :number_spaces
      t.integer :surface
      t.float :price
      t.float :expenses
      t.integer :antiquity
      t.text :description
      t.text :additional_info

      t.timestamps
    end
  end
end

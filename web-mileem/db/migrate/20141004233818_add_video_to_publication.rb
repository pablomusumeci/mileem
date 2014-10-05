class AddVideoToPublication < ActiveRecord::Migration
  def change
    add_column :publications, :video, :string
  end
end

class RemoveVideoFromPublication < ActiveRecord::Migration
  def change
    remove_column :publications, :video, :string
  end
end

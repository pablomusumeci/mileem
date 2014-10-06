class CreateVideoUploads < ActiveRecord::Migration
  def change
    create_table :video_uploads do |t|
      t.string :video
      t.belongs_to :publication
      t.timestamps
    end
  end
end

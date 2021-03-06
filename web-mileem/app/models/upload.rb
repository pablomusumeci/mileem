# == Schema Information
#
# Table name: uploads
#
#  id                  :integer          not null, primary key
#  upload_file_name    :string(255)
#  upload_content_type :string(255)
#  upload_file_size    :integer
#  upload_updated_at   :datetime
#  publication_id      :integer
#

class Upload < ActiveRecord::Base

  has_attached_file :upload
#  validates_attachment_content_type :upload, :content_type => ['image/png','image/jpg','image/jpeg']
  do_not_validate_attachment_file_type :upload	
  include Rails.application.routes.url_helpers

  def to_jq_upload
    {
      "name" => read_attribute(:upload_file_name),
      "size" => read_attribute(:upload_file_size),
      "url" => upload.url(:original),
      "delete_url" => upload_path(self),
      "delete_type" => "DELETE"
    }
  end

end

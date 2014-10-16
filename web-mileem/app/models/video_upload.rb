# == Schema Information
#
# Table name: video_uploads
#
#  id             :integer          not null, primary key
#  video          :string(255)
#  publication_id :integer
#  created_at     :datetime
#  updated_at     :datetime
#

class VideoUpload < ActiveRecord::Base
  mount_uploader :video, VideoUploader
  
end

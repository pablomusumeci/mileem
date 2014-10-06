class VideoUpload < ActiveRecord::Base
  mount_uploader :video, VideoUploader
  
end

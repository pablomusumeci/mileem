class VideoUploadsController < ApplicationController
  def create
    @publication = Publication.find(params[:publication_id])
    # check if has permission
    authorize @publication, :update?
    @videoUpload = VideoUpload.new(video_upload_params)
    @videoUpload.publication_id = @publication.id
    
    respond_to do |format|
      if @videoUpload.save
        format.html { redirect_to @publication, notice: 'El video fue guardado exitosamente.' }
        format.json { render :show, status: :created, location: @publication }
      else
        format.html { render :new }
        format.json { render json: @videoUpload.errors, status: :unprocessable_entity }
      end
    end
  end


 # DELETE /uploads/1
  # DELETE /uploads/1.json
  #def destroy
  #  @upload = Upload.find(params[:id])
  #  publication_id = @upload.publication_id
  #  publication_uploads_url = "/publications/#{publication_id}/uploads"
  #  @upload.destroy
  #  respond_to do |format|
  #    format.html { redirect_to publication_uploads_url }
  #    format.json { head :no_content }
  #  end
  #end

  def video_upload_params
    params.require(:video_upload).permit(:publication_id, :video)
  end
end

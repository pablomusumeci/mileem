# -*- encoding : utf-8 -*-
class UploadsController < ApplicationController

  # POST /uploads
  # POST /uploads.json
  def create
    @publication = Publication.find(params[:publication_id])
    # check if has permission
    authorize @publication, :update?
    @upload = Upload.new(params[:upload].permit(:upload))
    respond_to do |format|
      if @upload.save
        @publication.uploads << @upload 
        format.html {
          render :json => [@upload.to_jq_upload].to_json,
          :content_type => 'text/html',
          :layout => false
        }
        format.json { render json: {files: [@upload.to_jq_upload]}, status: :created, location: @upload }
      else
        print "errores" + @upload.errors.to_json
        format.html { render action: "uploads", controller: "publications"}
        format.json { render json: @upload.errors, status: :unprocessable_entity }
      end
    end
  end

 # DELETE /uploads/1
  # DELETE /uploads/1.json
  def destroy
    @upload = Upload.find(params[:id])
    @upload.destroy

    respond_to do |format|
      format.html { redirect_to uploads_url }
      format.json { head :no_content }
    end
  end

  def upload_params
    params.require(:upload).permit(:publication_id, :upload => [:upload])
  end
end

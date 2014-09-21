# -*- encoding : utf-8 -*-
class UploadsController < ApplicationController

  # GET /uploads
  # GET /uploads.json
  def index
    @course = Course.find(params[:id])
    @uploads = @course.uploads
    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @uploads.map{|upload| upload.to_jq_upload } }
    end
  end

  # GET /uploads/1
  # GET /uploads/1.json
  def show
    @upload = Upload.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @upload }
    end
  end

  # GET /uploads/new
  # GET /uploads/new.json
  def new
    @upload = Upload.new
    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @upload }
    end
  end

  # GET /uploads/1/edit
  def edit
    @upload = Upload.find(params[:id])
  end
  
  # POST /uploads
  # POST /uploads.json
  def create
    @publication = Publication.find(params[:publication_id])
    @upload = Upload.new(params[:upload])
    @upload.publication_id = @publication.id
    respond_to do |format|
      if @upload.save
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

  def upload_params
      params.require(:upload).permit(:upload, :publication_id)
    end
end

# -*- encoding : utf-8 -*-
class PublicationsController < ApplicationController
  before_action :set_publication, only: [:show, :edit, :update, :destroy]

  # GET /publications
  # GET /publications.json
  def index
    @publications = Publication.all
    respond_to do |format|
      format.html { render :index}
      format.json { render :json => @publications.to_a.map{ |p| p.to_json_for_index }.to_json }
    end
  end

  # GET /publications/1
  # GET /publications/1.json
  def show
    respond_to do |format|
      format.html { render :show}
      format.json { render :json => @publication.to_json}
    end
  end

  # GET /publications/new
  def new
    @publication = Publication.new
    pesos = Currency.where(name: "Pesos").first
    @publication.currency_id = pesos.id
    @publication.effective_date = Date.today.strftime("%d/%m/%Y")
  end  

  # GET /publications/1/edit
  def edit
  end

  # POST /publications
  # POST /publications.json
  def create
    @publication = Publication.new(publication_params)

    respond_to do |format|
      if @publication.save
        format.html { redirect_to @publication, notice: 'Publication was successfully created.' }
        format.json { render :show, status: :created, location: @publication }
      else
        format.html { render :new }
        format.json { render json: @publication.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /publications/1
  # PATCH/PUT /publications/1.json
  def update
    respond_to do |format|
      if @publication.update(publication_params)
        format.html { redirect_to @publication, notice: 'Publication was successfully updated.' }
        format.json { render :show, status: :ok, location: @publication }
      else
        format.html { render :edit }
        format.json { render json: @publication.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /publications/1
  # DELETE /publications/1.json
  def destroy
    @publication.destroy
    respond_to do |format|
      format.html { redirect_to publications_url, notice: 'Publication was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_publication
      @publication = Publication.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def publication_params
      params.require(:publication).permit(:effective_date, :operation, :address, :floor, :apartment, :number_spaces, :surface, :price, :expenses, :antiquity, :description, :additional_info, :neighbourhood_id, :currency_id,:property_type_id)
    end
end

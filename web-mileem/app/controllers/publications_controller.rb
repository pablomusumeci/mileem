# -*- encoding : utf-8 -*-
class PublicationsController < ApplicationController
  before_action :set_publication, only: [:show, :edit, :update, :destroy]
  before_action :authorize_update, only: [:edit, :update, :destroy] #perimsos del pundit
  before_filter :authenticate_user!, except: [:search, :show] # permisos del devise
  skip_before_action :verify_authenticity_token

  # GET /publications
  # GET /publications.json
  def index
    @filterrific = Filterrific.new(Publication, params[:filterrific])
    @publications = Publication.filterrific_find(@filterrific).paginate(page: params[:page])
#    @publications = current_user.publications
    respond_to do |format|
      format.html { render :index}
      format.js  
      format.json { render :json => @publications.to_a.map{ |p| p.to_json_for_index }.to_json }
    end
  end

  def reset_filterrific
    session[:filterrific_publications] = nil
    redirect_to :action => :index
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
    @publication.user_id = current_user.id
    respond_to do |format|
      if @publication.save
        format.html { redirect_to @publication, notice: 'La publicación fue creada exitosamente.' }
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
        format.html { redirect_to @publication, notice: 'La publicación fue actualizada exitosamente.' }
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
      format.html { redirect_to publications_url, notice: 'La publicación fue destruida exitosamente.' }
      format.json { head :no_content }
    end
  end

  # GET /publications/search.json?param1=x&param2=y...
  def search
    @publications = Publication.all.to_a

    if not params[:neighbourhood_name].nil?
      barrio = params[:neighbourhood_name]
      @publications.select!{ |p| p.neighbourhood.name == barrio} if barrio != "Todos"
    end

    # precio maximo
    if not params[:operation].nil?
      @publications.select!{ |p| p.operation == params[:operation] }
    end

    if not params[:property_name].nil?
      tipo = params[:property_name]
      @publications.select!{ |p| p.property_type.name == params[:property_name]} if tipo != "Todos"
    end

    # precio minimo
    if not params[:min_price].nil?
      @publications.select!{ |p| p.price >= params[:min_price].to_i }
    end

    # precio maximo
    if not params[:max_price].nil?
      @publications.select!{ |p| p.price <= params[:max_price].to_i }
    end

    # antiguedad minima
    if not params[:min_antiquity].nil?
      @publications.select!{ |p| p.antiquity >= params[:min_antiquity].to_i }
    end

    # antiguedad maxima
    if not params[:max_antiquity].nil?
      @publications.select!{ |p| p.antiquity <= params[:max_antiquity].to_i }
    end

    # superficie minima
    if not params[:min_surface].nil?
      @publications.select!{ |p| p.surface >= params[:min_surface].to_i }
    end

    # superficie maxima
    if not params[:max_surface].nil?
      @publications.select!{ |p| p.surface <= params[:max_surface].to_i }
    end

    # expensas minima
    if not params[:min_expenses].nil?
      @publications.select!{ |p| p.expenses >= params[:min_expenses].to_i }
    end

    # expensas maxima
    if not params[:max_expenses].nil?
      @publications.select!{ |p| p.expenses <= params[:max_expenses].to_i }
    end

    # abreviatura de moneda
    if not params[:currency].nil?
      @publications.select!{ |p| p.currency.abreviatura == params[:currency] }
    end

    # ambientes
    if not params[:number_spaces].nil?
      @publications.select!{ |p| p.number_spaces == params[:number_spaces].to_i }
    end

    respond_to do |format|
      format.json { render :json => @publications.to_a.map{ |p| p.to_json_for_index }.to_json }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_publication
      @publication = Publication.find(params[:id])
    end

    def authorize_update
      authorize @publication, :update?
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def publication_params
      params.require(:publication).permit(:effective_date, :operation, :address, :floor, :apartment, :number_spaces, :surface, :price, :expenses, :antiquity, :description, :additional_info, :neighbourhood_id, :currency_id,:property_type_id)
    end
end

# -*- encoding : utf-8 -*-
class PublicationsController < ApplicationController
  before_action :set_publication, only: [:show, :edit, :update, :destroy, :uploads, :jsonifier, :stop, :finish, :active ]
  before_action :authorize_update, only: [:edit, :update, :destroy, :uploads] #permisos del pundit
  before_action :authorize_read, only: [:show]
  before_filter :authenticate_user!, except: [:search, :jsonifier] # permisos del devise
  skip_before_action :verify_authenticity_token

  @@currency_conversion = {"USD" => {"USD" => 1, "$" => 10}, "$" => {"USD" => 0.1, "$" => 1}}
  # GET /publications
  # GET /publications.json
  def index
    @filterrific = Filterrific.new(Publication, params[:filterrific])
    number_status = Publication.statuses[:republished]
    @publications = Publication.filterrific_find(@filterrific).
    where("user_id = ? AND status <> ?", current_user.id, number_status).paginate(page: params[:page])

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

  def uploads
    @uploads = @publication.uploads
    respond_to do |format|
      format.html { render :upload}
      format.json { render json: @uploads.map{|upload| upload.to_jq_upload } }
    end
  end  
    
  # GET /publications/1
  # GET /publications/1.json
  def show
    render :show
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

  # GET /publications/republicate
  def republicate
    old_publication = Publication.find(params[:id])
    @publication_data = old_publication.attributes
    # Si republica antes de que pase un mes del vencimiento/finalizacion de la publicacion vieja
    @has_discount = (Date.today < old_publication.end_date + 1.month)
    # Limpio campos de la publicacion vieja
    ["id", "created_at", "updated_at", "status", "end_date"].each { |k| @publication_data.delete k }
    @publication = Publication.new(@publication_data)
    pesos = Currency.where(name: "Pesos").first
    @publication.effective_date = Date.today.strftime("%d/%m/%Y")
  end

  # POST /publications/republicate
  def save_republicate
    @publication = Publication.new(publication_params)    
    @publication.user_id = current_user.id
    @publication.end_date = @publication.effective_date + @publication.plan.duration.months
    respond_to do |format|
      if @publication.save
        @publication.available!
        # Como mucho, traigo el limite del nuevo plan... Extasis!
        Upload.where(publication_id: params[:old_publication_id]).
        limit(@publication.plan.number_images_allowed ).each do |upload_old_publication|
           # Le asigno el ID de la publicacion nueva
           upload_old_publication.publication_id = @publication.id
           upload_old_publication.save
        end

        # Si el nuevo plan permite videos
        if @publication.plan.number_videos_allowed > 0
          VideoUpload.where(publication_id: params[:old_publication_id]).each do |video_old_publication|
            video_old_publication.publication_id = @publication.id
            video_old_publication.save
          end
        end
        old_publication = Publication.find(params[:old_publication_id])
        old_publication.republished!  
        if (old_publication.plan.priority < @publication.plan.priority)
          flash["warning"] = 'Al obtener un plan inferior al actual se seleccionaron las primeras 
          imágenes y video que cumplen con la cantidad permitida por el nuevo plan.'
          format.html { redirect_to @publication }
          format.json { render :show, status: :created, location: @publication }
        else
          format.html { redirect_to @publication, notice: 'La publicación fue republicada exitosamente.' }
          format.json { render :show, status: :created, location: @publication }
        end
      else
        format.html { render :action=>'republicate', :id=> params[:old_publication_id] }
        format.json { render json: @publication.errors, status: :unprocessable_entity }
      end
    end
  end

  # POST /publications
  # POST /publications.json
  def create
    @publication = Publication.new(publication_params)
    @publication.user_id = current_user.id
    @publication.end_date = @publication.effective_date + @publication.plan.duration.months
    respond_to do |format|
      if @publication.save
        @publication.available!
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
      oldPlan = Publication.find(@publication.id).plan
      newPlan = Plan.find(publication_params["plan_id"])
      #Solo se puede hacer upgrade 
      if(newPlan.priority > oldPlan.priority)
        flash[:error] = "No se puede obtener un plan inferior al actual"
        render :edit 
        return
      end  
      if(!@publication.isActive)
        @publication.end_date = publication_params["effective_date"].to_date + newPlan.duration.months
      else
         @publication.end_date = Date.today + newPlan.duration.months
      end  
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

  def stop 
    if(@publication.isActive && @publication.available?)
      @publication.stopped!
      redirect_to publications_path
    end     
  end  

  def active
    if(@publication.isActive && @publication.stopped?)
      @publication.available!
      redirect_to publications_path
    end    
  end 

  def finish
    if(@publication.isActive && @publication.available?)
      @publication.end_date = Date.today
      @publication.finished!
      @publication.save
      redirect_to publications_path
    end    
  end 

  def jsonifier
    respond_to do |format|
      format.json { render :json => @publication.to_json}
    end
  end

  # GET /publications/search.json?param1=x&param2=y...
  def search
    @publications = Publication.all.to_a.map!{|p| p.to_json}
    Rails.logger.info "Publicaciones total: #{@publications.size}"
    @publications.select!{ |p| Publication.find(p["id"]).isAvailable }
    Rails.logger.info "Publicaciones activas: #{@publications.size}"

    if not params[:neighbourhood_name].nil? and (params[:neighbourhood_name].size > 0)
      barrios = params[:neighbourhood_name].split(",")
      @publications.select!{ |p| barrios.include?(p["neighbourhood_name"]) }
    end

    # Pesos como moneda default para el normalized price
    params[:currency] = "$" if params[:currency].nil?

    if not params[:currency].nil?
      @publications.each do |p|
        factor = @@currency_conversion[p["currency_symbol"]][params[:currency]]
        p["normalized_price"] = p["price"] * factor
        p["normalized_currency"] = params[:currency]
      end
    end
    # precio minimo
    if not params[:min_price].nil?
      @publications.select!{ |p| p["normalized_price"] >= params[:min_price].to_i }
    end

    # precio maximo
    if not params[:max_price].nil?
      @publications.select!{ |p| p["normalized_price"] <= params[:max_price].to_i }
    end
    
    # tipo de operacion
    if not params[:operation].nil?
      @publications.select!{ |p| p["operation"] == params[:operation] }
    end

    if not params[:property_name].nil?
      tipo = params[:property_name]
      @publications.reject!{ |p|  p["property_type"].nil? }
      @publications.select!{ |p| p["property_type"] == params[:property_name]} if tipo != "Todos"
    end

    # antiguedad minima
    if not params[:min_antiquity].nil?
      @publications.reject!{ |p|  p["antiquity"].nil? }
      @publications.select!{ |p| p["antiquity"] >= params[:min_antiquity].to_i}
    end

    # antiguedad maxima
    if not params[:max_antiquity].nil?
      @publications.reject!{ |p|  p["antiquity"].nil? }
      @publications.select!{ |p| p["antiquity"] <= params[:max_antiquity].to_i }
    end

    # superficie minima
    if not params[:min_surface].nil?
      @publications.reject!{ |p|  p["surface"].nil? }
      @publications.select!{ |p| p["surface"] >= params[:min_surface].to_i }
    end

    # superficie maxima
    if not params[:max_surface].nil?
      @publications.reject!{ |p|  p["surface"].nil? }
      @publications.select!{ |p| p["surface"] <= params[:max_surface].to_i }
    end

    # expensas minima
    if not params[:min_expenses].nil?
      @publications.reject!{ |p|  p["expenses"].nil? }
      @publications.select!{ |p| p["expenses"] >= params[:min_expenses].to_i }
    end

    # expensas maxima
    if not params[:max_expenses].nil?
      @publications.reject!{ |p|  p["expenses"].nil? }
      @publications.select!{ |p| p["expenses"] <= params[:max_expenses].to_i }
    end

    # ambientes
    if not params[:number_spaces].nil?
      @publications.reject!{ |p|  p["number_spaces"].nil? }
      @publications.select!{ |p| p["number_spaces"] == params[:number_spaces].to_i }
    end

    # ordeno por prioridad por default
    @publications.sort_by!{ |p|  p["plan_priority"] }

    # ordeno por precio y prioridad
    if not params[:sort_by].nil?
      order = 1 
      order = order * -1 if (not params[:order].nil?) and (params[:order] == "des")
      if params[:sort_by] == "price"
        @publications.sort! { |a, b| [a["normalized_price"] * order, a['plan_priority']] <=> [b["normalized_price"] * order, b['plan_priority']] }
      end 
    end

    puts "Resultado busqueda: #{@publications.size}"
    respond_to do |format|
      format.json { render :json => @publications.to_json }
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

    def authorize_read
      authorize @publication, :read?
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def publication_params
      params.require(:publication).permit(:effective_date, :operation, :address, :floor, :apartment, :number_spaces, :surface, :price, :expenses, :antiquity, :description, :additional_info, :neighbourhood_id, :currency_id,:property_type_id, :uploads, :plan_id, :lat, :lng, :end_date)
    end
end

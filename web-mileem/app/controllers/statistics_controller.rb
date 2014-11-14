class StatisticsController < ApplicationController
  $conversiones = {"USD" => {"USD" => 1, "$" => 10}, "$" => {"USD" => 0.1, "$" => 1}}
  def spaces_distribution
  	publications = Publication.select("number_spaces, COUNT(1) as quantity").group(:number_spaces)
  	result = []
  	publications.each do |p|
  		result << {"number_spaces" => p.number_spaces, "quantity" => p.quantity}
  	end
    respond_to do |format|
      format.json { render :json => result.to_json }
    end
  end

  def near_prices
  	barrio_parametro = Neighbourhood.find(params[:neighbourhood_id])
  	moneda_deseada = params[:currency]
  	moneda_deseada = "$" if moneda_deseada.nil?

  	ids_vecinos = NearNeighbourhood.where(neighbourhood_id_1: barrio_parametro.id).pluck(:neighbourhood_id_2)

  	# Quiero tambien el promedio del barrio pasado como parametro
  	ids_vecinos << barrio_parametro.id 

  	# Traigo publicaciones activas de los barrios vecinos, del tipo pasado por parametro
  	query = Publication.where(neighbourhood_id: ids_vecinos).where(operation: params[:operation]).select{|p| (p.isAvailable) and (p.payment_status == "Realizado")}
  	resultados_parciales = {}

  	#Para cada barrio vecino
  	ids_vecinos.each do |barrio|
  		# Me quedo con sus publicaciones activas
  		publications_from_this_neighbourhood = query.select{|p| p.neighbourhood_id == barrio}
  		if not publications_from_this_neighbourhood.empty? 
  			total = 0
	  		publications_from_this_neighbourhood.each do |p|
	  			# Convierto moneda a pesos
	  			factor = $conversiones[p.currency.abreviatura][moneda_deseada]
	  			# Sumo el precio en pesos de esta publicacion
	  			total += (p.price * factor) / p.surface if (p.surface > 0)
	  		end
	  		resultados_parciales[barrio] = (total.to_f / publications_from_this_neighbourhood.size).round(2) # 2 decimales
  		end
  	end

  	resultado = []

  	# Los barrios vecinos que no tienen publicaciones no se incluyen
  	resultados_parciales.each do |k,v|
  		resultado << {"neighbourhood_name" => Neighbourhood.find(k).name, "average" => v, "currency" => moneda_deseada}
  	end

	respond_to do |format|
		format.json { render :json => resultado.to_json }
	end
  end

end

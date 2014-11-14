require 'test_helper'

class StatisticsControllerTest < ActionController::TestCase

  test "should get right average" do
  	operacion = "Alquiler"
    get :near_prices, :format => :json, "neighbourhood_id" => 5, "operation" => operacion
    respuesta = JSON.parse(response.body)
    assert respuesta.size == 2, "Valido cantidad de resultados de las estadisticas sobre precios"
    assert respuesta.include?({"neighbourhood_name"=>"Palermo", "average"=>64.31, "currency"=>"$"}),"Valido estadisticas de Palermo en #{operacion}"
    assert respuesta.include?({"neighbourhood_name"=>"Belgrano", "average"=>41.67, "currency"=>"$"}),"Valido estadisticas de Belgrano en #{operacion}"
  end

   test "should get right average in another curency than pesos" do
  	operacion = "Alquiler"
    get :near_prices, :format => :json, "neighbourhood_id" => 5, "operation" => operacion, "currency" => "USD"
    respuesta = JSON.parse(response.body)
    assert respuesta.size == 2, "Valido cantidad de resultados de las estadisticas sobre precios"
    assert respuesta.include?({"neighbourhood_name"=>"Palermo", "average"=>6.43, "currency"=>"USD"}),"Valido estadisticas de Palermo en #{operacion} en USD"
    assert respuesta.include?({"neighbourhood_name"=>"Belgrano", "average"=>4.17, "currency"=>"USD"}),"Valido estadisticas de Belgrano en #{operacion} en USD"
  end

  test "should get right spaces distribution" do
    get :spaces_distribution, :format => :json
    respuesta = JSON.parse(response.body)
    assert respuesta.size == 4, "Valido cantidad de resultados de las estadisticas sobre ambientes"
    assert respuesta.include?({"number_spaces" => 1, "quantity" => 2}),"Valido cantidad de publicaciones de 1 ambiente"
    assert respuesta.include?({"number_spaces" => 2, "quantity" => 1}),"Valido cantidad de publicaciones de 2 ambientes"
    assert respuesta.include?({"number_spaces" => 3, "quantity" => 1}),"Valido cantidad de publicaciones de 3 ambientes"
    assert respuesta.include?({"number_spaces" => 4, "quantity" => 1}),"Valido cantidad de publicaciones de 4 ambientes"

  end
end
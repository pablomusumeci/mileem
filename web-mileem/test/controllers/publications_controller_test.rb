# -*- encoding : utf-8 -*-
require 'test_helper'

class PublicationsControllerTest < ActionController::TestCase
  setup do

    @publication = publications(:one)
    @publication.effective_date = Date.today
    
    @user = users(:one)
    sign_in @user
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:publications)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create publication" do
    assert_difference('Publication.count') do
      post :create, publication: { additional_info: @publication.additional_info, address: @publication.address, antiquity: @publication.antiquity, apartment: @publication.apartment, description: @publication.description, effective_date: @publication.effective_date, expenses: @publication.expenses, floor: @publication.floor, number_spaces: @publication.number_spaces, operation: @publication.operation, price: @publication.price, surface: @publication.surface, currency_id: 2, property_type_id: 1, neighbourhood_id: 1, user_id: 1, plan_id: 1 }
    end
  
    assert_redirected_to publication_path(assigns(:publication))
  end

  test "should show publication" do
    get :show, id: @publication
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @publication
    assert_response :success
  end

  test "should update publication" do
    patch :update, id: @publication, publication: { additional_info: @publication.additional_info, address: @publication.address, antiquity: @publication.antiquity, apartment: @publication.apartment, description: @publication.description, effective_date: @publication.effective_date, expenses: @publication.expenses, plan_id: @publication.plan_id, floor: @publication.floor, number_spaces: @publication.number_spaces, operation: @publication.operation, price: @publication.price, surface: @publication.surface }
    assert_redirected_to publication_path(assigns(:publication))
  end

  test "should destroy publication" do
    assert_difference('Publication.count', -1) do
      delete :destroy, id: @publication
    end

    assert_redirected_to publications_path
  end

  test "should search publication" do
    get :search,  :format => :json
    respuesta = JSON.parse(response.body)
    # Modificar segun cantidad de publicaciones en el yml del fixture!
    assert respuesta.size == 5, "Valido cantidad de resultados"
  end

  test "should search publication in neighbourhood" do
    get :search, :format => :json, "neighbourhood_name" => "Palermo"
    respuesta = JSON.parse(response.body)
    assert respuesta.size == 2, "Valido cantidad de resultados en Palermo"
    respuesta.each do |p|
      assert p["neighbourhood_name"] == "Palermo", "Valido resultados en Palermo"
    end

    get :search, :format => :json, "neighbourhood_name" => "Belgrano"
    respuesta = JSON.parse(response.body)
    assert respuesta.size == 3,"Valido cantidad de resultados en Belgrano"

    respuesta.each do |p|
      assert p["neighbourhood_name"] == "Belgrano", "Valido resultados en Belgrano"
    end
  end

  test "should search publication with price range" do
    get :search, :format => :json, "min_price" => 2500, "max_price" => 6900
    respuesta = JSON.parse(response.body)
    assert respuesta.size == 2, "Valido cantidad de resultados buscando por precio"
    respuesta.each do |p|
      @condition = ((p["price"] >= 2500) && (p["price"] <= 6900)) 
      assert @condition
    end
  end
end

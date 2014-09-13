# -*- encoding : utf-8 -*-
require 'test_helper'

class PublicationsControllerTest < ActionController::TestCase
  setup do
    @publication = publications(:one)
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
      post :create, publication: { additional_info: @publication.additional_info, address: @publication.address, antiquity: @publication.antiquity, apartment: @publication.apartment, description: @publication.description, effective_date: Date.today, expenses: @publication.expenses, floor: @publication.floor, number_spaces: @publication.number_spaces, operation: @publication.operation, price: @publication.price, surface: @publication.surface }
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
    patch :update, id: @publication, publication: { additional_info: @publication.additional_info, address: @publication.address, antiquity: @publication.antiquity, apartment: @publication.apartment, description: @publication.description, effective_date: @publication.effective_date, expenses: @publication.expenses, floor: @publication.floor, number_spaces: @publication.number_spaces, operation: @publication.operation, price: @publication.price, surface: @publication.surface }
    assert_redirected_to publication_path(assigns(:publication))
  end

  test "should destroy publication" do
    assert_difference('Publication.count', -1) do
      delete :destroy, id: @publication
    end

    assert_redirected_to publications_path
  end
end

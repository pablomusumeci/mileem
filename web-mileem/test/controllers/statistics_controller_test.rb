require 'test_helper'

class StatisticsControllerTest < ActionController::TestCase
  test "should get spaces_distribution" do
    get :spaces_distribution
    assert_response :success
  end

  test "should get near_prices" do
    get :near_prices
    assert_response :success
  end

end

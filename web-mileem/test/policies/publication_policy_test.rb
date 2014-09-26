require 'test_helper'

class PublicationPolicyTest < ActionController::TestCase

  setup do
    @publication = publications(:one)
    @user = users(:one)
    sign_in @user
  end

  def test_update
  end

  def test_can_delete
    @publication.effective_date = Date.tomorrow
    @policy = PublicationPolicy.new(@user, @publication)
    assert @policy.delete?, 'Must allow to delete a publication with Effective Date after today'
  end
  
  def test_cant_delete_from_yesterday
    @publication.effective_date = Date.yesterday
    @policy = PublicationPolicy.new(@user, @publication)
    assert_not @policy.delete?, 'Must not allow to delete a publication with Effective Date previous to today'
  end
  
  def test_cant_delete_from_today
    @publication.effective_date = Date.today
    @policy = PublicationPolicy.new(@user, @publication)
    assert_not @policy.delete?, 'Must not allow to delete a publication with Effective Date same as today'
  end
  
end

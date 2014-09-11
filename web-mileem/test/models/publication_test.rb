require 'test_helper'

class PublicationTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
  
  def test_empty_creation
    publication = Publication.new
    assert_not publication.save, 'Must not create an empty publication'
  end
  
end

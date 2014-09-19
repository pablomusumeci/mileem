require 'test_helper'

class UserTest < ActiveSupport::TestCase
	def setup
		@user = User.new
		@user.email = 'test@user.com'
		@user.password = 'password'
	end

	def test_create_user_ok
		assert @user.save, 'Validate all fields have values according to its validations'
	end

	def test_email_required
		@user.email = ''
		assert_not @user.save, 'Validate email not blank'
	end

	def test_password_required
		@user.password = ''
		assert_not @user.save, 'Validate password not blank'
	end

	def test_password_length
		@user.password = '12345'
		assert_not @user.save, 'Validate password 6 char long or more'
	end
end

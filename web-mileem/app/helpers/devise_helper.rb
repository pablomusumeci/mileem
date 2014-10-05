# -*- encoding : utf-8 -*-
module DeviseHelper
  def devise_error_messages!
  	translations = {"Email has already been taken" => "Ya existe un usuario registrado con ese email", "Password confirmation doesn't match Password" => "La contraseña no coincide con la confirmación."}
    return nil if resource.errors.empty?
    errors = []
    resource.errors.full_messages.each do |e|
    	if translations.has_key?(e)
      		errors << translations[e] 
      	else
      		errors << e
      	end
    end 
    
    flash[:errors] = errors
  end
end
# -*- encoding : utf-8 -*-
module DeviseHelper
  def devise_error_messages!
  	translations = {"Email has already been taken" => "Ya existe un usuario registrado con ese email, por favor elija otro.", 
      "Password confirmation doesn't match Password" => "La contraseña no coincide con la confirmación.",
      "Current password is invalid" => "La contraseña actual ingresada es inválida.",
      "Current password can't be blank" => "Debe completar el campo 'Contraseña actual'",
      "Password can't be blank" => "La contraseña nueva no puede ser vacía."}
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
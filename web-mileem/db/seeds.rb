#encoding: utf-8

Neighbourhood.create(:name => "Agronomía")
Neighbourhood.create(:name => "Almagro")
Neighbourhood.create(:name => "Balvanera")
Neighbourhood.create(:name => "Barracas")
Neighbourhood.create(:name => "Belgrano")
Neighbourhood.create(:name => "Boedo")
Neighbourhood.create(:name => "Caballito")
Neighbourhood.create(:name => "Chacarita")
Neighbourhood.create(:name => "Coghlan")
Neighbourhood.create(:name => "Colegiales")
Neighbourhood.create(:name => "Constitución")
Neighbourhood.create(:name => "Flores")
Neighbourhood.create(:name => "Floresta")
Neighbourhood.create(:name => "La Boca")
Neighbourhood.create(:name => "La Paternal")
Neighbourhood.create(:name => "Liniers")
Neighbourhood.create(:name => "Mataderos")
Neighbourhood.create(:name => "Monte Castro")
Neighbourhood.create(:name => "Monserrat")
Neighbourhood.create(:name => "Nueva Pompeya")
Neighbourhood.create(:name => "Núñez")
Neighbourhood.create(:name => "Palermo")
Neighbourhood.create(:name => "Parque Avellaneda")
Neighbourhood.create(:name => "Parque Chacabuco")
Neighbourhood.create(:name => "Parque Chas")
Neighbourhood.create(:name => "Parque Patricios")
Neighbourhood.create(:name => "Puerto Madero")
Neighbourhood.create(:name => "Recoleta")
Neighbourhood.create(:name => "Retiro")
Neighbourhood.create(:name => "Saavedra")
Neighbourhood.create(:name => "San Cristóbal")
Neighbourhood.create(:name => "San Nicolás")
Neighbourhood.create(:name => "San Telmo")
Neighbourhood.create(:name => "Vélez Sársfield")
Neighbourhood.create(:name => "Versalles")
Neighbourhood.create(:name => "Villa Crespo")
Neighbourhood.create(:name => "Villa del Parque")
Neighbourhood.create(:name => "Villa Devoto")
Neighbourhood.create(:name => "Villa General Mitre")
Neighbourhood.create(:name => "Villa Lugano")
Neighbourhood.create(:name => "Villa Luro")
Neighbourhood.create(:name => "Villa Ortúzar")
Neighbourhood.create(:name => "Villa Pueyrredón")
Neighbourhood.create(:name => "Villa Real")
Neighbourhood.create(:name => "Villa Riachuelo")
Neighbourhood.create(:name => "Villa Santa Rita")
Neighbourhood.create(:name => "Villa Soldati")
Neighbourhood.create(:name => "Villa Urquiza")


Currency.create(:name => "Pesos", abreviatura: "$")
Currency.create(:name => "Dólares", abreviatura: "USD")

PropertyType.create(:name => "Departamento")
PropertyType.create(:name => "PH")
PropertyType.create(:name => "Casa")
PropertyType.create(:name => "Terreno")
PropertyType.create(:name => "Oficina")
PropertyType.create(:name => "Local Comercial")

User.create!({:email => "usuario1@prueba.com", :password => "111111", :password_confirmation => "111111", :phone_number =>  "01161860000"})
User.create!({:email => "usuario2@prueba.com", :password => "222222", :password_confirmation => "222222", :phone_number =>  "01161860001" })

Plan.create(name: "Premium", price: 1000, duration: 12, priority: 1, number_images_allowed: 20, number_videos_allowed: 1)
Plan.create(name: "Básico", price: 300, duration: 4, priority: 2, number_images_allowed: 5, number_videos_allowed: 0)
Plan.create(name: "Gratis", price: 0, duration: 1, priority: 3, number_images_allowed: 1, number_videos_allowed: 0)

Publication.create(user_id: 1, effective_date: (DateTime.now + 1.year).strftime("%d/%m/%Y") , operation: "Alquiler", address: "Adolfo Alsina, Buenos Aires, Argentina", floor: 1, apartment: "1", number_spaces: 2, surface: 50, price: 3000.0, expenses: 200.0, antiquity: 10, description: "", additional_info: "", created_at: "2014-09-14 15:21:35", updated_at: "2014-09-14 17:33:20", currency_id: 2, neighbourhood_id: 5, property_type_id: 3, plan_id: 1)
Publication.create(user_id: 1, effective_date: (DateTime.now).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Quesada, Buenos Aires, Argentina", floor: 2, apartment: "3", number_spaces: 4, surface: 120, price: 7000.0, expenses: 200.0, antiquity: 50, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 6, property_type_id: 3,plan_id: 2)
Publication.create(user_id: 2, effective_date: (DateTime.now + 1.year).strftime("%d/%m/%Y"), operation: "Venta", address: "Avenida del Libertador, Buenos Aires, Argentina", floor: 10, apartment: "5", number_spaces: 3, surface: 100, price: 75000.0, expenses: 2000, antiquity: 5, description: "", additional_info: "", created_at: "2014-09-14 17:40:21", updated_at: "2014-09-14 17:56:50", currency_id: 1, neighbourhood_id: 1, property_type_id: 4,plan_id: 3)
Publication.create(user_id: 2, effective_date: (DateTime.now + 1.year).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Sarlanga 456, Buenos Aires, Argentina", floor: 1, apartment: "2", number_spaces: 4, surface: 120, price: 60000.0, expenses: 3000, antiquity: 5, description: "", additional_info: "", created_at: "2014-09-14 17:40:21", updated_at: "2014-09-14 17:56:50", currency_id: 1, neighbourhood_id: 32, property_type_id: 4,plan_id: 1)

Publication.create(user_id: 1, effective_date: (DateTime.now + 1.month).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Quesada, Buenos Aires, Argentina", floor: 2, apartment: "1", number_spaces: 1, surface: 70, price: 5000.0, expenses: 400.0, antiquity: 20, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 5, property_type_id: 3,plan_id: 2)
Publication.create(user_id: 1, effective_date: (DateTime.now + 1.month - 4.days).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Quesada, Buenos Aires, Argentina", floor: 4, apartment: "2", number_spaces: 3, surface: 120, price: 2000.0, expenses: 200.0, antiquity: 15, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 2, property_type_id: 3,plan_id: 3)
Publication.create(user_id: 1, effective_date: (DateTime.now + 17.days).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Quesada, Buenos Aires, Argentina", floor: 2, apartment: "3", number_spaces: 2, surface: 80, price: 3500.0, expenses: 500.0, antiquity: 5, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 6, property_type_id: 2,plan_id: 3)
Publication.create(user_id: 1, effective_date: (DateTime.now + 1.year - 5.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Quesada, Buenos Aires, Argentina", floor: 12, apartment: "3", number_spaces: 4, surface: 40, price: 6000.0, expenses: 700.0, antiquity: 35, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 6, property_type_id: 3,plan_id: 2)
Publication.create(user_id: 1, effective_date: (DateTime.now + 1.year + 50.days).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Quesada, Buenos Aires, Argentina", floor: 5, apartment: "5", number_spaces: 4, surface: 200, price: 7000.0, expenses: 1200.0, antiquity: 40, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 2, neighbourhood_id: 6, property_type_id: 3,plan_id: 2)
Publication.create(user_id: 1, effective_date: (DateTime.now + 14.days).strftime("%d/%m/%Y"), operation: "Venta", address: "Quesada, Buenos Aires, Argentina", floor: 2, apartment: "3", number_spaces: 3, surface: 150, price: 10000.0, expenses: 100.0, antiquity: 50, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 20, property_type_id: 1,plan_id: 2)
Publication.create(user_id: 1, effective_date: (DateTime.now + 1.year).strftime("%d/%m/%Y"), operation: "Venta", address: "Quesada, Buenos Aires, Argentina", floor: 7, apartment: "4", number_spaces: 5, surface: 120, price: 6500.0, expenses: 250.0, antiquity: 20, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 6, property_type_id: 3,plan_id: 1)
Publication.create(user_id: 1, effective_date: (DateTime.now + 10.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Quesada, Buenos Aires, Argentina", floor: 4, apartment: "2", number_spaces: 2, surface: 140, price: 7200.0, expenses: 200.0, antiquity: 50, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 14, property_type_id: 3,plan_id: 1)
Publication.create(user_id: 1, effective_date: (DateTime.now + 1.days).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Quesada, Buenos Aires, Argentina", floor: 8, apartment: "31", number_spaces: 4, surface: 90, price: 7800.0, expenses: 300.0, antiquity: 10, description: "", additional_info: "", created_at: "2014-09-14 17:38:48", updated_at: "2014-09-14 17:38:48", currency_id: 1, neighbourhood_id: 6, property_type_id: 3,plan_id: 3)



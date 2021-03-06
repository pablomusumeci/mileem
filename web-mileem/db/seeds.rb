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

palermo_id = Neighbourhood.where(name: "Palermo").first.id
belgrano_id = Neighbourhood.where(name: "Belgrano").first.id
nuniez_id = Neighbourhood.where(name: "Núñez").first.id
colegiales_id = Neighbourhood.where(name: "Colegiales").first.id
coghlan_id = Neighbourhood.where(name: "Coghlan").first.id

NearNeighbourhood.create(:neighbourhood_id_1 => belgrano_id, :neighbourhood_id_2 => nuniez_id)
NearNeighbourhood.create(:neighbourhood_id_1 => belgrano_id, :neighbourhood_id_2 => palermo_id)
NearNeighbourhood.create(:neighbourhood_id_1 => belgrano_id, :neighbourhood_id_2 => Neighbourhood.where(name: "Colegiales").first.id)
NearNeighbourhood.create(:neighbourhood_id_1 => belgrano_id, :neighbourhood_id_2 => Neighbourhood.where(name: "Coghlan").first.id)


Currency.create(:name => "Pesos", abreviatura: "$")
Currency.create(:name => "Dólares", abreviatura: "USD")

PropertyType.create(:name => "Departamento")
PropertyType.create(:name => "PH")
PropertyType.create(:name => "Casa")
PropertyType.create(:name => "Terreno")
PropertyType.create(:name => "Oficina")
PropertyType.create(:name => "Local Comercial")

User.create!({:email => "test_user_94742966@testuser.com", :password => "qatest7306", :password_confirmation => "qatest7306", :phone_number =>  "01161860000", :username => "TETE50014"})
User.create!({:email => "javierchoque21@gmail.com", :password => "222222", :password_confirmation => "222222", :phone_number =>  "01161860001" })

Plan.create(name: "Premium", price: 1000, duration: 12, priority: 1, number_images_allowed: 10, number_videos_allowed: 1)
Plan.create(name: "Básico", price: 300, duration: 3, priority: 2, number_images_allowed: 5, number_videos_allowed: 1)
Plan.create(name: "Gratis", price: 0, duration: 1, priority: 3, number_images_allowed: 3, number_videos_allowed: 0)

Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now + 2.months).strftime("%d/%m/%Y") , end_date: (DateTime.now + 2.months + 1.year).strftime("%d/%m/%Y"),operation: "Alquiler", address: "Av Cabildo 3000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 3, apartment: "C", number_spaces: 1, surface: 40, price: 2000.0, expenses: 300.0, antiquity: 80, description: "", additional_info: "", currency_id: 1, neighbourhood_id: belgrano_id, property_type_id: 1, plan_id: 1, status: 2, lat: -34.554551, lng: -58.463541)
Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"), end_date: (DateTime.now + 3.months).strftime("%d/%m/%Y"),operation: "Alquiler", address: "Av del Libertador 7001, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 2, apartment: "F", number_spaces: 3, surface: 120, price: 5000.0, expenses: 1000.0, antiquity: 50, description: "", additional_info: "", currency_id: 1, neighbourhood_id: nuniez_id, property_type_id: 1,plan_id: 2,status: 2, lat: -34.548003, lng: -58.455713)
Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"), end_date: (DateTime.now + 3.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "French 3001, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", number_spaces: 4, surface: 145, price: 9000.0, description: "", additional_info: "", currency_id: 1, neighbourhood_id: palermo_id, property_type_id: 3,plan_id: 2,status: 2, lat: -34.587158, lng: -58.405498)
Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"),end_date: (DateTime.now + 1.months).strftime("%d/%m/%Y"), operation: "Venta", address: "Franklin D. Roosevelt 2000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 6, apartment: "B", number_spaces: 2, surface: 60, price: 100000.0, description: "", additional_info: "", currency_id: 2, neighbourhood_id: nuniez_id, property_type_id: 1,plan_id: 3,status: 2, lat: -34.555218, lng: -58.456963)
Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now + 1.year - 5.months).strftime("%d/%m/%Y"),end_date: (DateTime.now + 1.year - 5.months + 3.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Av Coronel Díaz 2000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 5, apartment: "C", number_spaces: 2, surface: 50, price: 3000.0, expenses: 700.0, antiquity: 20, description: "", additional_info: "", currency_id: 1, neighbourhood_id: palermo_id, property_type_id: 1,plan_id: 2,status: 2, lat: -34.588275,lng: -58.409528)
Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"), end_date: (DateTime.now + 3.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Juramento 3000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 2, apartment: "C", number_spaces: 2, surface: 55, price: 3500.0, expenses: 700.0, description: "", additional_info: "", currency_id: 1, neighbourhood_id: belgrano_id, property_type_id: 1,plan_id: 3,status: 2, lat: -34.565353,lng: -58.462301)
p1 = Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"),end_date: (DateTime.now + 3.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Av Federico Lacroze 2300, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 1, apartment: "A", number_spaces: 1, surface: 25, price: 2300, expenses: 500, description: "Muy luminoso", additional_info: "",  currency_id: 1, neighbourhood_id: colegiales_id, property_type_id: 1,plan_id: 2,status: 2, lat: -34.568754, lng: -58.443693)
p2 = Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"),end_date: (DateTime.now + 1.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Av Congreso 4000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 2, apartment: "B", number_spaces: 2, surface: 65, price: 4000.0, expenses: 500.0, antiquity: 100, description: "", additional_info: "", currency_id: 1, neighbourhood_id: coghlan_id, property_type_id: 1,plan_id: 3,status: 2, lat: -34.564055,lng: -58.479263)
p3 = Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"),end_date: (DateTime.now + 3.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Av del Libertador 6000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 10, apartment: "A", number_spaces: 3, surface: 75, price: 5000.0, expenses: 1200.0, description: "", additional_info: "", currency_id: 1, neighbourhood_id: belgrano_id, property_type_id: 1,plan_id: 2,status: 2, lat: -34.556697, lng: -58.447700)
Publication.create(user_id: 2, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"), end_date: (DateTime.now + 1.year).strftime("%d/%m/%Y"),operation: "Alquiler", address: "Vuelta de Obligado 2500, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 4, apartment: "B", number_spaces: 2, surface: 50, price: 3300, expenses: 1000.0, description: "", additional_info: "", currency_id: 1, neighbourhood_id: belgrano_id, property_type_id: 1,plan_id: 1,status: 2, lat: -34.557712, lng: -58.458926)
Publication.create(user_id: 2, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"), end_date: (DateTime.now + 1.year).strftime("%d/%m/%Y"),operation: "Alquiler", address: "Conesa 2300, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 2, apartment: "E", number_spaces: 2, surface: 45, price: 3400, expenses: 700.0, description: "", additional_info: "", currency_id: 1, neighbourhood_id: colegiales_id, property_type_id: 1,plan_id: 1,status: 2, lat: -34.563494, lng: -58.463902)
Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"), end_date: (DateTime.now + 3.months).strftime("%d/%m/%Y"),operation: "Alquiler", address: "Arenales 3300, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 12, apartment: "A", number_spaces: 4, surface: 120, price: 600.0, description: "", additional_info: "", currency_id: 2, neighbourhood_id: palermo_id, property_type_id: 1,plan_id: 2,status: 2, lat: -34.588291, lng: -58.409511)

p1.update(effective_date: (DateTime.now - 1.months).strftime("%d/%m/%Y"),end_date: (DateTime.now - 1.months + 3.months).strftime("%d/%m/%Y"))
p2.update(effective_date: (DateTime.now - 45.days).strftime("%d/%m/%Y"),end_date: (DateTime.now - 45.days + 1.months).strftime("%d/%m/%Y"))
p3.update(effective_date: (DateTime.now - 6.months).strftime("%d/%m/%Y"),end_date: (DateTime.now - 3.months).strftime("%d/%m/%Y"))


p4 = Publication.create(user_id: 2, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"), end_date: (DateTime.now + 1.year).strftime("%d/%m/%Y"),operation: "Alquiler", address: "Conesa 2270, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 4, apartment: "C", number_spaces: 3, surface: 60, price: 5700, expenses: 550.0, description: "", additional_info: "", currency_id: 1, neighbourhood_id: colegiales_id, property_type_id: 1,plan_id: 1,status: 2, lat: -34.563494, lng: -58.463902)
p5 = Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"), end_date: (DateTime.now + 3.months).strftime("%d/%m/%Y"),operation: "Alquiler", address: "Arenales 3280, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 8, apartment: "F", number_spaces: 2, surface: 50, price: 2100.0, description: "", additional_info: "", currency_id: 1, neighbourhood_id: palermo_id, property_type_id: 1,plan_id: 2,status: 2, lat: -34.588291, lng: -58.409511)
p6 = Publication.create(user_id: 2, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"),end_date: (DateTime.now + 1.months).strftime("%d/%m/%Y"), operation: "Alquiler", address: "Av Congreso 4014, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 7, apartment: "A", number_spaces: 3, surface: 75, price: 2300.0, expenses: 400.0, antiquity: 60, description: "", additional_info: "", currency_id: 1, neighbourhood_id: coghlan_id, property_type_id: 1,plan_id: 3,status: 2, lat: -34.564055,lng: -58.479263)
p7 = Publication.create(user_id: 1, payment_status: "Realizado", effective_date: (DateTime.now).strftime("%d/%m/%Y"),end_date: (DateTime.now + 1.months).strftime("%d/%m/%Y"), operation: "Venta", address: "Av Congreso 5000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina", floor: 10, apartment: "B", number_spaces: 4, surface: 100, price: 150000.0, antiquity: 60, description: "", additional_info: "", currency_id: 2, neighbourhood_id: belgrano_id, property_type_id: 1,plan_id: 3,status: 2, lat: -34.564055,lng: -58.479263)

p4.update(effective_date: (DateTime.now - 5.months).strftime("%d/%m/%Y"),end_date: (DateTime.now - 5.months + 1.year).strftime("%d/%m/%Y"))
p5.update(effective_date: (DateTime.now - 12.days).strftime("%d/%m/%Y"),end_date: (DateTime.now - 12.days + 3.months).strftime("%d/%m/%Y"))
p6.update(effective_date: (DateTime.now - 5.days).strftime("%d/%m/%Y"),end_date: (DateTime.now - 5.days + 1.months).strftime("%d/%m/%Y"))
p7.update(effective_date: (DateTime.now - 5.days).strftime("%d/%m/%Y"),end_date: (DateTime.now - 5.days + 1.months).strftime("%d/%m/%Y"))


Upload.create(upload_file_name:	"depto6.jpg",upload_content_type: "image/jpeg",upload_file_size: 56158,publication_id: 1)
Upload.create(upload_file_name:	"depto10.jpg",upload_content_type: "image/jpeg",upload_file_size: 262308,publication_id: 1)
Upload.create(upload_file_name:	"depto2.jpg",upload_content_type: "image/jpeg",upload_file_size: 9527,publication_id: 2)
Upload.create(upload_file_name:	"depto16.jpg",upload_content_type: "image/jpeg",upload_file_size: 106926,publication_id: 2)
Upload.create(upload_file_name:	"casa3.jpg",upload_content_type: "image/jpeg",upload_file_size: 145274,publication_id: 3)
Upload.create(upload_file_name:	"depto3.jpg",upload_content_type: "image/jpeg",upload_file_size: 24302,publication_id: 3)
Upload.create(upload_file_name:	"depto5.jpg",upload_content_type: "image/jpeg",upload_file_size: 82476,publication_id: 4)
Upload.create(upload_file_name:	"depto4.jpg",upload_content_type: "image/jpeg",upload_file_size: 33132,publication_id: 4)
Upload.create(upload_file_name:	"depto11.jpg",upload_content_type: "image/jpeg",upload_file_size: 37897,publication_id: 5)
Upload.create(upload_file_name:	"depto6.jpg",upload_content_type: "image/jpeg",upload_file_size: 56158,publication_id: 5)
Upload.create(upload_file_name:	"depto14.jpg",upload_content_type: "image/jpeg",upload_file_size: 31052,publication_id: 6)
Upload.create(upload_file_name:	"depto12.jpg",upload_content_type: "image/jpeg",upload_file_size: 23760,publication_id: 6)
Upload.create(upload_file_name:	"depto18.jpg",upload_content_type: "image/jpeg",upload_file_size: 35975,publication_id: 7)
Upload.create(upload_file_name:	"depto10.jpg",upload_content_type: "image/jpeg",upload_file_size: 262308,publication_id: 7)
Upload.create(upload_file_name:	"depto9.JPG",upload_content_type: "image/jpeg",upload_file_size: 167993,publication_id: 8)
Upload.create(upload_file_name:	"depto3.jpg",upload_content_type: "image/jpeg",upload_file_size: 24302,publication_id: 8)
Upload.create(upload_file_name:	"depto21.jpg",upload_content_type: "image/jpeg",upload_file_size: 27727,publication_id: 9)
Upload.create(upload_file_name:	"depto19.jpg",upload_content_type: "image/jpeg",upload_file_size: 117265,publication_id: 9)
Upload.create(upload_file_name:	"depto1.jpg",upload_content_type: "image/jpeg",upload_file_size: 48035,publication_id: 10)
Upload.create(upload_file_name:	"depto22.jpg",upload_content_type: "image/jpeg",upload_file_size: 6616,publication_id: 10)
Upload.create(upload_file_name:	"depto20.jpg",upload_content_type: "image/jpeg",upload_file_size: 5441,publication_id: 11)
Upload.create(upload_file_name:	"depto16.jpg",upload_content_type: "image/jpeg",upload_file_size: 106926,publication_id: 11)
Upload.create(upload_file_name:	"depto12.jpg",upload_content_type: "image/jpeg",upload_file_size: 23760,publication_id: 12)
Upload.create(upload_file_name:	"depto5.jpg",upload_content_type: "image/jpeg",upload_file_size: 82476,publication_id: 12)
Upload.create(upload_file_name:	"depto2.jpg",upload_content_type: "image/jpeg",upload_file_size: 9527,publication_id: 14)
Upload.create(upload_file_name:	"depto4.jpg",upload_content_type: "image/jpeg",upload_file_size: 33132,publication_id: 14)
Upload.create(upload_file_name:	"depto17.jpg",upload_content_type: "image/jpeg",upload_file_size: 43211,publication_id: 15)
Upload.create(upload_file_name:	"depto22.jpg",upload_content_type: "image/jpeg",upload_file_size: 6616,publication_id: 15)
Upload.create(upload_file_name:	"depto19.jpg",upload_content_type: "image/jpeg",upload_file_size: 117265,publication_id: 13)
Upload.create(upload_file_name:	"depto24.jpg",upload_content_type: "image/jpeg",upload_file_size: 5630,publication_id: 13)
Upload.create(upload_file_name:	"depto10.jpg",upload_content_type: "image/jpeg",upload_file_size: 262308,publication_id: 16)
Upload.create(upload_file_name:	"depto14.jpg",upload_content_type: "image/jpeg",upload_file_size: 31052,publication_id: 16)

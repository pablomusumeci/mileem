json.array!(@publications) do |publication|
  json.extract! publication, :id, :effective_date, :operation, :address, :floor, :apartment, :number_spaces, :surface, :price, :expenses, :antiquity, :description, :additional_info
  json.url publication_url(publication, format: :json)
end

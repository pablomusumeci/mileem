class PaymentController < ApplicationController
  # GET /payment/notification
  def notification
  	@topic = params[:topic]
  	@payment_id = params[:id]
  	Rails.logger.info "NOTIFICACION RECIBIDA ID : #{@payment_id}"
  	@response = $mp.notification(@payment_id)
  	Rails.logger.info "Response #{@response}"
  	@notification = JSON.parse(@response)
  	@publication_id = @notification["external_reference"].split("-")[2].to_i
  	@publication = Publication.find(@publication_id)
  	Rails.logger.info "Antes de modificarla #{@publication}"
  	@publication.payment_status = 'Aprobado'
  	@publication.save
  	Rails.logger.info "Despues del save #{@publication}"
  rescue Exception => e
  	Rails.logger.error "Error en notification #{e.message}"
  end
end

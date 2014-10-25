class PaymentController < ApplicationController
  # GET /payment/notification
  def notification
  	@topic = params[:topic]
  	@payment_id = params[:id]
  	Rails.logger.info "NOTIFICACION RECIBIDA ID : #{@payment_id}"
  	@publication_id = @payment_id.split("-")[2].to_i
  	@publication = Publication.find(@publication_id)
  	p "Antes de modificarla #{@publication}"
  	@publication.payment_status = 'Aprobado'
  	@publication.save
  	p "Despues del save #{@publication}"
  end
end

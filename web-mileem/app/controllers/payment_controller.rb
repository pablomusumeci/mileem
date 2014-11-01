class PaymentController < ApplicationController
  skip_before_action :verify_authenticity_token
	
  # GET /payment/notification
  def notification
  	@topic = params[:topic]
  	@payment_id = params[:id]
  	Rails.logger.info "NOTIFICACION RECIBIDA ID : #{@payment_id}"
  	@response = $mp.notification(@payment_id)
  	Rails.logger.info "Response #{@response}"
    if @response["collection"]["status"] == "approved"
      Rails.logger.info "PAGO ID #{@payment_id} APROBADO!"
    	@publication_id = @response["collection"]["external_reference"].split("-")[2].to_i
    	@publication = Publication.find(@publication_id)
    	Rails.logger.info "Antes de modificarla #{@publication}"
    	@publication.payment_status = 'Realizado'
    	@publication.save
    	Rails.logger.info "Despues del save #{@publication}"
    end
  rescue Exception => e
  	Rails.logger.error "Error en notification #{e.message}"
  end
end

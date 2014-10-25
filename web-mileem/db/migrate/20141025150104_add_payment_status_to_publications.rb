class AddPaymentStatusToPublications < ActiveRecord::Migration
  def change
    add_column :publications, :payment_status, :string
  end
end

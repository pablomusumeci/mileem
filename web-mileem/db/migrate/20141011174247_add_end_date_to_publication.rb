class AddEndDateToPublication < ActiveRecord::Migration
  def change
  	add_column :publications, :end_date, :date
  end
end

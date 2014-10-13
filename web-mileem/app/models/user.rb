class User < ActiveRecord::Base
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable
  has_many :publications

  def can_have_new_free_publication?
 	free_publications = Publication.where(user_id: self.id).where(plan_id: 3).to_a
    # Filtro las que no estan activas
    free_publications.select!{ |p| p.isAvailable } if not free_publications.empty?
    amount_free_publications = (free_publications.empty? ? 0 : free_publications.size)
    Rails.logger.info "Cantidad de publicaciones gratis del usuario #{self.id} = #{amount_free_publications}"
    return (amount_free_publications < 5)
  end
end
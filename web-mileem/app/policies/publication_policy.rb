class PublicationPolicy < ApplicationPolicy
  
  def update?
    user.id == record.user_id
  end
  
  def delete?
    Date.today < record.effective_date
  end
  
  class Scope < Scope
    def resolve
      scope.where(:user_id => user.id)
    end
  end
end

class PublicationPolicy < ApplicationPolicy
  
  def update?
    user.id == record.user_id  && record.end_date > Date.today &&  !record.finished?
  end
  
  def delete?
    Date.today < record.effective_date
  end

  def republish?
    (record.end_date < Date.today) || record.finished?
  end

  class Scope < Scope
    def resolve
      scope.where(:user_id => user.id)
    end
  end
end


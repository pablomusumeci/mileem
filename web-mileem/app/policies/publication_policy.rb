class PublicationPolicy < ApplicationPolicy
  
  def update?
    user.id == record.user_id  && record.end_date > Date.today &&  !record.finished?
  end
  
  def read?
    user.id == record.user_id
  end
  
  def delete?
    Date.today < record.effective_date
  end

  def republish?
    (record.end_date < Date.today) || record.finished?
  end

  def upload_video?
    record.video_uploads.size() == 0 && !record.plan.is_free?
  end

  def delete_video?
    record.video_uploads.size() > 0
  end

  class Scope < Scope
    def resolve
      scope.where(:user_id => user.id)
    end
  end
end


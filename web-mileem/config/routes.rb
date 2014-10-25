# -*- encoding : utf-8 -*-
Rails.application.routes.draw do

  devise_for :users

  get 'publications/jsonifier/:id' => 'publications#jsonifier'
  get 'publications/search' => 'publications#search'
  get 'publications/:id/uploads', to: 'publications#uploads', as: 'uploads_publication'
  get 'publications/:id/stop', to: 'publications#stop', as: 'stop_publication'
  get 'publications/:id/finish', to: 'publications#finish', as: 'finish_publication'
  get 'publications/:id/active', to: 'publications#active', as: 'active_publication'
  get 'publications/:id/republicate', to: 'publications#republicate', as: 'republicate'
  post 'publications/:id/republicate', to: 'publications#save_republicate'


  resources :uploads
  resources :publications do
    resources :uploads, :only => [:create]
    collection do
      get :reset_filterrific
    end
  end

  get 'uploads/:id/delete', to: 'uploads#destroy', as: 'uploads_destroy'

  post 'video_uploads/create/:publication_id', to: 'video_uploads#create', as: 'video_uploads_publication'
  post 'video_uploads/destroy/:id', to: 'video_uploads#destroy', as: 'destroy_video_uploads_publication'
  # Busqueda

  # The priority is based upon order of creation: first created -> highest priority.
  # See how all your routes lay out with "rake routes".

  # You can have the root of your site routed with "root"
  root 'welcome#index'
  # Example of regular route:
  #   get 'products/:id' => 'catalog#view'

  # Example of named route that can be invoked with purchase_url(id: product.id)
  #   get 'products/:id/purchase' => 'catalog#purchase', as: :purchase

  # Example resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Example resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Example resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Example resource route with more complex sub-resources:
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', on: :collection
  #     end
  #   end

  # Example resource route with concerns:
  #   concern :toggleable do
  #     post 'toggle'
  #   end
  #   resources :posts, concerns: :toggleable
  #   resources :photos, concerns: :toggleable

  # Example resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end
end

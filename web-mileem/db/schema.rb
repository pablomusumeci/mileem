# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20141003183758) do

  create_table "currencies", force: true do |t|
    t.string   "name"
    t.string   "abreviatura"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "currency_conversions", id: false, force: true do |t|
    t.integer "currency_id_1"
    t.integer "currency_id_2"
    t.float   "factor",        limit: 24
  end

  create_table "neighbourhoods", force: true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "plans", force: true do |t|
    t.float    "price",                 limit: 24
    t.integer  "duration"
    t.integer  "priority"
    t.integer  "number_images_allowed"
    t.integer  "number_videos_allowed"
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "property_types", force: true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "publications", force: true do |t|
    t.date     "effective_date"
    t.string   "operation"
    t.string   "address"
    t.integer  "floor"
    t.string   "apartment"
    t.integer  "number_spaces"
    t.integer  "surface"
    t.float    "price",            limit: 24
    t.float    "expenses",         limit: 24
    t.integer  "antiquity"
    t.text     "description"
    t.text     "additional_info"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "currency_id"
    t.integer  "neighbourhood_id"
    t.integer  "property_type_id"
    t.integer  "user_id"
    t.integer  "plan_id"
    t.decimal  "lat",                         precision: 10, scale: 6
    t.decimal  "lng",                         precision: 10, scale: 6
  end

  add_index "publications", ["currency_id"], name: "index_publications_on_currency_id", using: :btree
  add_index "publications", ["neighbourhood_id"], name: "index_publications_on_neighbourhood_id", using: :btree
  add_index "publications", ["plan_id"], name: "index_publications_on_plan_id", using: :btree
  add_index "publications", ["property_type_id"], name: "index_publications_on_property_type_id", using: :btree
  add_index "publications", ["user_id"], name: "index_publications_on_user_id", using: :btree

  create_table "uploads", force: true do |t|
    t.string   "upload_file_name"
    t.string   "upload_content_type"
    t.integer  "upload_file_size"
    t.datetime "upload_updated_at"
    t.integer  "publication_id"
  end

  create_table "users", force: true do |t|
    t.string   "email",                  default: "", null: false
    t.string   "encrypted_password",     default: "", null: false
    t.string   "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer  "sign_in_count",          default: 0,  null: false
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string   "current_sign_in_ip"
    t.string   "last_sign_in_ip"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "phone_number"
  end

  add_index "users", ["email"], name: "index_users_on_email", unique: true, using: :btree
  add_index "users", ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true, using: :btree

end

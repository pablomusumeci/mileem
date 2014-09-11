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

ActiveRecord::Schema.define(version: 20140911005939) do

  create_table "publications", force: true do |t|
    t.date     "effective_date"
    t.string   "operation"
    t.string   "address"
    t.integer  "floor"
    t.string   "apartment"
    t.integer  "number_spaces"
    t.integer  "surface"
    t.float    "price",           limit: 24
    t.float    "expenses",        limit: 24
    t.integer  "antiquity"
    t.text     "description"
    t.text     "additional_info"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end

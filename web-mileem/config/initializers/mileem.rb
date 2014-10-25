ENV.update YAML.load_file('config/config.yml')[Rails.env] rescue {}

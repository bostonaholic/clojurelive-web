(ns clojurelive-web.core
  (:require [immutant.web :as web]
            [clojurelive-web.config :as config]
            [clojurelive-web.db :as db]
            [clojurelive-web.handler :as handler])
  (:gen-class))

(defn run-web [handler args]
  (condp = (:nomad/environment (config/config))
    "prod" (web/run handler args)
    "dev" (web/run-dmc handler args)
    (web/run-dmc handler args)))

(defn -main [& {:as args}]
  (db/ensure!)
  (run-web handler/app args))

(ns clojurelive-web.core
  (:require [immutant.web :as web]
            [clojurelive-web.config :as config]
            [clojurelive-web.handler :as handler])
  (:gen-class))

(defn run-web [handler]
  (condp = (:nomad/environment (config/config))
    "prod" (web/run handler)
    "dev" (web/run-dmc handler)
    (web/run-dmc handler)))

(defn -main [& args]
  (run-web handler/app))

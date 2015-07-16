(ns clojurelive-web.core
  (:require [immutant.web :as web]
            [clojurelive-web.config :as config]
            [clojurelive-web.handler :as handler]
            [environ.core :refer [env]])
  (:gen-class))

(defn run-web [handler args]
  (condp = (:nomad/environment (config/config))
    "prod" (web/run handler args)
    "dev" (web/run-dmc handler args)
    (web/run-dmc handler args)))

(defn -main [& {:as args}]
  (run-web handler/app
           (merge {"host" (env :demo-web-host), "port" (env :demo-web-port)}
                  args)))

(ns clojurelive-web.web
  (:require [clojurelive-web.handler :as handler]
            [compojure.handler :as compojure]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (compojure/site #'handler/app) {:port port :join? false})))

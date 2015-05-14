(ns clojurelive-web.handler
  (:require [clojurelive-web.view :as view]
            [clojurelive-web.actions :as actions]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]))

(defroutes app
  (route/resources "/")

  (GET "/" []
       (view/splash))

  (POST "/subscribe" [email]
        (actions/subscribe email))

  (POST "/unsubscribe" [email]
        (actions/unsubscribe email)))

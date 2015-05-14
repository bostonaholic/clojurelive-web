(ns clojurelive-web.handler
  (:require [clojurelive-web.view :as view]
            [clojurelive-web.actions :as actions]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :as ring]))

(defroutes routes
  (route/resources "/")

  (GET "/" []
       (view/splash))

  (POST "/subscribe" [email]
        (actions/subscribe email))

  (POST "/unsubscribe" [email]
        (actions/unsubscribe email)))

(def app
  (-> routes
      (ring/wrap-defaults ring/secure-site-defaults)))

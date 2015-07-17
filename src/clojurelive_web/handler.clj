(ns clojurelive-web.handler
  (:require [clojurelive-web.actions :as actions]
            [clojurelive-web.view.conduct :as conduct-view]
            [clojurelive-web.view.home :as home-view]
            [clojurelive-web.view.login :as login-view]
            [clojurelive-web.view.reset-password :as reset-password-view]
            [clojurelive-web.view.privacy :as privacy-view]
            [clojurelive-web.view.signup :as signup-view]
            [clojurelive-web.view.terms :as terms-view]
            [clojurelive-web.view.topic :as topic-view]
            [compojure.core :refer [defroutes ANY GET POST]]
            [compojure.handler :as compojure-handler]
            [compojure.route :as compojure-route]
            [ring.middleware.session.cookie :as cookie]
            [ring.util.response :as ring-response]))

(defroutes routes
  (compojure-route/resources "/")

  (GET "/signup" req (signup-view/show (:session req)))
  (POST "/signup" req (actions/signup req))

  (GET "/login" req (login-view/show (:session req)))
  (POST "/login" req (actions/login req))

  (ANY "/logout" req (actions/logout req))

  (GET "/reset-password" req (reset-password-view/show (:session req)))
  (POST "/reset-password" req (actions/send-reset-password-email req))
  (GET "/reset-password/:uuid" req (reset-password-view/new (:session req) (:uuid (:params req))))
  (POST "/reset-password/:uuid" req (actions/reset-password req))

  (GET "/link/new" req
       (if (actions/authenticated? req)
         (topic-view/new-link (:session req))
         (ring-response/redirect "/login")))
  (POST "/link/new" req (actions/new-link req))
  (GET "/text/new" req
       (if (actions/authenticated? req)
         (topic-view/new-text (:session req))
         (ring-response/redirect "/login")))
  (POST "/text/new" req (actions/new-text req))

  (GET "/t/:uuid" req
       (->> (actions/for-topic req)
            (topic-view/show (:session req))))
  (POST "/t/:uuid/comment" req (actions/new-comment req))

  (GET "/newest" req (topic-view/listing (:session req) (actions/newest-topics)))

  (GET "/terms" req (terms-view/show (:session req)))
  (GET "/conduct" req (conduct-view/show (:session req)))
  (GET "/privacy" req (privacy-view/show (:session req)))
  (GET "/" req (home-view/show (:session req) (actions/newest-topics))))

(def app
  (-> routes
      (compojure-handler/site {:session      {:store (cookie/cookie-store {:key "secret!@#$%^&*13"})}
                               :cookie-attrs {:max-age (* 3600 6)
                                              :secure true}})))

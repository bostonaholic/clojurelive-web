(ns clojurelive-web.db
  (:require [clojurelive-web.config :as config]))

(defonce subname (:subname (:db (config/config))))
(defonce db-user (:user (:db (config/config))))
(defonce db-password (:password (:db (config/config))))
(defonce sslmode (:sslmode (:db (config/config))))

(defonce conn-spec {:classname "org.postgresql.Driver"
                    :subprotocol "postgresql"
                    :subname subname
                    :user db-user
                    :password db-password
                    :sslmode sslmode})

(ns clojurelive-web.db
  (:require [clojurelive-web.config :as config]
            [clojure.java.io :as io]
            [datomic.api :as d]
            [io.rkn.conformity :as c]))

(def url (:datomic-url (config/config)))

(def conn (delay (d/connect url)))

(defn db [] (d/db @conn))

(def schema (read-string (slurp (io/resource "schema.edn"))))

(defn ensure!
  ([] (ensure! @conn))
  ([conn] (c/ensure-conforms conn schema)))

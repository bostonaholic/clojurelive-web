(ns clojurelive-web.actions
  (:require [clojurelive-web.mailers]))

(defn subscribe [email]
  (println "SUBSCRIBE:" email)
  (mailers/subscribe email)
  {:status 204})

(defn unsubscribe [email]
  (println "UNSUBSCRIBE:" email)
  (mailers/unsubscribe email)
  {:status 204})

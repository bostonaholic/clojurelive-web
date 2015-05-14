(ns clojurelive-web.actions)

(defn subscribe [email]
  (println "SUBSCRIBE:" email)
  {:status 204})

(defn unsubscribe [email]
  (println "UNSUBSCRIBE:" email)
  {:status 204})

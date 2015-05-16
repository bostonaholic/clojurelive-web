(ns clojurelive-web.mailers
  (:require [postmark.core :refer [postmark]]
            [environ.core :refer [env]]))

(def api-token (env :postmark-api-token))

(def send-message (postmark api-token "matthew@clojurelive.com"))

(defn subscribe [email]
  (send-message {:to "subscribe@clojurelive.com"
                 :subject "SUBSCRIBE"
                 :text (str "SUBSCRIBE: " email)
                 :reply-to email}))

(defn unsubscribe [email]
  (send-message {:to "unsubscribe@clojurelive.com"
                 :subject "UNSUBSCRIBE"
                 :text (str "UNSUBSCRIBE: " email)
                 :reply-to email}))

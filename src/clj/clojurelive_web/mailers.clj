(ns clojurelive-web.mailers
  (:require [postmark.core :refer [postmark]]))

(def api-token (get (System/getenv) "POSTMARK_API_TOKEN"))

(def send-message (postmark api-token "no-reply@clojurelive.com"))

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
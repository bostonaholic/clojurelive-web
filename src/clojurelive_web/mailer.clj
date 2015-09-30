(ns clojurelive-web.mailer
  (:require [clojurelive-web.config :as config]
            [clojure.pprint :as pp]
            [postmark.core :refer [postmark]]
            [environ.core :refer [env]]))

(def api-token (env :postmark-api-token))

(def send-message (postmark api-token "matthew@clojurelive.com"))

(defn reset-password-body [token]
  (str "To reset your Clojure Live password, please click the link below.

" (env :site-url) "/reset-password/" token "

If you did not request your password to be reset, please ignore this email and your password will stay as it is."))

(defn reset-password [email token]
  (let [message {:to email
                 :subject "Clojure Live Password Reset"
                 :text (reset-password-body token)}]
    (if (:send-emails? (config/config))
      (send-message message)
      (pp/pprint message))))

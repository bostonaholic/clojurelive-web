(ns clojurelive-web.data.reset-password
  (:require [clojurelive-web.db :as db]
            [clojurelive-web.data.common :as common]
            [clojure.java.jdbc :as jdbc]
            [clj-uuid :as uuid]
            [digest :as digest]))

(defn create [email token]
  (let [reset-password-data {:created_at (java.sql.Timestamp. (.getTime (java.util.Date.)))
                             :email email
                             :token token}]
    (jdbc/insert! db/conn-spec "reset_password_tokens" reset-password-data)))

(defn find-by-token [token]
  (first (jdbc/query db/conn-spec
                     ["select * from reset_password_tokens where token = ?" (uuid/as-uuid token)])))

(defn update [token password]
  (let [reset-token (find-by-token token)
        salt (common/generate-salt)]
    (jdbc/update! db/conn-spec "users" {:passhash (digest/md5 (str salt password))
                                        :salt salt}
                  ["email = ?" (:email reset-token)])
    (jdbc/delete! db/conn-spec "reset_password_tokens" ["token = ?" (uuid/as-uuid token)])))

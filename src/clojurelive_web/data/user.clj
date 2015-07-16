(ns clojurelive-web.data.user
  (:require [clojure.java.jdbc :as jdbc]
            [clojurelive-web.db :as db]
            [clojurelive-web.data.common :as common]
            [clj-uuid :as uuid]
            [digest :as digest]))

(defn cleanse [params]
  (-> params
      (dissoc :password)
      (dissoc :tos)))

(defn username-valid? [username]
  (empty? (re-find #"[^a-zA-Z0-9_]" username)))

(defn find-by-id [id]
  (first (jdbc/query db/conn-spec
                     ["select * from users where id = ?" id])))

(defn find-by-username [username]
  (first (jdbc/query db/conn-spec
                     ["select * from users where lower(username) = ?" (.toLowerCase username)])))

(defn find-by-email [email]
  (first (jdbc/query db/conn-spec
                     ["select * from users where lower(email) = ?" (.toLowerCase email)])))

(defn create [params]
  (let [salt (common/generate-salt)
        user (merge (cleanse params)
                    {:email (:email params)
                     :uuid (uuid/v1)
                     :created_at (java.sql.Timestamp. (.getTime (java.util.Date.)))
                     :passhash (digest/md5 (str salt (:password params)))
                     :salt salt
                     :role "user"})]
    (try
      (when-not (username-valid? (:username params))
        (throw (Exception. "username_invalid")))
      (when (not-empty (find-by-username (:username params)))
        (throw (Exception. "users_username_key")))
      (when (not-empty (find-by-email (:email params)))
        (throw (Exception. "users_email_key")))
      (jdbc/insert! db/conn-spec :users user)
      (catch Exception e
        (let [msg (.getMessage e)]
          {:errors {:username (or (when (re-find #"username_invalid" msg)
                                    {:message "username must only include a-z, A-Z, 0-9, _"})
                                  (when (re-find #"users_username_key" msg)
                                    {:message "username already exists"}))
                    :email (when (re-find #"users_email_key" msg)
                             {:message "email already exists"})}})))))

(defn authenticate [username password]
  (let [user (find-by-username username)]
    (= (:passhash user)
       (digest/md5 (str (:salt user) password)))))

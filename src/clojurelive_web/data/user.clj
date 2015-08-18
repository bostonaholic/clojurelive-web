(ns clojurelive-web.data.user
  (:require [clojurelive-web.db :as db]
            [clojurelive-web.data.common :as common]
            [datomic.api :as d]
            [digest :as digest]))

(defn username-valid? [username]
  (empty? (re-find #"[^a-zA-Z0-9_]" username)))

(defn find-by-username [username]
  (d/q '[:find (pull ?user [*]) .
         :in $ ?username
         :where
         [?user :user/username ?username]]
       (db/db)
       username))

(defn find-by-email [email]
  (d/q '[:find (pull ?user [*]) .
         :in $ ?email
         :where
         [?user :user/email ?email]]
       (db/db)
       email))

(defn create [params]
  (let [salt (common/generate-salt)
        user {:db/id (d/tempid :users)
              :user/email (:email params)
              :user/username (:username params)
              :user/uuid (d/squuid)
              :user/passhash (digest/md5 (str salt (:password params)))
              :user/salt salt}]
    (if-not (username-valid? (:user/username user))
      {:errors {:username {:message "username must only include a-z, A-Z, 0-9, _"}}}
      (try
        @(d/transact @db/conn [user])
        (catch Exception e
          (let [msg (.getMessage e)]
            {:errors {:username (when (re-find #":db.error/unique-conflict Unique conflict: :user/username" msg)
                                  {:message "username already exists"})
                      :email (when (re-find #":db.error/unique-conflict Unique conflict: :user/email" msg)
                               {:message "email already exists"})}}))))))

(defn authenticate [username password]
  (let [user (find-by-username username)]
    (= (:user/passhash user)
       (digest/md5 (str (:user/salt user) password)))))

(ns clojurelive-web.data.reset-password
  (:require [clojurelive-web.db :as db]
            [clojurelive-web.data.common :as common]
            [clj-uuid :as uuid]
            [datomic.api :as d]
            [digest :as digest]))

(defn create [email]
  (let [token (d/squuid)
        reset-password-data {:db/id (d/tempid :resetPasswordTokens)
                             :resetPasswordToken/email email
                             :resetPasswordToken/uuid token}]
    (when (first (d/datoms (db/db) :avet :user/email email))
      @(d/transact @db/conn [reset-password-data])
      token)))

(defn find-by-token [token]
  (d/q '[:find (pull ?e [*]) .
         :in $ ?token
         :where
         [?e :resetPasswordToken/uuid ?token]]
       (db/db)
       (uuid/as-uuid token)))

(defn update [token password]
  (let [reset-token (find-by-token token)
        salt (common/generate-salt)]
    (d/transact @db/conn [{:db/id [:user/email (:email reset-token)]
                           :user/passhash (digest/md5 (str salt password))
                           :user/salt salt}

                          [:db.fn/retractEntity [:resetPasswordToken/uuid (uuid/as-uuid token)]]])))

(ns clojurelive-web.scripts.create-db
  (:require [clojure.java.jdbc :as jdbc]
            [clojurelive-web.db :as db]))

;;
;; $ createuser clojurelive -P # clojurelive_dev
;; $ createdb clojurelive_web_dev -O clojurelive
;;
(defn create-table-users []
  (jdbc/db-do-commands
   db/conn-spec
   (jdbc/create-table-ddl :users
                          [:id :serial "PRIMARY KEY"]
                          [:uuid :uuid "UNIQUE NOT NULL"]
                          [:created_at :date "NOT NULL"]
                          [:username "VARCHAR(32)" "UNIQUE NOT NULL"]
                          [:email :text "UNIQUE NOT NULL"]
                          [:passhash "VARCHAR(32)" "NOT NULL"]
                          [:salt "VARCHAR(32)" "NOT NULL"]
                          [:role :text])
   "CREATE INDEX users_username_index ON users (username)"
   "CREATE INDEX users_email_index ON users (email)"))

(defn create-table-topics []
  (jdbc/db-do-commands
   db/conn-spec
   (jdbc/create-table-ddl :topics
                          [:id :serial "PRIMARY KEY"]
                          [:uuid :uuid "UNIQUE NOT NULL"]
                          [:created_at :date "NOT NULL"]
                          [:submitter_id :integer "REFERENCES users"]
                          [:title :text "NOT NULL"]
                          [:content :text]
                          [:type :text "NOT NULL"])
   "CREATE INDEX topics_uuid_index ON topics (uuid)"))

(defn create-table-reset-password-tokens []
  (jdbc/db-do-commands
   db/conn-spec
   (jdbc/create-table-ddl "reset_password_tokens"
                          [:id :serial "PRIMARY KEY"]
                          [:created_at :date "NOT NULL"]
                          [:email :text "NOT NULL"]
                          [:token :uuid "UNIQUE NOT NULL"])
   "CREATE INDEX reset_password_tokens_token_index ON reset_password_tokens (token)"))

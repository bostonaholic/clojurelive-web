(ns clojurelive-web.scripts.create-db
  (:require [clojure.java.jdbc :as jdbc]
            [clojurelive-web.db :as db]))

;;
;; $ createuser clojurelive -P # clojurelive_dev
;; $ createdb clojurelive_web_dev -O clojurelive
;;
(defn install-ci-text-module []
  (jdbc/db-do-commands
   (jdbc/execute! db/conn-spec ["CREATE EXTENSION IF NOT EXISTS citext;"])))

(defn create-table-users []
  (jdbc/db-do-commands
   db/conn-spec
   (jdbc/create-table-ddl :users
                          [:id :serial "PRIMARY KEY"]
                          [:uuid :uuid "UNIQUE NOT NULL"]
                          [:created_at :timestamp "NOT NULL"]
                          [:username "VARCHAR(32)" "UNIQUE NOT NULL"]
                          [:email :citext "UNIQUE NOT NULL"]
                          [:passhash "VARCHAR(32)" "NOT NULL"]
                          [:salt "VARCHAR(32)" "NOT NULL"]
                          [:role :text])
   "CREATE UNIQUE INDEX users_lower_username_index ON users (lower(username));"))

(defn create-table-topics []
  (jdbc/db-do-commands
   db/conn-spec
   (jdbc/create-table-ddl :topics
                          [:id :serial "PRIMARY KEY"]
                          [:uuid :uuid "UNIQUE NOT NULL"]
                          [:created_at :timestamp "NOT NULL"]
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
                          [:created_at :timestamp "NOT NULL"]
                          [:email :citext "NOT NULL"]
                          [:token :uuid "UNIQUE NOT NULL"])
   "CREATE UNIQUE INDEX reset_password_tokens_token_index ON reset_password_tokens (token);"))

(defn create-table-comments []
  (jdbc/db-do-commands
   db/conn-spec
   (jdbc/create-table-ddl :comments
                          [:id :serial "PRIMARY KEY"]
                          [:uuid :uuid "UNIQUE NOT NULL"]
                          [:created_at :timestamp "NOT NULL"]
                          [:topic_id :integer "REFERENCES topics"]
                          [:submitter_id :integer "REFERENCES users"]
                          [:content :text "NOT NULL"])))

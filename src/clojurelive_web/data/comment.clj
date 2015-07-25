(ns clojurelive-web.data.comment
  (:require [clojure.java.jdbc :as jdbc]
            [clojurelive-web.data.user :as user]
            [clojurelive-web.data.topic :as topic]
            [clojurelive-web.db :as db]
            [clj-uuid :as uuid]))

(defn create [username topic-uuid comment]
  (let [user (user/find-by-username username)
        topic (topic/find-by-uuid topic-uuid)
        comment (merge comment
                       {:uuid (uuid/v1)
                        :created_at (java.sql.Timestamp. (.getTime (java.util.Date.)))
                        :topics_id (:id topic)
                        :users_id (:id user)})]
    (jdbc/insert! db/conn-spec :comments comment)))

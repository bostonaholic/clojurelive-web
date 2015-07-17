(ns clojurelive-web.data.topic
  (:require [clojure.java.jdbc :as jdbc]
            [clojurelive-web.data.user :as user]
            [clojurelive-web.db :as db]
            [clj-uuid :as uuid]))

(defn assoc-submitter-username-for-topic [topic]
  (assoc-in topic
            [:submitter :username]
            (:username (user/find-by-id (:submitter_id topic)))))

(defn for-uuid [uuid]
  (let [topic (first (jdbc/query db/conn-spec
                                 ["SELECT * FROM topics WHERE uuid = ?" (uuid/as-uuid uuid)]))]
    (assoc-submitter-username-for-topic topic)))

(defn find-by-uuid [uuid]
  (first (jdbc/query db/conn-spec
                     ["SELECT * FROM topics WHERE uuid = ?" (uuid/as-uuid uuid)])))

(defn create [username topic]
  (let [user (user/find-by-username username)
        topic (merge topic
                     {:uuid (uuid/v1)
                      :created_at (java.sql.Timestamp. (.getTime (java.util.Date.)))
                      :submitter_id (:id user)})]
    (first (jdbc/insert! db/conn-spec :topics topic))))

(defn newest [offset limit]
  (let [topics (jdbc/query db/conn-spec
                           ["SELECT * FROM topics ORDER BY created_at DESC OFFSET ? LIMIT ?" offset limit])]
    (map assoc-submitter-username-for-topic topics)))

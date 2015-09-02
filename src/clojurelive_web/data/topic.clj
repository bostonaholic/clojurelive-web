(ns clojurelive-web.data.topic
  (:require [clojurelive-web.db :as db]
            [datomic.api :as d]
            [clj-uuid :as uuid])
  (:import (java.net URI)))

(defn for-uuid [uuid]
  (d/q '[:find (pull ?topic [* {:topic/submitter [*]
                                :topic/comments [* {:comment/submitter [*]}]}]) .
         :in $ ?uuid
         :where
         [?topic :topic/uuid ?uuid]]
       (db/db)
       (uuid/as-uuid uuid)))

(defn create-link [username link]
  (let [topic-uuid (d/squuid)
        link {:db/id (d/tempid :topics)
              :topic/uuid topic-uuid
              :topic/submitter [:user/username username]
              :topic/title (:title link)
              :topic/url (URI. (:url link))}]
    @(d/transact @db/conn [link])
    topic-uuid))

(defn create-text [username text]
  (let [topic-uuid (d/squuid)
        text {:db/id (d/tempid :topics)
              :topic/uuid topic-uuid
              :topic/submitter [:user/username username]
              :topic/title (:title text)
              :topic/content (:content text)}]
    @(d/transact @db/conn [text])
    topic-uuid))

(defn newest [offset limit]
  (->> (d/q '[:find [(pull ?topic [* {:topic/submitter [*]}]) ...]
              :where
              [?topic :topic/uuid]]
            (db/db))
       (sort-by :db/id)
       (reverse)
       (drop offset)
       (take limit)))

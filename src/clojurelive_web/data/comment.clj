(ns clojurelive-web.data.comment
  (:require [clojurelive-web.db :as db]
            [datomic.api :as d]
            [clj-uuid :as uuid]))

(defn create [username comment]
  (let [comment {:db/id (d/tempid :comments)
                 :comment/body (:body comment)
                 :comment/uuid (d/squuid)
                 :comment/submitter [:user/username username]
                 :topic/_comments [:topic/uuid (uuid/as-uuid (:parent-uuid comment))]}
        tx (d/transact @db/conn [comment])]
    @tx))

(ns clojurelive-web.scripts.create-db
  (:require [clojurelive-web.db :as db]
            [datomic.api :as d]))

(defn -main [& args]
  (d/create-database db/url)
  (System/exit 0))

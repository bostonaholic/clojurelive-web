(ns clojurelive-web.view.home
  (:require [clojurelive-web.view.topic :as topic-view]))

(defn show [session topics]
  (topic-view/list session topics))

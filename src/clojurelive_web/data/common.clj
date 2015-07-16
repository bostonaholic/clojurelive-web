(ns clojurelive-web.data.common
  (:require [clj-uuid :as uuid]))

(defn generate-salt []
  (apply str (take 13 (repeatedly
                       #(rand-nth "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")))))

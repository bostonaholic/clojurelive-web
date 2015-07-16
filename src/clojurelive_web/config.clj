(ns clojurelive-web.config
  (:require [clojure.java.io :as io]
            [nomad :refer [defconfig]]))

(defconfig config (io/resource "config/config.edn"))

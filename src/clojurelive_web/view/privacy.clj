(ns clojurelive-web.view.privacy
  (:require [clojurelive-web.view.common :as view]
            [hiccup.page :as h]))

(defn show [session]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)
             [:h1 "Privacy Policy"]
             [:h3 "TODO"]
             [:a {:href "https://github.com/ClojureLive/clojurelive-web"} "Open a PR"]
             view/footer
             view/google-analytics]]))

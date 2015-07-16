(ns clojurelive-web.view.terms
  (:require [clojurelive-web.view.common :as view]
            [hiccup.page :as h]))

(defn show [session]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)
             [:h1 "Terms of Service"]
             [:h3 "TODO"]
             [:a {:href "https://github.com/ClojureLive/clojurelive-web"} "Open a PR"]
             view/footer
             view/google-analytics]]))

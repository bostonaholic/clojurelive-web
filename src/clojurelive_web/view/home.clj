(ns clojurelive-web.view.home
  (:require [clojurelive-web.view.common :as view]
            [hiccup.page :as h]))

(defn show [session topics]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)

             [:div.site-actions
              [:a {:href (str "/link/new")} "+ Submit a new link"]
              [:br]
              [:a {:href (str "/text/new")} "+ Submit a new text post"]]

             [:ol#topics
              (for [topic topics]
                [:li.topic
                 [:div.topic-title
                  (view/render-title topic)]
                 [:div.topic-submitter
                  [:small (str "submitted by " (:username (:submitter topic)))]]
                 [:div.topic-actions
                  [:small
                   [:strong
                    [:a {:href (str "/t/" (:uuid topic))} "comment"]]]]])]

             view/footer]]))

(ns clojurelive-web.view.topic
  (:require [clojurelive-web.view.common :as view]
            [hiccup.page :as h]
            [hiccup.util :as hiccup-util]))

(defn listing [session topics]
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
                  [:small (str "submitted by " (:submitter topic))]]
                 [:div.topic-actions
                  [:small
                   [:strong
                    [:a {:href (str "/t/" (:uuid topic))} "comment"]]]]])]

             view/footer
             view/google-analytics]]))

(defn show [session topic]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)

             [:div.site-actions
              [:a {:href (str "/link/new")} "+ Submit a new link"]
              [:br]
              [:a {:href (str "/text/new")} "+ Submit a new text post"]]

             [:div#topic
              [:div.topic-title
               [:h2
                (view/render-title topic)]]

              [:div.topic-submitter
               [:small (str "submitted by " (:submitter topic))]]

              [:div.topic-content
               (view/render-content topic)]]

             (if (:clojurelive/username session)
               [:form {:action (str "/t/" (:uuid topic) "/comment") :method "POST"}
                [:div.form-group
                 [:label {:for "content"} "Comment:"]
                 [:textarea.text-content {:type "text" :name "content" :required "required"}]]

                [:div.form-group
                 [:button {:type "submit"} "Submit"]]]
               [:a {:href "/login"} "You must be logged in to comment."])

             [:div#topic-comments
              [:h3 "Comments"]
              (for [comment (:comments topic)]
                [:div.comment
                 [:div.comment-submitter
                  [:small (:username (:submitter comment))]]
                 [:div.comment-content
                  [:p (view/prettify (hiccup-util/escape-html (:content comment)))]]])]

             view/footer
             view/google-analytics]]))

(defn new-link [session]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)

             [:form {:action (str "/link/new") :method "POST"}
              [:div.form-group
               [:label {:for "title"} "Title:"]
               [:input.link-title {:type "text" :name "title" :required "required"}]]
              [:div.form-group
               [:label {:for "content"} "URL:"]
               [:input.link-content {:type "text" :name "content" :required "required"}]]

              [:div.form-group
               [:button {:type "submit"} "Submit"]]]]]))

(defn new-text [session]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)

             [:form {:action (str "/text/new") :method "POST"}
              [:div.form-group
               [:label {:for "title"} "Title:"]
               [:input.text-title {:type "text" :name "title" :required "required"}]]
              [:div.form-group
               [:label {:for "content"} "Text:"]
               [:textarea.comment-content {:type "text" :name "content"}]]

              [:div.form-group
               [:button {:type "submit"} "Submit"]]]]]))

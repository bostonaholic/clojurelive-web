(ns clojurelive-web.view.topic
  (:require [clojurelive-web.view.common :as view]
            [hiccup.page :as h]))

(defn show [session topic]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)

             [:div.site-actions
              [:a {:href (str "/link/new")} "+ Submit a new link"]
              [:br]
              [:a {:href (str "/text/new")} "+ Submit a new text post"]]

             [:div.topic-title
              [:h2
               (view/render-title topic)]]

             [:div.topic-body
              (view/render-body topic)]

             view/footer]]))

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
               [:textarea.text-content {:type "text" :name "content"}]]

              [:div.form-group
               [:button {:type "submit"} "Submit"]]]]]))

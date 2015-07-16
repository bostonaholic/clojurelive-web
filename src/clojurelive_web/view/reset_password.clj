(ns clojurelive-web.view.reset-password
  (:require [clojurelive-web.view.common :as view]
            [hiccup.page :as h]))

(defn show [session]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)

             [:form {:action "/reset-password" :method "POST"}
              [:div.form-group
               [:label {:for "Email"} "Email:"]
               [:input.form-control {:type "text" :name "email" :required "required"}]]

              [:div.form-group
               [:button {:type "submit"} "Submit"]]]

             view/footer]]))

(defn new [session uuid]
  (h/html5 [:html
            view/head
            [:body
             (view/navbar session)

             [:form {:action (str "/reset-password/" uuid) :method "POST"}
              [:div.form-group
               [:label {:for "password"} "Password:"]
               [:input.form-control {:type "password" :name "password" :required "required"}]]

              [:div.form-group
               [:button {:type "submit"} "Submit"]]]

             view/footer]]))

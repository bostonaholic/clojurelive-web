(ns clojurelive-web.view.login
  (:require [clojurelive-web.view.common :as view]
            [hiccup.page :as h]))

(defn show
  ([session] (show session nil))
  ([session opts]
   (h/html5 [:html
             view/head
             [:body
              (view/navbar session)

              (when (:errors opts)
                [:span.alizarin (:errors opts)])
              [:form {:action "/login" :method "POST"}
               [:div.form-group
                [:label {:for "username"} "Username:"]
                [:input.form-control {:type "text" :name "username" :required "required"}]]
               [:div.form-group
                [:label {:for "password"} "Password:"]
                [:input.form-control {:type "password" :name "password" :required "required"}]]

               [:div.form-group
                [:button {:type "submit"} "Submit"]]

               [:div.form-group
                [:a {:href "/reset-password"} "Forgot your password?"]]]

              view/footer
              view/google-analytics]])))

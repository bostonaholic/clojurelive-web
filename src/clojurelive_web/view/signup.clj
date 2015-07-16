(ns clojurelive-web.view.signup
  (:require [clojurelive-web.view.common :as view]
            [hiccup.page :as h]))

(defn show
  ([session] (show session nil))
  ([session opts]
   (h/html5 [:html
             view/head
             [:body
              (view/navbar session)

              [:form {:action "/signup" :method "POST"}
               [:div {:class (if (:username (:errors opts)) "form-group has-error" "form-group")}
                [:label {:for "username"} "Username:"]
                [:input {:type "text" :name "username" :maxlength 32 :required "required"}]
                (when (:username (:errors opts))
                  [:span.alizarin (:message (:username (:errors opts)))])]
               [:div {:class (if (:email (:errors opts)) "form-group has-error" "form-group")}
                [:label {:for "email"} "Email:"]
                [:input {:type "email" :name "email" :required "required"}]
                (when (:email (:errors opts))
                  [:span.alizarin (:message (:email (:errors opts)))])]
               [:div.form-group
                [:label {:for "password"} "Password:"]
                [:input.form-control {:type "password" :name "password" :required "required"}]]
               [:div.form-group
                [:input {:type "checkbox" :name "tos" :required "required"}]
                "I agree to the "
                [:a {:href "/terms"} "Terms of Service"]
                " and "
                [:a {:href "/conduct"} "Code of Conduct"]]

               [:div.form-group
                [:button {:type "submit"} "Submit"]]]

              view/footer
              view/google-analytics]])))

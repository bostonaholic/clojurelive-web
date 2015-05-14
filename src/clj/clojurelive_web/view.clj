(ns clojurelive-web.view
  (:require [hiccup.page :as h]))

(def head
  [:head
   [:title "Clojure Live - Up-to-date news on Clojure, ClojureScript, and Datomic"]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]

   (h/include-css "/css/vendor/normalize.css"
                  "/css/vendor/bootstrap.min.css"
                  "/css/flat-ui.min.css"
                  "/css/main.css")
   (h/include-js "/vendor/js/jquery.min.js"
                 "/vendor/js/flat-ui.min.js")])

(def body
  [:body
   [:div.container
    [:div#headline
     [:h1
      [:span "Clojure Live"]
      [:small "Up-to-date news on Clojure, ClojureScript, and Datomic"]]]

    [:form#main-subscribe-form {:action "/subscribe" :method "post"}
     [:div.form-group
      [:label.sr-only {:for "email"} "Your email"]
      [:input.form-control {:type "email" :placeholder "Your email" :name "email" :require "required"}]]
     [:input.btn.btn-primary {:type "submit" :value "Subscribe"}]]]

   [:footer
    [:div.container
     [:div.pull-left
      [:span "&copy; 2015"]]
     [:div.pull-right
      [:span "Made with "]
      [:span.fui-heart.alizarin]
      [:span " in Clojure."]]]]

   [:script {:type "text/javascript"}
    "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');ga('create', 'UA-62980400-1', 'auto');ga('send', 'pageview');"]

   (h/include-js "/js/main.js")])

(defn splash []
  (h/html5 [:html
            head
            body]))

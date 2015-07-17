(ns clojurelive-web.view.common
  (:require [clojure.string :as string]
            [hiccup.page :as h]
            [hiccup.util :as hiccup-util]))

(def head
  [:head
   [:title "ClojureLive - Up-to-date news on Clojure, ClojureScript, and Datomic"]
   [:meta {:name "description" :content "Up-to-date news on Clojure, ClojureScript, and Datomic"}]
   [:meta {:name "keywords" :content "clojure, clojurescript, datomic, forum"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]

   [:link {:rel "apple-touch-icon" :sizes "57x57" :href "/apple-touch-icon-57x57.png"}]
   [:link {:rel "apple-touch-icon" :sizes "60x60" :href "/apple-touch-icon-60x60.png"}]
   [:link {:rel "apple-touch-icon" :sizes "72x72" :href "/apple-touch-icon-72x72.png"}]
   [:link {:rel "apple-touch-icon" :sizes "76x76" :href "/apple-touch-icon-76x76.png"}]
   [:link {:rel "apple-touch-icon" :sizes "114x114" :href "/apple-touch-icon-114x114.png"}]
   [:link {:rel "apple-touch-icon" :sizes "120x120" :href "/apple-touch-icon-120x120.png"}]
   [:link {:rel "apple-touch-icon" :sizes "144x144" :href "/apple-touch-icon-144x144.png"}]
   [:link {:rel "apple-touch-icon" :sizes "152x152" :href "/apple-touch-icon-152x152.png"}]
   [:link {:rel "apple-touch-icon" :sizes "180x180" :href "/apple-touch-icon-180x180.png"}]
   [:link {:rel "icon" :type "image/png" :href "/favicon-32x32.png" :sizes "32x32"}]
   [:link {:rel "icon" :type "image/png" :href "/android-chrome-192x192.png" :sizes "192x192"}]
   [:link {:rel "icon" :type "image/png" :href "/favicon-96x96.png" :sizes "96x96"}]
   [:link {:rel "icon" :type "image/png" :href "/favicon-16x16.png" :sizes "16x16"}]
   [:link {:rel "manifest" :href "/manifest.json"}]
   [:meta {:name "msapplication-TileColor" :content "#da532c"}]
   [:meta {:name "msapplication-TileImage" :content "/mstile-144x144.png"}]
   [:meta {:name "theme-color" :content "#ffffff"}]

   (h/include-css "/css/normalize.css"
                  "/css/main.css")])

(defn navbar [session]
  [:nav {:role "navigation"}
   [:div.pull-right
    (if (:clojurelive/username session)
      [:div
       [:span (:clojurelive/username session)]
       [:span " | "]
       [:a {:href "/logout"} "Logout"]]
      [:div
       [:a {:href "/signup"} "Sign Up"]
       [:span " | "]
       [:a {:href "/login"} "Log In"]])]
   [:h1#logo
    [:a {:href "/"} "Clojure Live"]]])

(def footer
  [:footer
   [:div.pull-right
    [:span "Made with "]
    [:span.alizarin "<3"]
    [:span " in Clojure."]]
   [:div.pull-left
    [:span "&copy; 2015"]]
   [:div.center
    [:a {:href "https://twitter.com/ClojureLive" :target "_blank"} "@ClojureLive"]]])

(def google-analytics
  [:script {:type "text/javascript"}
   "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-62980400-1', 'auto');
ga('send', 'pageview');"])

(defn prettify [content]
  (string/replace content #"(\r\n)+" "</p><p>"))

(defmulti render-title :type)
(defmethod render-title :default [_] nil)
(defmethod render-title "link" [this]
  [:a {:href (:content this) :target "blank"} (hiccup-util/escape-html (:title this))])
(defmethod render-title "text" [this]
  [:a {:href (str "/t/" (:uuid this))} (hiccup-util/escape-html (:title this))])

(defmulti render-body :type)
(defmethod render-body :default [_]
  [:hr])
(defmethod render-body "text" [this]
  (list
   [:p (prettify (hiccup-util/escape-html (:content this)))]
   [:hr]))

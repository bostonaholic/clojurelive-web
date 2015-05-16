(ns clojurelive-web.splash
  (:import [goog.net XhrIo]))

(defn ^:export main []
  (.log js/console "%cSubscribe to Clojure Live: subscribe@clojurelive.com" "color: #1abc9c;font-size: 24px;"))

(defn post [url data]
  (XhrIo.send url nil "POST" data nil nil))

(defn subscribe [email]
  (post "/subscribe" (str "email=" email)))

(.submit (js/$ "#main-subscribe-form")
         (fn [evt handler]
           (this-as this
                    (.preventDefault evt)
                    (subscribe (.val (js/$ "[name=email]")))
                    (.addClass (js/$ this) "hide")
                    (.addClass (js/$ "#thank-you") "in"))))

(main)

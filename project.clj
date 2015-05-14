(defproject clojurelive-web "0.1.0-SNAPSHOT"
  :description "https://www.clojurelive.com"
  :url "https://github.com/ClojureLive/clojurelive-web"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [;; server
                 [org.clojure/clojure "1.7.0-beta3"]
                 [compojure "1.3.4"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [hiccup "1.0.5"]
                 [postmark "1.1.0" :exclusions [org.clojure/clojure]]
                 [environ "1.0.0"]

                 ;; client
                 [org.clojure/clojurescript "0.0-3269"]
                 [org.omcljs/om "0.8.8"]]
  :min-lein-version "2.4.0"
  :plugins [[lein-cljsbuild "1.0.4"]]
  :hooks [leiningen.cljsbuild]
  :main clojurelive-web.web
  :source-paths ["src/clj" "src/cljs"]
  :uberjar-name "clojurelive-web-standalone.jar"
  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js"]
  :cljsbuild {:builds {:dev {:source-paths ["src/cljs"]
                             :compiler     {:output-to     "resources/public/js/main.js"
                                            ;; :output-dir "resources/public/js/build-output-dev"
                                            ;; :source-map "resources/public/js/main.js.map"
                                            :optimizations :whitespace}}
                       :minify {:source-paths ["src/cljs"]
                                :compiler     {:output-to "resources/public/js/main.js"
                                               :optimizations :advanced}}}})

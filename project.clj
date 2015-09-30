(defproject clojurelive-web "0.1.0"
  :description "The code that runs Clojure Live"
  :url "https//www.clojurelive.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [jarohen/nomad "0.7.1"]
                 [org.immutant/web "2.0.2"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-devel "1.4.0"]
                 [compojure "1.3.4"]
                 [com.datomic/datomic-free "0.9.5206" :exclusions [joda-time]]
                 [io.rkn/conformity "0.3.5" :exclusions [datomic-free]]
                 [danlentz/clj-uuid "0.1.6"]
                 [digest "1.4.4"]
                 [hiccup "1.0.5"]
                 [postmark "1.1.0" :exclusions [org.clojure/clojure]]
                 [environ "1.0.0"]]
  :min-lein-version "2.0.0"
  :uberjar-name "clojurelive-web-standalone.jar"
  :main clojurelive-web.core
  :profiles {:uberjar {:aot [clojurelive-web.core]}}
  :aliases {"create-db" ["run" "-m" "clojurelive-web.scripts.create-db"]})

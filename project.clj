
(defproject clj-php "0.0.1"
  :description "Clojure to PHP compiler"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [midje "1.3.1"]
                 [com.stuartsierra/lazytest "1.2.3"]]
  :dev-dependencies [[lein-midje "1.0.8"]]
  :repositories {"stuart" "http://stuartsierra.com/maven2"}
  :main clj-php.core
  :uberjar-name "clj-php.jar")



(defproject clj-php "0.0.3"
  :description "Clojure to PHP compiler"
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :dev-dependencies [[com.stuartsierra/lazytest "1.2.3"]
                     [lein-midje "1.0.8"]
                     [midje "1.3.1"]]
  :repositories {"stuart" "http://stuartsierra.com/maven2"}
  :main clj-php.core
  :uberjar-name "clj-php.jar")


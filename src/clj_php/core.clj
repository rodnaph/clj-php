
(ns clj-php.core
  (:gen-class)
  (:use clj-php.exprs))

(defn php-includes []
  "<?php namespace clojure\\lang;include 'src/php/bootstrap.php';")

(defn -main [& args]
  (let [path (first args)]
    (println (php-includes)
             (parse-file "src/clojure/core.cljp")
             (parse-file path))))


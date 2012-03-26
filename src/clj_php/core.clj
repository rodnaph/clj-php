
(ns clj-php.core
  (:gen-class)
  (:use clj-php.exprs))

(defn parse-file
  "Parse a cljp file"
  [path]
  (let [exprs (format "'(%s)" (slurp path))]
    (apply parse-body (load-string exprs))))

(defn php-includes []
  "<?php namespace clojure\\core;include 'php/bootstrap.php';")

(defn -main [& args]
  (let [path (first args)]
    (println (format "%s%s"
                     (php-includes)
                     (parse-file path)))))


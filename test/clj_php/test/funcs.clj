
(ns clj-php.test.funcs
  (:use clj-php.funcs
        midje.sweet))

(facts "about function names"
  (parse-func-name "foo") => "$foo"
  (parse-func-name 'println) => "\\clojure\\core::println"
  (parse-func-name "println") => "\\clojure\\core::println"
  (parse-func-name "*") => "\\clojure\\core::multiply")

(facts "about functions as arguments"
  (parse-func-arg "foo") => "$foo"
  (parse-func-arg "+") => "\\clojure\\core::$add")


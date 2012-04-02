
(ns clj-php.test.funcs
  (:use clj-php.funcs
        midje.sweet))

(facts "about function names"
  (parse-func-name "foo") => "ns::$def->foo"
  (parse-func-name 'println) => "\\clojure\\core::$def->println"
  (parse-func-name "println") => "\\clojure\\core::$def->println"
  (parse-func-name "*") => "\\clojure\\core::$def->multiply")

(facts "about functions as arguments"
  (parse-func-name "foo") => "ns::$def->foo"
  (parse-func-name "+") => "\\clojure\\core::$def->add")

(facts "about local function vars"
  (with-local-args ["foo" 'bar]
    (parse-func-name 'bar) => "$bar"
    (parse-func-name 'foo) => "$foo"
    (parse-func-name "foo") => "$foo")
  (parse-func-name "foo") => "ns::$def->foo")


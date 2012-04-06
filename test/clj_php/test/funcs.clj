
(ns clj-php.test.funcs
  (:use clj-php.funcs
        midje.sweet))

(facts "about function names"
  (parse-func-name "foo") => "ns::$def->foo"
  (parse-func-name 'foo) => "ns::$def->foo"
  (parse-func-name "*") => "ns::$def->multiply")

(facts "about functions as arguments"
  (parse-func-name "foo") => "ns::$def->foo"
  (parse-func-name "+") => "ns::$def->add")

(facts "about local function vars"
  (with-local-args ["foo" 'bar]
    (parse-func-name 'bar) => "$bar"
    (parse-func-name 'foo) => "$foo"
    (parse-func-name "foo") => "$foo")
  (parse-func-name "foo") => "ns::$def->foo")

(facts "about functions with require prefixes"
  (parse-func-name "foo/bar") => "ns::$def->foo->bar")


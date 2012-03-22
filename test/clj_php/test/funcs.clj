
(ns clj-php.test.funcs
  (:use clj-php.funcs
        midje.sweet))

(facts "about function calls"
  (parse-func '(foo 1 2)) => "foo(1, 2)"
  (parse-func '(foo a 1)) => "foo($a, 1)")

(facts "about function call arguments"
  (to-func-arg 1) => "1"
  (to-func-arg 'b) => "$b"
  (parse-func-args '[1 2]) => "1, 2"
  (parse-func-args '[a b]) => "$a, $b")

(facts "about literal strings"
  (parse-func '(foo "hello")) => "foo(\"hello\")")

(facts "about function names"
  (parse-func-name "*") => "\\clojure\\core\\multiply")


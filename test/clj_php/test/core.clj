
(ns clj-php.test.core
  (:use clj-php.core
        midje.sweet))

(facts "about defns"
  (parse-defn '(defn foo [])) => "function foo() {}"
  (parse-defn '(defn foo [a b])) => "function foo($a, $b) {}")

(facts "about defn arguments"
  (parse-defn-args '[a]) => "$a"
  (parse-defn-args '[foo bar]) => "$foo, $bar")

(facts "about function calls"
  (parse-func '(foo 1 2)) => "foo(1, 2)"
  (parse-func '(foo a 1)) => "foo($a, 1)")

(facts "about function call arguments"
  (to-func-arg 1) => "1"
  (to-func-arg 'b) => "$b"
  (parse-func-args '[1 2]) => "1, 2"
  (parse-func-args '[a b]) => "$a, $b")


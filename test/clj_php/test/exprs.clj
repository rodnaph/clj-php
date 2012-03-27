
(ns clj-php.test.exprs
  (:use clj-php.exprs
        midje.sweet))

(facts "about defns"
  (parse-defn '(defn foo [])) => "$foo = function() {return null;};"
  (parse-defn '(defn foo [a b])) => "$foo = function($a, $b) {return null;};")

(facts "about defn arguments"
  (parse-defn-args '[a]) => "$a"
  (parse-defn-args '[foo bar]) => "$foo, $bar")

(facts "about def'ing vars"
  (parse-def '(def x [1 2 3])) => "$x = new \\clojure\\core\\Vector(1, 2, 3);"
  (parse-def '(def x 123)) => "$x = 123;")

(facts "about vectors"
  (parse-vector '[[1 2] [3 4]]) => "new \\clojure\\core\\Vector(new \\clojure\\core\\Vector(1, 2), new \\clojure\\core\\Vector(3, 4))"
  (parse-vector '[1 2 3]) => "new \\clojure\\core\\Vector(1, 2, 3)")

(facts "about parsing expression bodies"
  (parse-body '(def x 1) '(defn foo [x])) => "$x = 1;$foo = function($x) {return null;};"
  (parse-body '(def x 1) '(def y 2)) => "$x = 1;$y = 2;")

(facts "about let bindings"
  (parse-let '(let [x 1 y 2] (* x y))) => "$x = 1;$y = 2;\\clojure\\core::multiply($x, $y);"
  (parse-let '(let [x 1])) => "$x = 1;")

(facts "about string literals"
  (parse-expr '(foo "bar")) => "$foo(\"bar\");"
  (parse-expr "foo bar") => "\"foo bar\"")


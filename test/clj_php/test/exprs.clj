
(ns clj-php.test.exprs
  (:use clj-php.exprs
        midje.sweet))

(facts "about defns"
  (parse-defn '(defn foo [])) => "ns::$def->foo = function() {return null;};"
  (parse-defn '(defn foo [x] (+ x x))) => "ns::$def->foo = function($x) {return ns::$def->add($x, $x);};"
  (parse-defn '(defn foo [a b])) => "ns::$def->foo = function($a, $b) {return null;};")

(facts "about defn arguments"
  (parse-defn-args '[a]) => "$a"
  (parse-defn-args '[foo bar]) => "$foo, $bar")

(facts "about def'ing vars"
  (parse-def '(def x [1 2 3])) => "ns::$def->x = new \\clojure\\lang\\Vector(1, 2, 3);"
  (parse-def '(def x 123)) => "ns::$def->x = 123;")

(facts "about vectors"
  (parse-vector '[[1 2] [3 4]]) => "new \\clojure\\lang\\Vector(new \\clojure\\lang\\Vector(1, 2), new \\clojure\\lang\\Vector(3, 4))"
  (parse-vector '[1 2 3]) => "new \\clojure\\lang\\Vector(1, 2, 3)")

(facts "about parsing expression bodies"
  (parse-body '(def x 1) '(defn foo [x])) => "ns::$def->x = 1;ns::$def->foo = function($x) {return null;};"
  (parse-body '(def x 1) '(def y 2)) => "ns::$def->x = 1;ns::$def->y = 2;")

(facts "about let bindings"
  (parse-let '(let [x 1 y 2] (* x y))) => "ns::$def->x = 1;ns::$def->y = 2;ns::$def->multiply(ns::$def->x, ns::$def->y);"
  (parse-let '(let [x 1])) => "ns::$def->x = 1;")

(facts "about string literals"
  (parse-expr '(foo "bar")) => "ns::$def->foo(\"bar\");"
  (parse-expr "foo bar") => "\"foo bar\"")

(facts "about creating objects"
  (parse-expr '(Foo. 123)) => "new \\Foo(123);")

(facts "about calling methods on objects"
  )

(facts "about constructors"
  (constructor? 'Foo.) => true
  (constructor? "Foo.") => true)


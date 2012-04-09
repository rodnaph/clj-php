
(ns clj-php.test.exprs
  (:use clj-php.exprs
        midje.sweet))

(facts "about defns"
  (parse-defn '(defn foo [x & y])) => "ns::$def->foo = function() {$__args = func_get_args();$x = array_shift($__args);$y = $__args;return null;}"
  (parse-defn '(defn foo [])) => "ns::$def->foo = function() {$__args = func_get_args();return null;};"
  (parse-defn '(defn foo [x] (+ x x))) => "ns::$def->foo = function() {$__args = func_get_args();$x = array_shift($__args);return ns::$def->add($x, $x);};"
  (parse-defn '(defn foo [a b])) => "ns::$def->foo = function() {$__args = func_get_args();$a = array_shift($__args);$b = array_shift($__args);return null;};")

(facts "about def'ing vars"
  (parse-def '(def x [1 2 3])) => "ns::$def->x = new \\clojure\\lang\\Vector(1, 2, 3);"
  (parse-def '(def x 123)) => "ns::$def->x = 123;")

(facts "about vectors"
  (parse-vector '[[1 2] [3 4]]) => "new \\clojure\\lang\\Vector(new \\clojure\\lang\\Vector(1, 2), new \\clojure\\lang\\Vector(3, 4))"
  (parse-vector '[1 2 3]) => "new \\clojure\\lang\\Vector(1, 2, 3)")

(facts "about parsing expression bodies"
  (parse-body '(def x 1) '(defn foo [x])) => "ns::$def->x = 1;ns::$def->foo = function() {$__args = func_get_args();$x = array_shift($__args);return null;};"
  (parse-body '(def x 1) '(def y 2)) => "ns::$def->x = 1;ns::$def->y = 2;")

(facts "about let bindings"
  (parse-let '(let [x 1 y 2] (* x y))) => "ns::$def->x = 1;ns::$def->y = 2;ns::$def->multiply(ns::$def->x, ns::$def->y);"
  (parse-let '(let [x 1])) => "ns::$def->x = 1;")

(facts "about string literals"
  (parse-expr '(foo "bar")) => "ns::$def->foo(\"bar\");"
  (parse-expr "foo bar") => "\"foo bar\"")

(facts "about creating objects"
  (parse-expr '(Foo. "baz")) => "new \\Foo(\"baz\");"
  (parse-expr '(Foo. 123)) => "new \\Foo(123);")

(facts "about calling methods on objects"
  (parse-expr '(.bar foo 123)) => "ns::$def->foo->bar(123);")

(facts "about constructors"
  (constructor? 'Foo.) => true
  (constructor? "Foo.") => true)

(facts "about method calls"
  (method? ".bar") => true
  (method? '.bar) => true)


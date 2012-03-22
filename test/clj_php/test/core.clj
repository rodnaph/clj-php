
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
  (parse-func '(foo 1 2)) => "foo(1, 2);"
  (parse-func '(foo a 1)) => "foo($a, 1);")

(facts "about function call arguments"
  (to-func-arg 1) => "1"
  (to-func-arg 'b) => "$b"
  (parse-func-args '[1 2]) => "1, 2"
  (parse-func-args '[a b]) => "$a, $b")

(facts "about def'ing vars"
  (parse-def '(def x "123")) => "$x = \"123\";")

(facts "about namespaces"
  (parse-ns '(ns foo.bar)) => "namespace foo\\bar;")

(facts "about parsing expression bodies"
  (parse-body '(def x "1") '(defn foo [x])) => "$x = \"1\";function foo($x) {}"
  (parse-body '(def x "1") '(def y "2")) => "$x = \"1\";$y = \"2\";")

(facts "about parsing files"
  (parse-file "test/example.cljp") => "namespace cljphp\\example;$x = 123;function double($x) {multiply($x, 2);}println(double($x));")


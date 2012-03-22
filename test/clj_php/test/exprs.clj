
(ns clj-php.test.exprs
  (:use clj-php.exprs
        midje.sweet))

(facts "about defns"
  (parse-defn '(defn foo [])) => "function foo() {return null;}"
  (parse-defn '(defn foo [a b])) => "function foo($a, $b) {return null;}")

(facts "about defn arguments"
  (parse-defn-args '[a]) => "$a"
  (parse-defn-args '[foo bar]) => "$foo, $bar")

(facts "about def'ing vars"
  (parse-def '(def x "123")) => "$x = \"123\";")

(facts "about namespaces"
  (parse-ns '(ns foo.bar)) => "namespace foo\\bar;")

(facts "about parsing expression bodies"
  (parse-body '(def x "1") '(defn foo [x])) => "$x = \"1\";function foo($x) {return null;}"
  (parse-body '(def x "1") '(def y "2")) => "$x = \"1\";$y = \"2\";")



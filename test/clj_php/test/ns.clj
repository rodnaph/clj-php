
(ns clj-php.test.ns
  (:use clj-php.ns
        midje.sweet))

(facts "about namespaces"
  (parse-ns '(ns foo.bar)) => "namespace foo\\bar;")
  ;(parse-ns '(ns foo.bar)) => "namespace foo;class bar extends \\clojure\\core { public static $fn; }bar::$fn = new \\clojure\\core\\FMap();")



(ns clj-php.test.core
  (:use clj-php.core
        midje.sweet))

(facts "about parsing files"
  (parse-file "test/example.cljp") => "namespace cljphp\\example;$x = 123;$double = function($x) {return \\clojure\\core::multiply($x, 2);};\\clojure\\core::println($double($x));")


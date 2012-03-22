
(clj-php.test.lists
  (:use clj-php.lists
        midje.sweet))

(facts "about lists"
  (parse-list '(a b c)) => "array($a, $b, $c)")


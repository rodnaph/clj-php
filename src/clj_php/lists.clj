
(ns clj-php.lists
  (:use clj-php.funcs))

(defn parse-list
  "Parse a list"
  [lst]
  (format "new \\clojure\\core\\List(%s)"
          (parse-args (map to-func-arg lst))))
 

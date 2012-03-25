
(ns clj-php.funcs)

(def clojure-core-functions {
  "*" "multiply"
  "/" "divide"
  "+" "add"
  "-" "subtract"
  "println" "println"
  "str" "str"
  "map" "map"
  "first" "first"
})

(defn- parse-name
  [func-name name-format]
  (let [str-name (str func-name)
        core-name (get clojure-core-functions str-name)]
    (if (nil? core-name)
        (str "$" str-name)
        (format name-format core-name))))

(defn parse-func-name
  "Resolves clojure names to php functions"
  [func-name]
  (parse-name func-name "\\clojure\\core::%s"))

(defn parse-func-arg
  "Resolves function names as arguments"
  [func-name]
  (parse-name func-name "\\clojure\\core::$%s"))


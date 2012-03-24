
(ns clj-php.funcs)

(def clojure-core-functions {
  "*" "multiply"
  "/" "divide"
  "+" "add"
  "-" "subtract"
  "println" "println"
  "str" "str"
})

(defn parse-func-name
  "Resolves clojure names to php functions"
  [func-name]
  (let [str-name (str func-name)
        core-name (get clojure-core-functions str-name)]
    (if (nil? core-name)
        str-name
        (format "\\clojure\\core\\%s" core-name))))



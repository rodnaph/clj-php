
(ns clj-php.funcs)

(declare parse-func)

(def clojure-core-functions {
  "*" "multiply"
  "/" "divide"
  "+" "add"
  "-" "subtract"
  "println" "println"
  "str" "str"
})

(defn parse-args
  "Parse args into argument string"
  [args]
  (let [arg-str (reduce #(str %1 ", " %2) "" args)]
    (if (> (count arg-str) 2)
        (subs arg-str 2)
        arg-str)))

(defn to-func-arg
  "Transform function argument (number or scalar)"
  [arg]
  (let [s (str arg)]
    (cond (list? arg) (parse-func arg)
          (string? arg) (str "\"" arg "\"")
          (re-matches #"\d+" s) s
          :else (str "$" s))))

(defn parse-func-args
  "Parse arguments to a function call"
  [args]
  (parse-args 
    (map to-func-arg args)))

(defn parse-func-name
  "Resolves clojure names to php functions"
  [func-name]
  (let [str-name (str func-name)
        core-name (get clojure-core-functions str-name)]
    (if (nil? core-name)
        str-name
        (format "\\clojure\\core\\%s" core-name))))

(defn parse-func 
  "Parse a function call"
  [[func-name & args]]
  (format "%s(%s)"
          (parse-func-name func-name)
          (parse-func-args args)))


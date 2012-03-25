
(ns clj-php.funcs)

(def func-map {
  "*" "multiply"
  "/" "divide"
  "+" "add"
  "-" "subtract"
})

(def core-funcs (concat (vals func-map) [
  "println" "str" "map" "first"
]))

(defn- resolve-name
  "Switches certain core function names"
  [func-name]
  (let [new-name (get func-map (str func-name))]
    (if (nil? new-name)
      (str func-name)
      new-name)))

(defn- parse-name
  [func-name name-format]
  (let [str-name (resolve-name func-name)]
    (if (some #{str-name} core-funcs)
        (format name-format str-name)
        (str "$" str-name))))

(defn parse-func-name
  "Resolves clojure names to php functions"
  [func-name]
  (parse-name func-name "\\clojure\\core::%s"))

(defn parse-func-arg
  "Resolves function names as arguments"
  [func-name]
  (parse-name func-name "\\clojure\\core::$%s"))


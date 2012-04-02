
(ns clj-php.funcs)

(def ^:dynamic *local-args* [])

(def format-call "\\clojure\\core::$def->%s")

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

(defn- in?
  [needle haystack]
  (some #{needle} haystack))

(defn- parse-name
  [func-name]
  (let [str-name (resolve-name func-name)]
    (cond (in? str-name core-funcs) 
            (format format-call str-name)
          (in? str-name *local-args*)
            (str "$" str-name)
          :else 
            (str "ns::$def->" str-name))))

; Public

(defn parse-func-name
  "Resolves clojure names to php functions"
  [func-name]
  (parse-name func-name))

(defmacro with-local-args [args & body]
  `(binding [*local-args* (map str ~args)]
     (do ~@body)))


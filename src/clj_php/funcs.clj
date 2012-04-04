
(ns clj-php.funcs)

(def ^:dynamic *local-args* [])

(def format-local "$%s")
(def format-ns-def "ns::$def->%s")

(def func-map {
  "*" "multiply"
  "/" "divide"
  "+" "add"
  "-" "subtract"
})

(defn- resolve-name
  "Switches certain lang function names"
  [func-name]
  (let [new-name (get func-map (str func-name))]
    (if (nil? new-name)
      (str func-name)
      new-name)))

(defn- in?
  "Indicates of the needle is in the haystack"
  [needle haystack]
  (some #{needle} haystack))

(defn- parse-name
  "Parse a name to either a mapped function, local var, of namespace def"
  [func-name]
  (let [str-name (resolve-name func-name)]
    (format
      (if (in? str-name *local-args*) 
          format-local
          format-ns-def) str-name)))

; Public

(defn parse-func-name
  "Resolves clojure names to php functions"
  [func-name]
  (parse-name func-name))

(defmacro with-local-args [args & body]
  `(binding [*local-args* (map str ~args)]
     (do ~@body)))



(ns clj-php.exprs
  (:use clj-php.funcs))

(def ^:dynamic *is-statement* true)

(declare parse-body parse-expr)

(defn parse-args
  "Parse args into argument string"
  [args]
  (let [arg-str (reduce #(str %1 ", " %2) "" args)]
    (if (> (count arg-str) 2)
        (subs arg-str 2)
        arg-str)))

(defn parse-def
  "Parse a definition"
  [[_ def-name value]]
  (format "$%s = %s;"
          def-name
          (parse-expr value)))

(defn parse-defn-args 
  "Parse an argument list"
  [args]
  (parse-args
    (map (partial str "$") args)))

(defn parse-defn 
  "Parse a function definition"
  [[_ func-name args & body]]
  (let [body-str (apply parse-body body)]
    (format "$%s = function(%s) {return %s};"
            func-name
            (parse-defn-args args)
            (if (> (count body-str) 0)
                body-str 
                "null;"))))

(defn parse-ns
  "Parse a namespace declaration"
  [[_ ns-name]]
  (format "namespace %s;"
          (.replace (str ns-name) "." "\\")))

(defn parse-let
  "Parse a let binding"
  [[_ args & body]]
  (let [def-str (apply str
                  (map #(parse-def (cons nil %1)) 
                    (partition 2 args)))
       body-str (apply str
                  (map parse-expr body))]
    (str def-str body-str)))

(defn parse-func-args
  "Parse arguments to a function call"
  [args]
  (parse-args 
    (map parse-expr args)))

(defn parse-func 
  "Parse a function call"
  [[func-name & args]]
  (binding [*is-statement* false]
    (format "%s(%s)"
            (parse-func-name func-name)
            (parse-func-args args))))

(defn parse-list
  "Parse a list"
  [expr]
  (condp = (str (first expr))
    "def" (parse-def expr)
    "defn" (parse-defn expr)
    "ns" (parse-ns expr)
    "let" (parse-let expr)
    (str (parse-func expr) 
         (if *is-statement* ";"))))

(defn parse-vector
  "Parses a vector"
  [expr]
  (binding [*is-statement* false]
    (format "new \\clojure\\core\\Vector(%s)"
            (parse-args (map parse-expr expr)))))

(defn parse-expr
  "Parses an expression"
  [expr]
  (cond (list? expr) (parse-list expr)
        (vector? expr) (parse-vector expr)
        (string? expr) (str "\"" expr "\"")
        (re-matches #"\d+" (str expr)) expr
        :else (parse-func-arg expr))) ; check for ref required? ie. \clojure\core::$add, not $add

(defn parse-body 
  "Parse a function body"
  [& exprs] 
  (reduce str 
    (map parse-expr exprs)))


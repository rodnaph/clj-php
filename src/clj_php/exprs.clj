
(ns clj-php.exprs
  (:use clj-php.funcs))

(declare parse-body)

(defn parse-def
  "Parse a definition"
  [[_ def-name value]]
  (format "$%s = \"%s\";"
          def-name
          value))

(defn parse-defn-args 
  "Parse an argument list"
  [args]
  (parse-args
    (map (partial str "$") args)))

(defn parse-defn 
  "Parse a function definition"
  [[_ func-name args & body]]
  (let [body-str (apply parse-body body)]
    (format "function %s(%s) {return %s}"
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

(defn to-expr
  "Parses an expression"
  [expr]
  (let [[type & rest] expr]
    (condp = (str type)
      "def" (parse-def expr)
      "defn" (parse-defn expr)
      "ns" (parse-ns expr)
      "" ""
      (str (parse-func expr) ";"))))

(defn parse-body 
  "Parse a function body"
  [& exprs] 
  (reduce str 
    (map to-expr exprs)))



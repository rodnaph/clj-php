
(ns clj-php.core
  (:gen-class))

(declare parse-body parse-func)

(defn parse-ns
  "Parse a namespace declaration"
  [[_ ns-name]]
  (format "namespace %s;"
          (.replace (str ns-name) "." "\\")))

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
  (format "$%s = \"%s\";"
          def-name
          value))

(defn to-func-arg
  "Transform function argument (number or scalar)"
  [arg]
  (let [s (str arg)]
    (cond (list? arg) (parse-func arg)
          (re-matches #"\d+" s) s
          :else (str "$" s))))

(defn parse-func-args
  "Parse arguments to a function call"
  [args]
  (parse-args 
    (map to-func-arg args)))

(defn parse-func 
  "Parse a function call"
  [[func-name & args]]
  (format "%s(%s)"
          func-name
          (parse-func-args args)))

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

(defn parse-file
  "Parse a cljp file"
  [path]
  (let [exprs (format "'(%s)" (slurp path))]
    (apply parse-body (load-string exprs))))

(defn -main [& args]
  (let [path (first args)]
    (println (parse-file path))))


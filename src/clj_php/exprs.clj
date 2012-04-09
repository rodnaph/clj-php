
(ns clj-php.exprs
  (:use clj-php.funcs
        clj-php.ns
        clj-php.fs
        clj-php.vars))

(def format-def "ns::$def->%s = %s;")
(def format-defn "ns::$def->%s = function() {$__args = func_get_args();%sreturn %s};")
(def format-func "%s(%s)")
(def format-constructor "new \\%s(%s)")
(def format-method "ns::$def->%s->%s(%s)")
(def format-vector "new \\clojure\\lang\\Vector(%s)")
(def format-arg "$%s = array_shift($__args);")

(def ^:dynamic *is-statement* true)

(declare parse-file parse-body parse-expr)

; Arguments

(defn parse-args
  "Parse args into argument string"
  [args]
  (apply str (interpose ", " args)))

(defn parse-defn-args 
  "Parse an argument list"
  [args]
  (apply str
    (map #(format format-arg %) args)))

(defn parse-func-names
  "Parse arguments to a function call"
  [args]
  (->> args
       (map parse-expr)
       (interpose ", ")
       (apply str)))

; Definitions

(defn parse-def
  "Parse a definition"
  [[_ def-name value]]
  (format format-def
          def-name
          (parse-expr value)))

(defn parse-defn 
  "Parse a function definition"
  [[_ func-name args & body]]
  (with-local-args args
    (let [body-str (apply parse-body body)]
      (format format-defn
              func-name
              (parse-defn-args args)
              (if (> (count body-str) 0)
                  body-str 
                  "null;")))))

; Bindings

(defn parse-let
  "Parse a let binding"
  [[_ args & body]]
  (let [def-str (apply str
                  (map #(parse-def (cons nil %1)) 
                    (partition 2 args)))
       body-str (apply str
                  (map parse-expr body))]
    (str def-str body-str)))

; Interop

(defn constructor?
  [func-name]
  (not (nil?
    (re-matches #".*\.$" (str func-name)))))

(defn method?
  [method-name]
  (not (nil?
    (re-matches #"^\..*" (str method-name)))))

(defn- parse-constructor
  [func-name args]
  (let [str-name (str func-name)]
    (format format-constructor
            (.substring str-name 0 (dec (count str-name)))
            (parse-func-names args))))

(defn- parse-method
  [[method-name obj-name & args]]
  (format format-method
          obj-name
          (.substring (str method-name) 1)
          (parse-func-names args)))

; Functions

(defn parse-func 
  "Parse a function call"
  [[func-name & args]]
  (binding [*is-statement* false]
    (cond (constructor? func-name) (parse-constructor func-name args)
          (method? func-name) (parse-method (cons func-name args))
          :else (format format-func
                        (parse-func-name func-name)
                        (parse-func-names args)))))

; Namespaces

(defn parse-ns
  "Parse a namespace declaration"
  [[_ ns-decl & body]]
  (str (parse-ns-includes parse-file body)
       (parse-ns-decl ns-decl)
       (parse-ns-body body)))

; Data structures

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
    (format format-vector
            (parse-args (map parse-expr expr)))))

; Expressions

(defn parse-expr
  "Parses an expression"
  [expr]
  (cond (list? expr) (parse-list expr)
        (vector? expr) (parse-vector expr)
        (string? expr) (str "\"" expr "\"")
        (re-matches #"\d+" (str expr)) expr
        :else (parse-func-name expr))) ; check for ref required? ie. \clojure\lang::$add, not $add

(defn parse-body 
  "Parse a function body"
  [& exprs] 
  (reduce str 
    (map parse-expr exprs)))

(defn parse-file
  "Parse a cljp file, if it hasn't already been"
  [path]
  (binding [*cljp-file* path]
    (let [exprs (format "'(%s)" (slurp-resource path))]
      (apply parse-body (load-string exprs)))))


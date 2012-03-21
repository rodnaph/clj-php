
(ns clj-php.core)

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
    (if (re-matches #"\d+" s) s
        (str "$" s))))

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

(defn parse-body 
  "Parse a function body"
  [& args] 
  "")

(defn parse-defn-args 
  "Parse an argument list"
  [args]
  (parse-args
    (map (partial str "$") args)))

(defn parse-defn 
  "Parse a function definition"
  [[_ func-name args] & body]
  (format "function %s(%s) {%s}"
          func-name
          (parse-defn-args args)
          (parse-body body)))

(defn -main [& args])



(ns clj-php.core
  (:gen-class)
  (:use clj-php.exprs
        clj-php.fs))

(def include-paths [
  "lang/Base"
  "lang/Php"
  "lang/DefMap"
  "lang"
  "lang/ISeq"
  "lang/ASeq"
  "lang/Cons"
  "lang/Seq"
  "lang/LazySeq"
  "lang/Vector"
  "lang/CList"
])

(defn php-includes []
  (->> include-paths
       (map #(str "src/php/clojure/" % ".php"))
       (map slurp-resource)
       (map #(.substring % 5))
       (reduce str "")))

(defn compile-cljp
  [path]
  (str "<?php "
       (php-includes)
       (parse-file "src/clojure/core.cljp")
       (parse-file path)))

(defn -main [& args]
  (let [path (first args)]
    (println (compile-cljp path))))


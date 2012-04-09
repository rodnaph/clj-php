
(ns clj-php.fs
  (:use clojure.java.io))

; Public

(defn slurp-resource
  "Try to read a resource from current Jar file, and fall back
   to the filesystem for development"
  [resource-name]
  (let [jar-resource-name (resource (.substring resource-name 4))]
    (slurp (if (nil? jar-resource-name)
               resource-name
               jar-resource-name))))


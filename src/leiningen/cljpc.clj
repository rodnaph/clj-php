
(ns leiningen.cljpc
  (:use clj-php.core))

(defn- default-path
  "Returns the default include path for the project"
  [project]
  (format "src/%s/core.cljp"
          (:name project)))

(defn cljpc
  "Compile the specified path, or the default"
  [project & args]
  (let [path (first args)
        path (if (nil? path) (default-path project) path)]
    (println (compile-cljp path))))


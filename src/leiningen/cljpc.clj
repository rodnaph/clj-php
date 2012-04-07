
(ns leiningen.cljpc
  (:use clj-php.core))

(defn cljpc
  "Compile the current project as ClojurePHP"
  [project]
  (let [path (format "%s/%s/core.cljp"
                     (:source-path project)
                     (:name project))]
    (println (compile-cljp path))))


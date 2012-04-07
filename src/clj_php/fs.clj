
(ns clj-php.fs)

; Public

(defn slurp-resource
  "Try to read a resource from current Jar file, and fall back
   to the filesystem for development"
  [resource-name]
  (try
    (-> (.getContextClassLoader (Thread/currentThread))
        (.getResourceAsStream (.substring resource-name 4))
        (java.io.InputStreamReader.)
        (slurp))
    (catch java.lang.NullPointerException npe
      (slurp resource-name))))



(ns clj-php.ns
  (:require [clojure.string :as str]))

(def decl-format (str "namespace %s;"
                    "class ns extends \\clojure\\core {"
                        "public static $def;"
                    "}"
                    "ns::$def = new \\clojure\\core\\DefMap();"))
(def format-use "ns::$def->__use(\\%s\\ns::$def);")

(def ^:dynamic *includes* (ref []))

(defn- to-php-ns
  [ns-decl]
  (.replace (str ns-decl) "." "\\"))

(defn- parse-ns-use
  ""
  [ns-name]
  (format format-use 
          (to-php-ns (first ns-name))))

(defn- parse-ns-require
  ""
  [body]
  "req")

(defn- to-ns-incl
  "Parse the includes body for the namespace"
  [[type & body]]
  (condp = type
    :use (parse-ns-use body)
    :require (parse-ns-require body)))

(defn- to-namespaces
  [acc [type & body]]
  (concat acc
    (condp = type
      :use body
      :require (map first body))))

; Public

(defn parse-ns-body
  "Parse the name declaration of a namespace"
  [body]
  (reduce str "" 
    (map to-ns-incl body)))

(defn parse-ns-decl
  "Parse the namespace declaration"
  [ns-decl]
  (format decl-format
          (to-php-ns ns-decl)))

(defn path-not-included?
  "Indicates of the path has been included yet"
  [path]
  (not (some (partial = path) 
             (deref *includes*))))

(defn ns-to-fs
  "Convert a namespace to its path on the file system"
  [ns-name]
  (str (str/replace ns-name #"\." "/")
       ".cljp"))

(defn parse-ns-includes
  "Parse any namespaces includes that need to be compiled"
  [parse-file body]
  (let [nss (->> (reduce to-namespaces [] body)
                 (filter path-not-included?)
                 (map ns-to-fs))]
    (reduce str
      (map #(parse-file %) nss))))


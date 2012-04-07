
(ns clj-php.ns
  (:require [clojure.string :as str]))

(def decl-format (str "namespace %s;"
                    "class ns extends \\clojure\\lang {"
                        "public static $def;"
                    "}"
                    "ns::$def = new \\clojure\\lang\\DefMap();"
                    "ns::$def->__use(\\clojure\\core\\ns::$def);"))
(def format-use "ns::$def->__use(\\%s\\ns::$def);")
(def format-require "ns::$def->__require('%s',\\%s\\ns::$def);")

(def ^:dynamic *includes* (ref []))

(defn- to-php-ns
  [ns-decl]
  (.replace (str ns-decl) "." "\\"))

(defn- parse-ns-use
  [ns-names]
  (map #(format format-use 
                (to-php-ns (first %)))
       ns-names))

(defn- parse-ns-require
  [ns-names]
  (map (fn [[ns-name _ req-name]]
         (format format-require
                 req-name
                 (to-php-ns ns-name))) ns-names))

(defn- to-namespaces
  [acc [type & body]]
  (concat acc
    (condp = type
      :use body
      :require (map first body))))

; Public

(defn to-ns-incl
  "Parse the includes body for the namespace"
  [[type & body]]
  (reduce str ""
    (condp = type
      :use (parse-ns-use body)
      :require (parse-ns-require body))))

(defn parse-ns-body
  "Parse the body of a namespace decl"
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


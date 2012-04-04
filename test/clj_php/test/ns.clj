
(ns clj-php.test.ns
  (:use clj-php.ns
        midje.sweet))

(facts "about namespaces"
  (parse-ns-decl 'foo.bar) => "namespace foo\\bar;class ns extends \\clojure\\lang {public static $def;}ns::$def = new \\clojure\\lang\\DefMap();ns::$def->__use(\\clojure\\core\\ns::$def);")

(facts "about namespace file system paths"
  (path-not-included? "foo/wumba.cljp") => true
  (ns-to-fs 'foo.bar) => "foo/bar.cljp")


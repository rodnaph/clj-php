
# clj-php

A very naive experiment on compiling Clojure to PHP.  Hmmm...

## Using

Simple example of defining and cailling a function:

```clojure
(ns examples.basic)

(defn double [x]
    (* 2 x))

(println (str "Double 2 is... " (double 2)))
```

You can compile and run this with:

```bash
lein uberjar
java -jar clj-php-0.0.1-standalone.jar ./examples/basic.cljp | php
```

Which should output:

```
Double 2 is... 4
```

## Features

At the moment only the basic of Clojure have been implemented, but hopefully this will be a growing list.

* defn
* namespaces (with :use and :require)
* PHP integration

## PHP Integration

Integration with PHP is handled (like in ClojureScript) through the *php* import to each namespace.  You
can then reference any functions from the standard PHP distributions.

```clojure
(println "Date is: " (php/date "dS F Y, H:i:s"))
```

## Tests

Tests written with Midje and PHPUnit, run them with...

```bash
lein midje
phpunit test/php
```

### Disclaimer

This is not meant to ever be an actually useful thing, compiling Clojure to PHP is insane.  It's just
a learning tool for me to get more familiar with Clojure.


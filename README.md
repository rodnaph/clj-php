
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
./bin/cljp examples/basic.cljp
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

You can also include other PHP libraries and use objects, much like the Java interop provided by Clojure.
Like this contrived database example:

```
(ns examples.objects)

(def cnn (DBConnection. "localhost" "root" ""))

(.query cnn "select * from table")
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

## TODO

I don't plan to implement *all* Clojure features, some of them don't make sense (like Futures, as PHP
has no thread support).  These are some things I am looking to implement though:

* destructuring
* tail recursion
* lazy sequences
* macros


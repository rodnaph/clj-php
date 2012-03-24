
# clj-php

A very naive experiment on compiling Clojure to PHP.  Hmmm...

## Using

Only the simplest of examples work at the moment, for example:

```clojure
(ns examples.basic)

(defn double [x]
    (* 2 x))

(println (str "Double 2 is..." (double 2)))
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

## Tests

Tests written with Midje and PHPUnit, run them with...

```bash
lein midje
phpunit tests/php
```


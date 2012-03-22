
# clj-php

A very naive experiment on compiling Clojure to PHP.  Hmmm...

## Using

Only the simplest of examples work at the moment.

```bash
lein uberjar
java -jar clj-php-0.0.1-standalone.jar ./examples/basic.cljp | php
```

Which should output:

```
Double 2 is...
4
```

## Tests

Tests written with Midje, run them with...

```bash
lein midje
```


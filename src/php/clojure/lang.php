<?php

namespace clojure;

use clojure\lang\ISeq;
use clojure\lang\Base;
use clojure\lang\DefMap;

class lang extends Base { public static $def; }

lang::$def = new DefMap();


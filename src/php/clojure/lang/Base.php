<?php

namespace clojure\lang;

class Base {

    private static $def;

    public static function __callStatic( $name, $args ) {
        return call_user_func_array(
            static::$def->$name,
            $args
        );
    }
 
}


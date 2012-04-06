<?php

namespace clojure\lang;

class Php {

    /**
     * Proxy functions to PHP core
     *
     * @param string $name
     * @param array $args
     */
    public function __call( $name, $args ) {
        return call_user_func_array( $name, $args );
    }

}


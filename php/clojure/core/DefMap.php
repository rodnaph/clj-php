<?php

namespace clojure\core;

class DefMap {

    private $defs = array();

    public function __set( $name, $value ) {
        $this->defs[ $name ] = $value;
    }

    public function __get( $name ) {
        return $this->defs[ $name ];
    }

    public function __call( $name, $args ) {
        return call_user_func_array( $this->defs[$name], $args );
    }

    public function __use( DefMap $def ) {
        $this->defs = array_merge(
            $this->defs,
            $def->defs()
        );
    }

    public function defs() {
        return $this->defs;
    }

}



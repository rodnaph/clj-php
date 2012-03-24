<?php

namespace clojure\core;

abstract class Seq {

    /**
     * @param array
     */
    private $items;

    /**
     * Create a new sequence
     *
     */
    public function __construct() {
    
        $this->items = func_get_args();

    }

    /**
     * Returns number of items in list
     *
     * @return integer
     */
    public function count() {

        return count( $this->items );

    }

}


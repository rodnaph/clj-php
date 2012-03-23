<?php

namespace clojure\core;

class CList implements \Countable {

    /**
     * @param array
     */
    private $items;

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


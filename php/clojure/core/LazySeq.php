<?php

namespace clojure\core;

class LazySeq extends Seq {

    /**
     * Function to apply when realising items
     */
    private $func;

    /**
     * Index of currently realised item
     */
    private $index;

    /**
     * Create a lazy sequence with a function to realise items, and
     * a number of items.
     *
     * @param Callable $func
     * @param array $items
     */
    public function __construct( $func, $items ) {
        $this->items = $items;
        $this->func = $func;
        $this->index = -1;
    }

    /**
     * When accessing items check they have been realised
     *
     * @param integer $n
     *
     * @return mixed
     */
    protected function nth( $n ) {
        if ( $n > $this->index ) {
            $this->realiseTo( $n );
        }
        return parent::nth( $n );
    }

    /**
     * Realises items from current index to new index
     *
     * @param integer $n
     */
    public function realiseTo( $n ) {
        $func = $this->func;
        while ( $this->index++ <= $n ) {
            $this[ $this->index ] = $func( $this[$this->index] );
        }
        $this->index--;
    }

}


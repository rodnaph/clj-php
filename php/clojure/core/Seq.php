<?php

namespace clojure\core;

abstract class Seq implements \ArrayAccess, ISeq {

    /**
     * @param array
     */
    protected $items;

    /**
     * Create a new sequence
     *
     */
    public function __construct() {
        $this->items = func_get_args();
    }

    /**
     * Returns the number of items in the sequence
     *
     * @return integer
     */
    public function count() {
        return count( $this->items );
    }

    /**
     * Return the first item from the sequence
     *
     * @return mixes
     */
    public function first() {
        return $this->nth( 0 );
    }

    /**
     * Returns a new sequence containing all but the first
     * item in the sequence
     *
     * @return LazySeq
     */
    public function rest() {
        return new LazySeq(
            function( $x ) { return $x; },
            array_slice( $this->items, 1 )
        );
    }

    protected function nth( $n ) {
        return isset( $this->items[$n] )
            ? $this->items[ $n ]
            : null;    
    }

    public function offsetExists( $offset ) {
        return isset( $this->items[$offset] );
    }

    public function offsetGet( $offset ) {
        return $this->nth( $offset );
    }

    public function offsetSet( $offset, $value ) {
        $this->items[ $offset ] = $value;
    }

    public function offsetUnset( $offset ) {
        unset( $this->items[$offset] );
    }

    public function cons( $item ) {}

}


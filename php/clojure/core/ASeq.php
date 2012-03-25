<?php

namespace clojure\core;

abstract class ASeq implements ISeq, \Countable {

    protected $items;

    public function __construct() {
        $this->items = func_get_args();
    }

    public function first() {
        return isset( $this->items[0] )
            ? $this->items[ 0 ]
            : null;
    }

    public function nxt() {}

    public function more() {}

    public function cons( $item ) {
        $class = new \ReflectionClass( get_called_class() );
        return $class->newInstanceArgs(
            array_merge( array($item), $this->items )
        );
    }

    public function count() {
        return count( $this->items );
    }

}


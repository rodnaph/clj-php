<?php

namespace clojure\lang;

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

    public function nxt() {
        $more = $this->more();
        return $more->count() == 0 ? null : $more;
    }

    protected function newInstance( $items ) {
        $class = new \ReflectionClass( get_called_class() );
        return $class->newInstanceArgs( $items );
    }

    public function more() {
        return $this->newInstance(
            array_slice( $this->items, 1 )
        );
    }

    public function cons( $item ) {
        return $this->newInstance(
            array_merge( array($item), $this->items )
        );
    }

    public function count() {
        return count( $this->items );
    }

    public function toArray() {
        return $this->items;
    }

}


<?php

namespace clojure\core;

interface ISeq {

    /**
     * Returns the first item in the sequence, or null
     *
     * @return mixed
     */
    public function first();

    public function nxt();

    public function more();

    /**
     * Puts the item at the start of this sequence, ie. becomes the next first()
     *
     * @param mixed $item
     */
    public function cons( $item );

}
 

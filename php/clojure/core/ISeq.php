<?php

namespace clojure\core;

interface ISeq {

    /**
     * Returns the first item in the sequence, or null
     *
     * @return mixed
     */
    public function first();

    /**
     * Returns a sequence representing everything in this sequence except
     * the first item.  This new sequence may be empty.
     *
     * @return ISeq
     */
    public function rest();

    /**
     * Puts the item at the start of this sequence, ie. becomes the next first()
     *
     * @param mixed $item
     */
    public function cons( $item );

}
 

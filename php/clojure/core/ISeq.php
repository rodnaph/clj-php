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
     * Returns a new sequence containing all but the first item
     * in the sequence, or null if there are no more items.
     *
     * @return ISeq
     */
    public function nxt();

    /**
     * Returns a new sequence (possibly empty) containing all but
     * the first item in this sequence.
     *
     * @return ISeq
     */
    public function more();

    /**
     * Returns a new sequence containing all the items in this sequence,
     * and the new item prepended.
     *
     * @param mixed $item
     */
    public function cons( $item );

}
 

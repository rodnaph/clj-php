<?php

namespace clojure\core;

class Cons extends ASeq {

    public function __construct( $first=null, ISeq $more=null ) {
        $this->first = $first;
        $this->more = $more;
    }

    /**
     * Returns the first item in this sequence, or null
     *
     * @return mixed
     */
    public function first() {
        return $this->first;
    }

    /**
     * Returns all the items in this sequence apart from the first as
     * a sequence. This new sequence could be empty.
     *
     * @return Cons
     */
    public function rest() {
        return $this->more
            ? $this->more
            : new Cons();
    }

    /**
     * Returns a new sequence which has the specified item first, and then
     * this sequence as the rest.
     *
     * @param mixed $item
     *
     * @return Cons
     */
    public function cons( $item ) {
        return new Cons( $item, $this );
    }

    /**
     * Returns the size of this sequence, this requires realising
     * all items in the sequence.
     *
     * @return integer
     */
    public function count() {
        return $this->first != null
            ? 1 + $this->rest()->count()
            : 0;
    }

}


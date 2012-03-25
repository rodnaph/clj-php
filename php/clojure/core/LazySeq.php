<?php

namespace clojure\core;

class LazySeq implements ISeq, \Countable {

    private $fn;

    private $sv;

    private $sequence;

    public function __construct( $fn ) {
        $this->fn = $fn;
    }

    protected function sequenceValue() {
        if ( $this->fn ) {
            $fn = $this->fn;
            $this->sv = $fn();
            $this->fn = null;
        }
        if ( $this->sv ) {
            return $this->sv;
        }
        return $this->sequence;
    }

    /**
     * Realises this lazy sequence
     *
     */
    protected function seq() {
        $this->sequenceValue();
        if ( $this->sv ) {
            $ls = $this->sv;
            while ( is_subclass_of($ls,'\clojure\core\LazySeq') ) {
                $ls = $ls->sequenceValue();
            }
            $this->sequence = \clojure\core\seq( $ls );
        }
        return $this->sequence;
    }

    /**
     * Returns the first item in the sequence, as ISeq
     *
     * @return mixed
     */
    public function first() {
        $this->seq();
        return $this->sequence
            ? $this->sequence->first()
            : null;
    }

    public function nxt() {
    }

    public function more() {}

    public function cons( $item ) {}

    public function count() {
        $this->seq();
        return $this->sequence
            ? $this->sequence->count()
            : 0;
    }

}


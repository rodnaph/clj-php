<?php

namespace clojure\lang;

class DefMap {

    public $defs = array();

    public function __construct() {

        $self = $this;

        $this->defs['add'] = function() {
            return array_reduce(
                func_get_args(),
                function( $x, $y ) { return $x + $y; },
                0
            );
        };

        $this->defs['multiply'] = function() {
            return array_reduce(
                func_get_args(),
                function( $x, $y ) { return $x * $y; },
                1
            );
        };

        $this->defs['divide'] = function() {
            $rest = func_get_args();
            $first = \array_shift( $rest );
            return array_reduce(
                $rest,
                function( $x, $y ) { return $x / $y; },
                $first
            );
        };

        $this->defs['subtract'] = function() {
            $rest = func_get_args();
            $first = \array_shift( $rest );
            return array_reduce(
                $rest,
                function( $x, $y ) { return $x - $y; },
                $first
            );
        };

        $this->defs['str'] = function() {
            return array_reduce(
                func_get_args(),
                function( $acc, $e ) { return $acc . $e; },
                ""
            );
        };

        $this->defs['seq'] = function( $obj ) {
            if ( is_subclass_of($obj,'\clojure\lang\ASeq') ) { return $obj; }
            if ( is_subclass_of($obj,'\clojure\lang\LazySeq') ) { return $obj->seq(); }
            return seqFrom( $obj );
        };

        $this->defs['apply'] = function( $func, $args ) {
            return call_user_func_array( $func, $args );
        };

        $this->defs['first'] = function( ISeq $seq ) {
            return $seq->first();
        };

        $this->defs['println'] = function() use ( $self ) {
            echo $self->defs['apply']( $self->defs['str'], func_get_args() ) . "\n"; 
        };

        $this->defs['cons'] = function( $item, ISeq $seq ) {
            return $seq->cons( $item );
        };

        $this->defs['map'] = function( $func, ISeq $seq ) {
            $first = $seq->first();
            return ( $first != null )
                ? new lang\Cons(
                     $func( $first ),
                     lang::$def->map( $func, $seq->more() )
                  )
                : null;
        };

    }

    protected function _seqFrom( $obj ) {
        throw new Exception( 'Cant create sequence from object: ' . $obj );
    }

    protected function _seqFor( $items, ISeq $orig ) {
        $class = new \ReflectionClass( get_class($orig) );
        return $class->newInstanceArgs( $items );
    }

    public function __set( $name, $value ) {
        $this->defs[ $name ] = $value;
    }

    public function __get( $name ) {
        return $this->defs[ $name ];
    }

    public function __call( $name, $args ) {
        return call_user_func_array( $this->defs[$name], $args );
    }

    public function __use( DefMap $def ) {
        $this->defs = array_merge(
            $this->defs,
            $def->defs()
        );
    }

    public function defs() {
        return $this->defs;
    }

}



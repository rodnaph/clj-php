<?php

namespace clojure\lang;

class DefMap {

    public $defs = array();

    public function __construct() {

        $self = $this;

        $this->defs['php'] = new Php();

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

    /**
     * Set a property magic method
     *
     * @param string $name
     * @param mixed $value
     */
    public function __set( $name, $value ) {
        $this->defs[ $name ] = $value;
    }

    /**
     * Get a value magic method
     *
     * @param string $name
     *
     * @return mixed
     */
    public function __get( $name ) {
        return $this->defs[ $name ];
    }

    /**
     * Allows calling functions magically
     *
     * @param string $name
     * @param array $args
     *
     * @return mixed
     */
    public function __call( $name, $args ) {
        return call_user_func_array( $this->defs[$name], $args );
    }

    /**
     * "use" another DefMap, which imports all properties from that
     * into this DefMap
     *
     * @param DefMap $def
     */
    public function __use( DefMap $def ) {
        $this->defs = array_merge(
            $this->defs,
            $def->defs()
        );
    }

    /**
     * "requires" another DefMap on this one by name
     *
     * @param string $name
     * @param DefMap $map
     */
    public function __require( $name, DefMap $map ) {
        $this->defs[ $name ] = $map;
    }

    /**
     * Returns all the properties defined in this DefMap
     *
     * @return array
     */
    public function defs() {
        return $this->defs;
    }

}



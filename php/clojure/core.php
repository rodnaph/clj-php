<?php

namespace clojure\core;

class FMap {
    private static $fn = array();
    public function __get( $name ) {
        return static::$fn[ $name ];
    }
    public function __set( $name, $value ) {
        static::$fn[ $name ] = $value;
    }
    public static function __callStatic( $name, $args ) {
        return call_user_func_array(
            static::$fn->$name,
            $args
        );
    }
}

namespace clojure;

use clojure\core\ISeq;
use clojure\core\FMap;

class core extends FMap { public static $fn; }

core::$fn = new \clojure\core\FMap();

core::$fn->add = function() {
    return array_reduce(
        func_get_args(),
        function( $x, $y ) { return $x + $y; },
        0
    );
};

core::$fn->multiply = function() {
    return array_reduce(
        func_get_args(),
        function( $x, $y ) { return $x * $y; },
        1
    );
};

core::$fn->divide = function() {
    $rest = func_get_args();
    $first = \array_shift( $rest );
    return array_reduce(
        $rest,
        function( $x, $y ) { return $x / $y; },
        $first
    );
};

core::$fn->subtract = function() {
    $rest = func_get_args();
    $first = \array_shift( $rest );
    return array_reduce(
        $rest,
        function( $x, $y ) { return $x - $y; },
        $first
    );
};

core::$fn->str = function() {
    return array_reduce(
        func_get_args(),
        function( $acc, $e ) { return $acc . $e; },
        ""
    );
};

core::$fn->seq = function( $obj ) {
    if ( is_subclass_of($obj,'\clojure\core\ASeq') ) {
        return $obj;
    }
    if ( is_subclass_of($obj,'\clojure\core\LazySeq') ) {
        return $obj->seq();
    }
    return seqFrom( $obj );
};

core::$fn->apply = function( $func, $args ) {
    return call_user_func_array( $func, $args );
};

core::$fn->first = function( ISeq $seq ) {
    return $seq->first();
};

core::$fn->println = function() {
    echo core::apply( core::$fn->str, func_get_args() ) . "\n"; 
};

core::$fn->cons = function( $item, ISeq $seq ) {
    return $seq->cons( $item );
};

core::$fn->map = function( $func, ISeq $seq ) {
    return _seqFor(
        array_map( $func, $seq->toArray() ),
        $seq
    );
};

////////////// internal /////////////////

function _seqFrom( $obj ) {
    throw new Exception( 'Cant create sequence from object: ' . $obj );
}

function _seqFor( $items, ISeq $orig ) {
    $class = new \ReflectionClass( get_class($orig) );
    return $class->newInstanceArgs( $items );
}


<?php

namespace clojure\core;

class Base {
    private static $def;
}

namespace clojure;

use clojure\core\ISeq;
use clojure\core\Base;
use clojure\core\DefMap;

class core extends Base { public static $def; }

core::$def = new DefMap();

core::$def->add = function() {
    return array_reduce(
        func_get_args(),
        function( $x, $y ) { return $x + $y; },
        0
    );
};

core::$def->multiply = function() {
    return array_reduce(
        func_get_args(),
        function( $x, $y ) { return $x * $y; },
        1
    );
};

core::$def->divide = function() {
    $rest = func_get_args();
    $first = \array_shift( $rest );
    return array_reduce(
        $rest,
        function( $x, $y ) { return $x / $y; },
        $first
    );
};

core::$def->subtract = function() {
    $rest = func_get_args();
    $first = \array_shift( $rest );
    return array_reduce(
        $rest,
        function( $x, $y ) { return $x - $y; },
        $first
    );
};

core::$def->str = function() {
    return array_reduce(
        func_get_args(),
        function( $acc, $e ) { return $acc . $e; },
        ""
    );
};

core::$def->seq = function( $obj ) {
    if ( is_subclass_of($obj,'\clojure\core\ASeq') ) { return $obj; }
    if ( is_subclass_of($obj,'\clojure\core\LazySeq') ) { return $obj->seq(); }
    return seqFrom( $obj );
};

core::$def->apply = function( $func, $args ) {
    return call_user_func_array( $func, $args );
};

core::$def->first = function( ISeq $seq ) {
    return $seq->first();
};

core::$def->println = function() {
    echo core::$def->apply( core::$def->str, func_get_args() ) . "\n"; 
};

core::$def->cons = function( $item, ISeq $seq ) {
    return $seq->cons( $item );
};

core::$def->map = function( $func, ISeq $seq ) {
    $first = $seq->first();
    return ( $first != null )
        ? new core\Cons(
             $func( $first ),
             core::$def->map( $func, $seq->more() )
          )
        : null;
};

////////////// internal /////////////////

function _seqFrom( $obj ) {
    throw new Exception( 'Cant create sequence from object: ' . $obj );
}

function _seqFor( $items, ISeq $orig ) {
    $class = new \ReflectionClass( get_class($orig) );
    return $class->newInstanceArgs( $items );
}


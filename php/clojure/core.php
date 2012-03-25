<?php

namespace clojure\core;

class core {
    public static $add, $multiply, $divide, $subtract, $str;
}

core::$add = function() {
    return array_reduce(
        func_get_args(),
        function( $x, $y ) { return $x + $y; },
        0
    );
};

core::$multiply = function() {
    return array_reduce(
        func_get_args(),
        function( $x, $y ) { return $x * $y; },
        1
    );
};

core::$divide = function() {
    $rest = func_get_args();
    $first = \array_shift( $rest );
    return array_reduce(
        $rest,
        function( $x, $y ) { return $x / $y; },
        $first
    );
};

core::$subtract = function() {
    $rest = func_get_args();
    $first = \array_shift( $rest );
    return array_reduce(
        $rest,
        function( $x, $y ) { return $x - $y; },
        $first
    );
};

core::$str = function() {
    return array_reduce(
        func_get_args(),
        function( $acc, $e ) { return $acc . $e; },
        ""
    );
};

function apply( $func, $args ) {
    return call_user_func_array( $func, $args );
}

function add() {
    return apply( core::$add, func_get_args() );
}

function multiply() {
    return apply( core::$multiply, func_get_args() );
}

function divide() {
    return apply( core::$divide, func_get_args() );
}

function subtract() {
    return apply( core::$subtract, func_get_args() );
}

function str() {
    return apply( core::$str, func_get_args() );
}

function map( $func, Seq $seq ) {
    return new LazySeq( $func, $seq );
}

function first( Seq $seq ) {
    return $seq->first();
}

function seq( $obj ) {
    if ( is_subclass_of($obj,'\clojure\core\ASeq') ) {
        return $obj;
    }
    if ( is_subclass_of($obj,'\clojure\core\LazySeq') ) {
        return $obj->seq();
    }
    return seqFrom( $obj );
}

function _seqFrom( $obj ) {
    throw new Exception( 'Cant create sequence from object: ' . $obj );
}

/**
 * Print a list of variables to stdout
 *
 */
function println() {
    echo apply( core::$str, func_get_args() ) . "\n"; 
}


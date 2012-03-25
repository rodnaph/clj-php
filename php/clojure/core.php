<?php

namespace clojure;

use clojure\core\ISeq;

class core {
    // @todo find way to remove the need for this
    public static $add, $multiply, $divide, $subtract, $str, $seq, $apply,
                  $map, $first, $println, $cons;
    public static function __callStatic( $name, $args ) {
        $func = core::$$name;
        return call_user_func_array( $func, $args );
    }
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

core::$seq = function( $obj ) {
    if ( is_subclass_of($obj,'\clojure\core\ASeq') ) {
        return $obj;
    }
    if ( is_subclass_of($obj,'\clojure\core\LazySeq') ) {
        return $obj->seq();
    }
    return seqFrom( $obj );
};

core::$apply = function( $func, $args ) {
    return call_user_func_array( $func, $args );
};

core::$first = function( ISeq $seq ) {
    return $seq->first();
};

core::$println = function() {
    echo core::apply( core::$str, func_get_args() ) . "\n"; 
};

core::$cons = function( $item, ISeq $seq ) {
    return $seq->cons( $item );
};

////////////// internal /////////////////

function _seqFrom( $obj ) {
    throw new Exception( 'Cant create sequence from object: ' . $obj );
}


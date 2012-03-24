<?php

namespace clojure\core;

class coreTest extends \PHPUnit_Framework_TestCase {

    public function testAddingSomeNumbers() {
        $this->assertEquals( 10, add(1,4,3,2) );
    }

    public function testMultiplyingSomeNumbers() {
        $this->assertEquals( 24, multiply(2,2,3,2) );
    }

    public function testDividingNumbers() {
        $this->assertEquals( 10, divide(100,2,5) );
    }

    public function testSubtractingSomeNumbers() {
        $this->assertEquals( 3, subtract(20,9,5,3) );
    }

    public function testStrConcatenatesAllArgumentsToString() {
        $this->assertEquals( "foo 1 bar", str("foo ", 1, " bar") );
    }

    public function testMapCanApplyAFunctionToASequence() {
        $seq = new Vector( 1, 2, 3 );
        $dbl = function( $x ) { return $x * 2; };
        $res = map( $dbl, $seq );
        $this->assertEquals( 2, $res[0] );
        $this->assertEquals( 4, $res[1] );
        $this->assertEquals( 6, $res[2] );
    }

}


<?php

namespace clojure;

require_once 'php/bootstrap.php';

class langTest extends \PHPUnit_Framework_TestCase {

    public function testAddingSomeNumbers() {
        $this->assertEquals( 10, lang::add(1,4,3,2) );
    }

    public function testMultiplyingSomeNumbers() {
        $this->assertEquals( 24, lang::multiply(2,2,3,2) );
    }

    public function testDividingNumbers() {
        $this->assertEquals( 10, lang::divide(100,2,5) );
    }

    public function testSubtractingSomeNumbers() {
        $this->assertEquals( 3, lang::subtract(20,9,5,3) );
    }

    public function testStrConcatenatesAllArgumentsToString() {
        $this->assertEquals( "foo 1 bar", lang::str("foo ", 1, " bar") );
    }

    public function testASeqReturnedAsIsFromSeq() {
        $seq = new lang\Cons();
        $this->assertSame( $seq, lang::seq($seq) );
    }

    public function testConsPrependsAnItemToASequence() {
        $seq = lang::cons( 1, new lang\Vector(2,3) );
        $this->assertEquals( 3, $seq->count() );
        $this->assertEquals( 1, $seq->first() );
    }

    public function testFirstReturnsFirstItemOfASequence() {
        $seq = new lang\Vector( 23, 45 );
        $this->assertEquals( 23, lang::first($seq) );
    }

}


<?php

namespace clojure;

require_once 'php/bootstrap.php';

class coreTest extends \PHPUnit_Framework_TestCase {

    public function testAddingSomeNumbers() {
        $this->assertEquals( 10, core::add(1,4,3,2) );
    }

    public function testMultiplyingSomeNumbers() {
        $this->assertEquals( 24, core::multiply(2,2,3,2) );
    }

    public function testDividingNumbers() {
        $this->assertEquals( 10, core::divide(100,2,5) );
    }

    public function testSubtractingSomeNumbers() {
        $this->assertEquals( 3, core::subtract(20,9,5,3) );
    }

    public function testStrConcatenatesAllArgumentsToString() {
        $this->assertEquals( "foo 1 bar", core::str("foo ", 1, " bar") );
    }

    public function testASeqReturnedAsIsFromSeq() {
        $seq = new core\Cons();
        $this->assertSame( $seq, core::seq($seq) );
    }

    public function testConsPrependsAnItemToASequence() {
        $seq = core::cons( 1, new core\Vector(2,3) );
        $this->assertEquals( 3, $seq->count() );
        $this->assertEquals( 1, $seq->first() );
    }

    public function testFirstReturnsFirstItemOfASequence() {
        $seq = new core\Vector( 23, 45 );
        $this->assertEquals( 23, core::first($seq) );
    }

}


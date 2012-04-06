<?php

namespace clojure\lang;

require_once 'src/php/bootstrap.php';

class MySeq extends Seq {}

class SeqTest extends \PHPUnit_Framework_TestCase {

    private $seq;

    protected function setUp() {
        $this->seq = new MySeq( 1, 2, 3 );
        $this->emp = new MySeq();
    }

    public function testFirstItemFromSequenceCanVeFetched() {
        $this->assertEquals( 1, $this->seq->first() );
    }

    public function testNullReturnedWhenNoFirstItemInSequence() {
        $this->assertNull( $this->emp->first() );
    }

    public function testCountReturnsSizeOfSequence() {
        $this->assertEquals( 3, $this->seq->count() );
    }

}


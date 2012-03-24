<?php

namespace clojure\core;

require_once 'php/bootstrap.php';

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

    public function testRestContainsAnotherSequenceWithAllButFirstItem() {
        $rest = $this->seq->rest();
        $this->assertInstanceOf( '\clojure\core\Seq', $rest );
        $this->assertEquals( 2, $rest->count() );
    }

}


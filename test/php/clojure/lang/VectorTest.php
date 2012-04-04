<?php

namespace clojure\lang;

require_once 'php/bootstrap.php';

class VectorTest extends \PHPUnit_Framework_TestCase {

    private $vector;

    protected function setUp() {
        $this->vector = new Vector( 1, 2, 3 );
    }

    public function testFirstItemCanBeFetched() {
        $this->assertEquals( 1, $this->vector->first() );
    }

    public function testCountOfVectorCanBeFetched() {
        $this->assertEquals( 3, $this->vector->count() );
    }

    public function testConsReturnsNewVectorWithItemPrepended() {
        $vector = $this->vector->cons( 0 );
        $this->assertEquals( 4, $vector->count() );
        $this->assertEquals( 0, $vector->first() );
    }

    public function testNxtReturnsANewSequenceWithAllButFirstItem() {
        $vector = $this->vector->nxt();
        $this->assertInstanceOf( '\clojure\lang\Vector', $vector );
        $this->assertEquals( 2, $vector->count() );
    }

    public function testNxtReturnsNullWhenNoMoreItems() {
        $vector = new Vector();
        $this->assertNull( $vector->nxt() );
    }

    public function testMoreReturnsNewVectorWithAllButFirstItem() {
        $vector = $this->vector->more();
        $this->assertInstanceOf( '\clojure\lang\Vector', $vector );
        $this->assertEquals( 2, $vector->count() );
    }

    public function testMoreReturnsEmptyVectorWhenNoItems() {
        $vector = new Vector();
        $more = $vector->more();
        $this->assertInstanceOf( '\clojure\lang\Vector', $more );
        $this->assertEquals( 0, $more->count() );
    }

}


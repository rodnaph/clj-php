<?php

namespace clojure\core;

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

}


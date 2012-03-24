<?php

namespace clojure\core;

require_once 'php/bootstrap.php';

class CListTest extends \PHPUnit_Framework_TestCase {

    private $list;

    protected function setUp() {
        $this->list = new CList(1,2,3);
    }

    public function testCountableIsImplemented() {
        is_subclass_of( $this->list, '\Countable' );
    }

    public function testReturnsNumberOfItemsInList() {
        $this->assertEquals( 3, $this->list->count() );
    }

}


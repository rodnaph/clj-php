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

}


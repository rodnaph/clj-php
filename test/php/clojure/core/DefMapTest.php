<?php

namespace clojure\core;

require_once 'php/bootstrap.php';

class DefMapTest extends \PHPUnit_Framework_TestCase {

    private $map;

    protected function setUp() {
        $this->map = new DefMap();
        $this->map->foo = function() { return 2; };
    }

    public function testFunctionsCanBeAddedToDefMap() {
        $this->assertEquals( 2, $this->map->foo() );
    }

    public function testDefMapCanUseDefsFromOtherMap() {
        $map = new DefMap();
        $map->foo = function() { return 3; };
        $this->map->__use( $map );
        $this->assertEquals( 3, $this->map->foo() );
    }

    public function testFunctionsCanBeAccessedAsProperties() {
        $this->assertNotNull( $this->map->foo );
    }

}


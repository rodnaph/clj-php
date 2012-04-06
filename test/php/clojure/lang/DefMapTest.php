<?php

namespace clojure\lang;

require_once 'src/php/bootstrap.php';

class DefMapTest extends \PHPUnit_Framework_TestCase {

    private $map;

    protected function setUp() {
        $this->map = new DefMap();
        $this->map->foo = function() { return 2; };
        $this->other = new DefMap();
        $this->other->foo = function() { return 3; };
    }

    public function testFunctionsCanBeAddedToDefMap() {
        $this->assertEquals( 2, $this->map->foo() );
    }

    public function testDefMapCanUseDefsFromOtherMap() {
        $this->map->__use( $this->other );
        $this->assertEquals( 3, $this->map->foo() );
    }

    public function testFunctionsCanBeAccessedAsProperties() {
        $this->assertNotNull( $this->map->foo );
    }

    public function testOtherNamespacesCanBeRequiredInByName() {
        $this->map->__require( 'baz', $this->other );
        $this->assertEquals( 3, $this->map->baz->foo() );
    }

}


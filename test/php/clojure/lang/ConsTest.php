<?php

namespace clojure\lang;

require_once 'php/bootstrap.php';

class ConsTest extends \PHPUnit_Framework_TestCase {

    public function testConsCanBeCreatedWithNothingAndIsEmpty() {
        $cons = new Cons();
        $this->assertEquals( 0, $cons->count() );
    }

    public function testConsCanBeCreatedWithAFirstItem() {
        $first = 123;
        $cons = new Cons( $first );
        $this->assertEquals( $first, $cons->first() );
    }

    public function testRestOfConsCreatedWithOnlyOneItemIsEmpty() {
        $cons = new Cons( 1 );
        $this->assertEquals( 0, $cons->rest()->count() );
    }

    public function testConsReturnsNewSequenceWithItemAsFirst() {
        $cons1 = new Cons( "foo" );
        $cons2 = $cons1->cons( "bar" );
        $this->assertEquals( 2, $cons2->count() );
        $this->assertEquals( "bar", $cons2->first() );
    }

}


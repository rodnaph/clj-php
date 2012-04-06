<?php

namespace clojure\lang;

class PhpTest extends \PHPUnit_Framework_TestCase {

    private $php;

    protected function setUp() {
        $this->php = new Php();
    }

    public function testPhpFunctionsCanBeCalledAsProperties() {
        $this->assertEquals( 
            '24th May 1981',
            $this->php->date('dS F Y',$this->php->strtotime('1981-05-24 00:00:00'))
        );
    }

}


<?php

class Namer {

    public function __construct( $firstName ) {
        $this->firstName = $firstName;
    }

    public function getFullName( $lastName ) {
        return $this->firstName . ' ' . $lastName;
    }

}


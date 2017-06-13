package com.farenda.tutorials.spock
 
import spock.lang.Specification
 
class HelloSpockTest extends Specification {
 
    def 'should test something useful'() {
        expect:
        true
    }
}

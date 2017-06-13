package com.farenda.solved
 
import spock.lang.Shared
import spock.lang.Specification
 
class SharingElementsTest extends Specification {
 
    // Will be instantiated only once for all the tests!
    def @Shared heavyObject = new VeryCostlyObject()
 
    def 'should test this'() {
        expect:
        heavyObject != null
    }
 
    def 'should test that'() {
        expect:
        heavyObject != null
    }
}

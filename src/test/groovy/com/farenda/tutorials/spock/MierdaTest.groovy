package com.farenda.tutorials.spock

import spock.lang.Specification

class MierdaTest extends Specification {
	def 'should sort list of numbers'() {
		given:
		def list = [a, b]

		when:
		def calculated = list.sort()

		then:
		calculated == expectedResult

		where:
		a | b | expectedResult
		5 | 1 | [1, 5]
		9 | 9 | [9, 9]
	}
}

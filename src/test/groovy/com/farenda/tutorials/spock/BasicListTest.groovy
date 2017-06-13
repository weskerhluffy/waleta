import spock.lang.Specification
 
class BasicListTest extends Specification {
  def "should not be empty after adding element"() {
    given:
    // [] is Groovy literal for List and is infered
    def list = []
 
    when:
    list.add(42)
 
    then:
    // Asserts are implicit and not need to be stated.
    // Change "==" to "!=" and see what's happening!
    list.size() == 1
    list == [42]
  }
}

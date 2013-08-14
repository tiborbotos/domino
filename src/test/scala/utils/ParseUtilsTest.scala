package utils

import org.junit.Assert
import org.junit.Test

class ParseUtilsTest {
	
	@Test
	def testAsLong() {
		val value = ParseUtils.asLong("10").get
		
		Assert.assertEquals(10L, value)
	}
	
	@Test
	def testAsLongWithNotANumber() {
		val value = ParseUtils.asLong("xxx").get
		
		Assert.assertEquals(0L, value)
	}
}
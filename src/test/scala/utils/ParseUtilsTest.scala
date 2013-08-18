package utils

import org.junit.Assert._
import org.junit.Test
import org.apache.commons.lang.RandomStringUtils

class ParseUtilsTest {

  @Test
  def testAsLong() {
    val value = ParseUtils.asLong("10").get
    assertEquals(10L, value)
  }

  @Test
  def testAsLongWithNotANumber() {
    val value = ParseUtils.asLong(RandomStringUtils.randomAlphabetic(2)).get
    assertEquals(0L, value)
  }

  @Test
  def testAsInt() = {
    val value = ParseUtils.asInt("10").get
    assertEquals(10, value)
  }

  @Test
  def testAsIntWithNotANumber() = {
    val value = ParseUtils.asInt(RandomStringUtils.randomAlphabetic(2)).get
    assertEquals(0, value)
  }

  @Test
  def testParseGroupFirst() = {
    val matchedValue = RandomStringUtils.randomAlphabetic(5)
    val input = "data1=1, data2=" + matchedValue + ", data3=3,"
    val pattern = "data2=(.*?),"

    val result = ParseUtils.parseGroupFirst(input, pattern)

    assertEquals(matchedValue, result)
  }

  @Test
  def testParseGroupFirstWhenMatchedValueContainsNewLine() = {
    val matchedValue = RandomStringUtils.randomAlphabetic(5) + "\n\r" + RandomStringUtils.randomAlphabetic(5)
    val input = "data1=1, data2=" + matchedValue + ", data3=3,"
    val pattern = "data2=(.*?),"

    val result = ParseUtils.parseGroupFirst(input, pattern, true)

    assertEquals(matchedValue, result)
  }

  @Test
  def testParseGroupFirstWhenNoMatch() = {
    val input = RandomStringUtils.randomAscii(100)
    val pattern = "(Í)"

    val result = ParseUtils.parseGroupFirst(input, pattern, true)

    assertEquals("", result)
  }

}
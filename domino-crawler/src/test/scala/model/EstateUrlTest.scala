package model

import org.junit.Test
import org.junit.Assert

class EstateUrlTest {

  @Test
  def testDomain() {
    val url = Url(path = "https://www.domain.com/index.php")

    Assert.assertEquals("https://www.domain.com", url.domain)
  }

  @Test
  def testDomainEmpty() {
    val url = Url(path = "index.html")

    Assert.assertEquals("", url.domain)
  }
}
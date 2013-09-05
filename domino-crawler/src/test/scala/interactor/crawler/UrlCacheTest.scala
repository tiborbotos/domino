package interactor.crawler

import org.junit.Test
import org.junit.Assert
import model.Url
import org.junit.Before
import org.apache.commons.lang.RandomStringUtils

class UrlCacheTest {

  val target:UrlCache = new UrlCache
  
  @Before
  def setup() {
    target.clear
  }
  
  @Test
  def testPush() {
    target.push(createUrl())
    
    Assert.assertEquals(1, target.size)
  }

  @Test
  def testPush_theSameUrl() {
    val url = createUrl()
    target.push(url)
    target.push(url)
    
    assert(2 == target.size, "went wrong")
  }
  
  def createUrl(path:String = "www.domain.com/" + RandomStringUtils.randomAlphabetic(10)) = new Url(path = path)
}
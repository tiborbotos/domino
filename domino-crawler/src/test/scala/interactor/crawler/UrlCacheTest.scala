package interactor.crawler

import org.scalamock.scalatest.MockFactory
import model.Url
import org.scalatest.FlatSpec
import org.apache.commons.lang.RandomStringUtils
import scala.concurrent.duration._

class UrlCacheTest extends FlatSpec {

  def fixture =
    new {
      val target = new UrlCache
      def createUrl = new Url(path = "www.domain.com/" + RandomStringUtils.randomAlphabetic(10))
    }
  
  "a cache" must "accept a url" in {
    val f = fixture
    import f._
    f.target.push(createUrl)
    assert(f.target.size === 1)
  }
  
  it must "not store the same url twice" in {
    val f = fixture
    val url = f.createUrl
    f.target.push(url)
    f.target.push(url)
    assert(f.target.size === 1)
  }
  
  it must "not retrieve if not pushed" in {
    val f = fixture
    val url = f.target.pop
    assert(url.isEmpty === true)
  }
  
  it must "retrieve after pushed" in {
    val f = fixture
    import f._
    target.push(createUrl)
    val url = target.pop
    assert(url.isEmpty === false)
  }
    
  it must "not retrieve url which is already reserved" in {
    val f = fixture
    import f._
    val cacheElement = CacheElement.createCacheElementForUrl(createUrl)
    val reservationObsolate = UrlCache.RESERVATION_TTL.fromNow

    cacheElement.reserve
    val result = target.isValidCacheElement(cacheElement, reservationObsolate)
    
    assert(result === false)
  }
  
  it must "retrieve url which reservation is expired" in {
    val f = fixture
    import f._
    val cacheElement = CacheElement.createCacheElementForUrl(createUrl)
    val reservationObsolate = UrlCache.RESERVATION_TTL.fromNow

    cacheElement.reserve
    val result = target.isValidCacheElement(cacheElement, reservationObsolate)
    
    assert(result === false)
  }
  
  it must "not retrieve url which reservation is not expired" in {
    val f = fixture
    import f._
    
    val cacheElement = CacheElement.createCacheElementForUrl(createUrl)
    val reservationObsolate = 0 minutes fromNow
    
    cacheElement.reserve
    val result = target.isValidCacheElement(cacheElement, reservationObsolate)
    
    assert(result === false)
  }
}
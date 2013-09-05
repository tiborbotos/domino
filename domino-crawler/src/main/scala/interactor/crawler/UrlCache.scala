package interactor.crawler

import model.Url
import scala.collection.mutable.ListBuffer
import scala.collection.concurrent.Map
import java.util.Date
import java.lang.Boolean
import scala.concurrent.duration._
import scala.collection.mutable.HashMap

object UrlCache {
  val RESERVATION_TTL = 10 minutes
}

class UrlCache {
  private val urlMap = new HashMap[String, CacheElement]()// with Map[String, CacheElement]
  
  def push(url: Url) {
    val old = urlMap.get(url.path)
    if (old.isDefined)
      old.get.found
    else urlMap.put(url.path, CacheElement.createForUrl(url))
  }

  def pop: Option[Url] = {
    val reservationObsolate = UrlCache.RESERVATION_TTL.fromNow
    
    urlMap.find(
        item => {
          val cacheElement = item._2
          (cacheElement.isReserved == false || 
            (cacheElement.isReserved == true && cacheElement.getReservedUntil.get < reservationObsolate)) 
      }
    ).flatMap(a => Some(a._2.url))
  }
  
  private[crawler] def size = urlMap.size
  private[crawler] def clear = urlMap.clear
}

object CacheElement {
  def createForUrl(url: Url):CacheElement = {
    val elem = new CacheElement
    elem.pUrl = url
    return elem
  }
}

private[crawler] class CacheElement {
  private var pUrl: Url = null
  private var foundCount = 1
  private var reservedUntil:Option[Deadline] = None
  private var reserved = false
  
  def url = pUrl
  def found = foundCount += 1
  def isReserved = reserved
  def getReservedUntil = reservedUntil
  def reserve = {
    reserved = true
    reservedUntil = Some(UrlCache.RESERVATION_TTL.fromNow)
  }
}
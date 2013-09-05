package interactor.crawler

import akka.actor.Actor
import model.Url
import model.estate.WebPage
import utils.URLReader
import model.Url
import model.estate.Estate
import akka.actor.ActorLogging
import interactor.UrlList
import interactor.EstateMessage

/**
 * Consumes realestate urls, and estates.
 * Saves to the database, and retrieve new urls which are not currently under processing. This actor is responsible for
 * keeping the currently processed urls consistent.
 */
class WebPageConsumerActor extends Actor with ActorLogging {

  def receive = {
    case url: Url => saveUrl(url)
    case urlList: UrlList => saveUrlList(urlList.list)
    case estate: EstateMessage => saveEstate(estate.estate)

    case a: Any => log.error("received unknown message $1", a.toString)
  }

  def saveUrlList(urlList: List[Url]) = urlList.foreach(p => saveUrl(p))

  def saveUrl(url: Url) = {
    // TODO not implemented yet
    log.info("SaveUrl(url=" + url + ")")
  }

  def findUrl(): Option[Url] = {
    // TODO not implemented yet
    None
  }

  def saveEstate(estate: Estate[Any]) = {
    // TODO not implemented yet
  }
}
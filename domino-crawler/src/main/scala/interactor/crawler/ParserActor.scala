package interactor.crawler

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import model.Domain
import model.Url
import model.estate.WebPage
import webpageparser.WebPageParser
import utils.URLReader
import interactor.UrlList
import interactor.EstateMessage

/**
 * Web page crawler actor
 * It receives only a url, and parses the downloaded page
 */
class ParserActor(webPageConsumer: ActorRef) extends Actor with ActorLogging {

  def receive = {
    case url: Url => 
      parseUrl(url)
    case a:Any =>
      log.error("Invalid message recieved! [" + a + "]")
  }

  /**
   * Downloads the page, retrieves a WebPageParser instance, which result will be sent further
   */
  def parseUrl(url: Url) = {
    log.info("parseUrl(url=%1 $1 %s)", url)
    val page = downloadWebPage(url)
    log.debug("downloaded " + page.html.size + " bytes")

    val parser = WebPageParser.parserForPage(page).getOrElse(throw new RuntimeException(""))

    val urls = parser.getEstateUrls
    val realEstate = parser.parse

    log.debug("found " + urls.length + " urls")
    log.debug("found estate: " + realEstate.isDefined)

    webPageConsumer ! UrlList(urls)
    
    if (realEstate.isDefined)
      webPageConsumer ! EstateMessage(realEstate.get)
  }

  def downloadWebPage[DOMAIN <: Domain](url: Url): WebPage[DOMAIN] = {
    WebPage(html = URLReader.download(url.path), url = url)
  }
}
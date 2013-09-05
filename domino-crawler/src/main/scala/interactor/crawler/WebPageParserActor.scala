package interactor.crawler

import akka.actor.Actor
import model.Url
import akka.actor.ActorLogging
import scala.collection.mutable.ListBuffer
import webpageparser.ParserAlberletHu
import akka.actor.ActorRef
import akka.actor.Props
import akka.routing.RoundRobinRouter
import interactor.CrawlerStart
import interactor.Ping
import interactor.Pong

/**
 * Actor, which delegates the parsing for the crawler actors
 */
sealed class WebPageParserActor(estateConsumer: ActorRef, crawlerStart: CrawlerStart) extends Actor with ActorLogging {

  /* ******************************************************************************************************************
   * Actor state
   */

  private var recievedUrlsToParse = 0

  /* ******************************************************************************************************************
   * Constructor
   */

  val crawlerCount = if (crawlerStart.threadCount < 1) crawlerStart.baseUrls.length else crawlerStart.threadCount
  val crawlers = createCrawlers(crawlerCount)

  log.info("EstateProducer created with " + crawlerCount + " crawlers")
  crawlerStart.baseUrls.foreach(f => parseUrl(f))

  /* ******************************************************************************************************************
   * Actor methods
   */

  def receive = {
    case url: Url =>
      parseUrl(url)
    case Ping =>
      sender ! Pong
    
    case a:Any =>
      log.error("Received unknown message! [" + a + "]")
  }

  def parseUrl(url: Url) = {
    recievedUrlsToParse += 1
    log.info("parse url(url=" + url + ",recievedUrlsToParse=" + recievedUrlsToParse + ")")
    crawlers ! url
  }

  def createCrawlers(pcrawlerCount: Int) =
    context.actorOf(
      Props(new EstateCrawler(estateConsumer))
      	.withDispatcher("crawler-actor-dispatcher")
        .withRouter(RoundRobinRouter(pcrawlerCount)), name = "WebPageDownloader")
        
}
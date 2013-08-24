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
import scala.concurrent.duration._

/**
 * Estate producer actor, which delegates the parsing for the crawler actors
 */
sealed class EstateProducer(estateConsumer: ActorRef, crawlerStart: CrawlerStart) extends Actor with ActorLogging {
  import context._ // implicit defs

  val CHECK_PARSABLE_URL_COUNT = "checkParseableUrlCount"

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
  context.system.scheduler.scheduleOnce(10 seconds, self, CHECK_PARSABLE_URL_COUNT)

  /* ******************************************************************************************************************
   * Actor methods
   */

  def receive = {
    case url: Url =>
      parseUrl(url)

    case CHECK_PARSABLE_URL_COUNT =>
      if (recievedUrlsToParse == crawlerStart.baseUrls.length) {
        log.info("No URLs recieved to parse, shutting down!")
        context.system.shutdown
      }

    case "ready" =>
      
      log.info("*************READY?")
      sender ! "yes"
      
    case _ =>
      log.error("received unknown message")
  }

  def parseUrl(url: Url) = {
    recievedUrlsToParse += 1
    log.info("parse url(url=" + url + ",recievedUrlsToParse=" + recievedUrlsToParse + ")")
    crawlers ! url
  }

  def createCrawlers(pcrawlerCount: Int) =
    context.actorOf(
      Props(new EstateCrawler(estateConsumer))
        .withRouter(RoundRobinRouter(pcrawlerCount)), name = "EstateCrawler")
}
package interactor

import akka.actor.Actor
import akka.actor.ActorLogging
import webpageparser.ParserAlberletHu
import interactor.crawler.EstateCrawler
import akka.actor.Props
import interactor.crawler.EstateProducer
import interactor.crawler.EstateConsumer
import model.DomainAlberletHu
import model.Url

/**
 * Master actor for starting crawler process
 */
class Master() extends Actor with ActorLogging {

  def receive = {
    case CrawlerProcessStart =>
      val starterPackage = CrawlerStart(List(Url(path = DomainAlberletHu.path)))
      
      val estateConsumer = context.actorOf(Props[EstateConsumer], "EstateConsumer")
      val estateProducer = context.actorOf(Props(new EstateProducer(estateConsumer, starterPackage)))

      val res = estateProducer ! "ready" // should answer yes
    
    case "yes" =>
      log.info("***************yes!")
      
    case _ => log.error("Unhandled message recieved!")
  }

}
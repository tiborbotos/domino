package interactor

import akka.actor.Actor
import akka.actor.ActorLogging
import webpageparser.ParserAlberletHu
import interactor.crawler.EstateCrawler
import akka.actor.Props
import interactor.crawler.WebPageParserActor
import interactor.crawler.WebPageConsumerActor
import model.DomainAlberletHu
import model.Url

/**
 * Master actor for starting crawler process
 */
class Master() extends Actor with ActorLogging {

  
  
  def receive = {
    case CrawlerProcessStart =>
      val starterPackage = CrawlerStart(List(Url(path = DomainAlberletHu.path)))
      
      val estateConsumer = context.actorOf(Props[WebPageConsumerActor], "EstateConsumer")
      val estateProducer = context.actorOf(Props(new WebPageParserActor(estateConsumer, starterPackage)))

      val res = estateProducer ! Ping
    
    case Pong =>
      log.info("WebPageParserActor is ready!")
      
    case _ => log.error("Unhandled message recieved!")
  }

}
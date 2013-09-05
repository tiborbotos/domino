package app

import akka.actor.ActorSystem
import akka.actor.Props
import interactor.Master
import interactor.CrawlerProcessStart
import com.typesafe.config.ConfigFactory

/**
 * Application entry point for starting the crawler process
 */
object Domino extends App {
  start
  
  def start() = {
    
    val config = ConfigFactory.load()
    val system = ActorSystem("DominoSystem", config.getConfig("domino-akka-config").withFallback(config))
    val master = system.actorOf(Props(new Master), "master")
    
    master ! CrawlerProcessStart
  }
}
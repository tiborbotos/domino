package app

import akka.actor.ActorSystem
import akka.actor.Props
import interactor.Master
import interactor.CrawlerProcessStart

/**
 * Application entry point for starting the crawler process
 */
object Domino extends App {
  start
  
  def start() = {
    val system = ActorSystem("DominoSystem")
    val master = system.actorOf(Props(new Master), "master")
    
    master ! CrawlerProcessStart
  }
}
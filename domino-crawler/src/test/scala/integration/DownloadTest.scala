package integration

import org.junit.Before
import org.junit.Assert
import org.junit.Test
import web.EstateServer
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props
import interactor.Master
import interactor.CrawlerProcessStart

class DownloadTest {

  val URL_COUNT = 100;
  val SUB_URL_FACTOR = 0.5
  val ESTATE_FACTOR = 0.5

  val estateServer = new EstateServer(URL_COUNT, SUB_URL_FACTOR, ESTATE_FACTOR)

  @Before
  def setup() = {
  }
  
  @Test
  def testDownload() {
    
    val config = ConfigFactory.load()
    val system = ActorSystem("DominoSystem", config.getConfig("domino-akka-config").withFallback(config))
    val master = system.actorOf(Props(new Master), "master")
    
    estateServer.start
    
    master ! CrawlerProcessStart
    
    estateServer.stop(10)
  }
}
package interactor

import akka.actor.Actor
import scala.concurrent.duration._
import akka.actor.ActorRef
import akka.actor.ActorLogging
import java.util.Date
import java.util.Calendar

class Monitoring(EstateProducer:ActorRef) extends Actor with ActorLogging {
  import context._ // implicit defs
  val PING_PRODUCER_ACTOR = "pingProducerActor"
  
  var lastPing = new Date;
  context.system.scheduler.scheduleOnce(10 seconds, self, PING_PRODUCER_ACTOR)

  def receive = {

    case PING_PRODUCER_ACTOR => 
      log.info("Ping...")
      lastPing = new Date()
      EstateProducer ! Ping
    
    case Pong => 
      var pingReply = (new Date().getTime() - lastPing.getTime()) / 1000
      log.info("Ponged in " + pingReply + " sec")
  }
}
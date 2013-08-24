package interactor.persistence

import repository.UrlRepository
import akka.actor.Actor
import model.Url
import akka.actor.ActorLogging
import model.estate.Estate

/**
 * Actor for accessing the url repository
 */
class UrlAccessor(urlRepository: UrlRepository) extends Actor with ActorLogging {
  
  def receive = {
    case url: Url => urlRepository.save(url)    
    case _ => log.error("received unknown message")
  }
  
}
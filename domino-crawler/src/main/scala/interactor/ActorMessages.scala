package interactor

import model.Domain
import model.Url
import model.estate.Estate

/**
 * All of it sub classes are used to deliver messages between actors
 */
sealed trait DominoMessage

case object Ping extends DominoMessage
case object Pong extends DominoMessage

case object CrawlerProcessStart extends DominoMessage

case class CrawlerStart(baseUrls: List[Url], threadCount:Int = 0) extends DominoMessage

case object CrawlerKill extends DominoMessage

case class UrlList(list: List[Url]) extends DominoMessage

case class EstateMessage(estate: Estate[Any]) extends DominoMessage

case class UrlFindByDomain(domain: Domain) extends DominoMessage

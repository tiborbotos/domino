package webpageparser

import model.Domain
import model.DomainAlberletHu
import model.Url
import model.estate.Estate
import model.estate.WebPage
import model.DomainLocalhost

/**
 * Estate parser trait (domain specific)
 */
trait WebPageParser[+DOMAIN <: Domain] {

	def parse(): Option[Estate[DOMAIN]]

	def getEstateUrls: List[Url]
}

/**
 * The WebPageParser companion object is able to create a parser for a specific page, based on the Domain type 
 * parameter
 */
object WebPageParser {
  def parserForPage[DOMAIN <: Domain](page: WebPage[DOMAIN]):Option[WebPageParser[DOMAIN]] = {
    page.url.path match {
      case url:String if (url == DomainAlberletHu.path) =>
        Some(new ParserAlberletHu(page.asInstanceOf[WebPage[DomainAlberletHu]]).asInstanceOf[WebPageParser[DOMAIN]])
      
      case url:String if (url == DomainLocalhost.path) =>
        Some(new ParserTestLocalhost(page.asInstanceOf[WebPage[DomainLocalhost]]).asInstanceOf[WebPageParser[DOMAIN]])
      case _ => None
    }
  }
}
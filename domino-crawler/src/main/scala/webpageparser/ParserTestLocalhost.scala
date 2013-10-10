package webpageparser

import model.estate.WebPage
import model.DomainLocalhost
import model.estate.Estate
import model.Url

class ParserTestLocalhost(webPage: WebPage[DomainLocalhost]) extends WebPageParser[DomainLocalhost] {

  def parse(): Option[Estate[DomainLocalhost]] = {
    None
  }
  
  def getEstateUrls: List[Url] = List() 
}
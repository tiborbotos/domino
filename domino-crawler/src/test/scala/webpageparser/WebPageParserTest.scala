package webpageparser

import org.junit.Test
import org.junit.Assert.assertTrue
import model.estate.WebPage
import model.DomainAlberletHu
import model.Url
import model.Domain
import org.apache.commons.lang.RandomStringUtils

class WebPageParserTest {
  @Test
  def testParserForPage() = {
    val page = new WebPage[DomainAlberletHu](html = "", url = Url(path = DomainAlberletHu.path))
    
    val parser = WebPageParser.parserForPage(page)
    
    assertTrue(parser.isDefined)
    assertTrue(parser.get.getClass == classOf[ParserAlberletHu])
  }
  
  @Test
  def testParserForUnknownPage() = {
    val domainFooPath = RandomStringUtils.randomAlphabetic(10)
    case class DomainFoo extends Domain(url = Url(path = domainFooPath ))
    
    val page = new WebPage[DomainFoo](html = "", url = Url(path = domainFooPath))
    
    val parser = WebPageParser.parserForPage(page)
    
    assertTrue(parser.isEmpty)
  }
  
}
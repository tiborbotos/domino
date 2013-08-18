package webpageparser

import org.junit.Test
import org.junit.Assert._
import java.io.File
import model.estate.WebPage
import model.Url
import org.apache.commons.lang.RandomStringUtils
import model.estate.Estate
import model.DomainAlberletHu

class ParserAlberletHuTest {
  val path = "src/test/resources/webpageparser/"

  @Test
  def testRead01() = {
    val urlCount = 17
    val d = "Bérlésre kínáljuk másfél szobás, teljesen bútorozott, gépesített, világos, 4. emeleti lakásunkat a Kazinczy utcában. Remek központi fekvése miatt minden könnyen elérhető, a környék közlekedése kitűnő."
    val urlPath = "http://www." + RandomStringUtils.randomAlphabetic(5) + ".com";

    val parserResult = parseEstate(path + "alberlethu_01.html", urlPath)
    val urlList = parserResult._2
    val estateO = parserResult._1

    assertEquals(urlCount, urlList.size)
    assertTrue(estateO.isDefined)

    val estate = estateO.get
    assertEquals(None, estate.id)
    assertEquals("Budapest VII. kerület Kazinczy utca", estate.address.get.fullAddress)
	assertEquals("Budapest", estate.address.get.city)
	assertEquals("VII. Kerület", estate.address.get.district.get)
	assertEquals("Kazinczy utca", estate.address.get.address.get)
	assertEquals(None, estate.address.get.country)

    assertEquals(47, estate.basicInfo.get.area.get)
    assertEquals(d, estate.basicInfo.get.description.get)
    assertEquals(4, estate.basicInfo.get.floor.get)
    assertEquals(1, estate.basicInfo.get.halfRoomsCount.get)
    assertEquals(1, estate.basicInfo.get.roomsCount.get)
    assertEquals(4, estate.basicInfo.get.floor.get)

    assertEquals(0, estate.fee.get.commonFee.get)
    assertEquals(0, estate.fee.get.depositFee.get)
    assertEquals(0, estate.fee.get.rentalFee.get)
    assertEquals(0, estate.fee.get.utilitiesFee.get)

    assertEquals(None, estate.url.id)
    assertEquals("", estate.url.label)
    assertEquals(urlPath, estate.url.path)
    assertEquals(true, estate.url.urlActive)
  }

  @Test
  def testRead02() = {
    val urlCount = 18
    val d = """VI. kerület Rippl Rónai utcában kiadó 2013-ban átadott társasház 1. emeletén egy 2 háló + amerikai konyha nappali elrendezésű /az egyik hálórész hálófülke kialakítású/, 55 m2-es luxuslakás.A társasházban 24 órás portaszolgálat áll rendelkezésre, valamint fitness és wellness-szolgáltatást nyújt lakói számára. A lakás hang és hőszigetelt nyílászárókkal, fűtő-hűtő geotermikus fűtésrendszerrel felszerelt, minőségi olasz burkolatokkal és konyhabútorral beépített.A lakás közös költsége 37.000.-/hó, mely tartalmazza a korábban említett szolgáltatásokat. A rezsi egyedi fogyasztásmérők alapján fizetendő, 2 havi kaució szükséges. Háziállat tartása sajnos nem megengedett.Augusztus 05.-től költözhető, minimum 1 évre."""
    val urlPath = "http://www." + RandomStringUtils.randomAlphabetic(5) + ".com";

    val parserResult = parseEstate(path + "alberlethu_02.html", urlPath)
    val urlList = parserResult._2
    val estateO = parserResult._1

    assertEquals(urlCount, urlList.size)
    assertTrue(estateO.isDefined)

    val estate = estateO.get
    assertEquals(None, estate.id)
    assertEquals("Budapest VI. kerület Rippl Rónai utca", estate.address.get.fullAddress)
	assertEquals("Budapest", estate.address.get.city)
	assertEquals("VI. Kerület", estate.address.get.district.get)
	assertEquals("Rippl Rónai utca", estate.address.get.address.get)
	assertEquals(None, estate.address.get.country)

    assertEquals(55, estate.basicInfo.get.area.get)
    assertEquals(d, estate.basicInfo.get.description.get)
    assertEquals(1, estate.basicInfo.get.floor.get)
    assertEquals(2, estate.basicInfo.get.halfRoomsCount.get)
    assertEquals(1, estate.basicInfo.get.roomsCount.get)
    assertEquals(1, estate.basicInfo.get.floor.get)

    assertEquals(0, estate.fee.get.commonFee.get)
    assertEquals(0, estate.fee.get.depositFee.get)
    assertEquals(0, estate.fee.get.rentalFee.get)
    assertEquals(0, estate.fee.get.utilitiesFee.get)

    assertEquals(None, estate.url.id)
    assertEquals("", estate.url.label)
    assertEquals(urlPath, estate.url.path)
    assertEquals(true, estate.url.urlActive)
  }

  
  def parseEstate(filePath: String, urlPath: String): (Option[Estate[DomainAlberletHu]], List[Url]) = {
    val file = new File(filePath)
    val data = scala.io.Source.fromFile(file).getLines.mkString("")
    val parser = new ParserAlberletHu(new WebPage(html = data, url = Url(path = urlPath)))

    return (parser.parse, parser.getEstateUrls)
  }
}
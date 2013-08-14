package webpageparser

import model.estate.WebPage
import utils.ParseUtils._
import model.estate.EstateImage
import scala.collection.mutable.ListBuffer
import java.util.regex.Pattern
import model.Url
import model.estate.Estate
import model.estate.EstateFee
import model.estate.EstateBasicInfo
import model.DomainAlberletHu
import org.apache.commons.lang.StringEscapeUtils

/**
 * WebPageParser for alberlet.hu domain pages
 */
class ParserAlberletHu(webPage: WebPage[DomainAlberletHu]) extends WebPageParser[DomainAlberletHu] {

  lazy val page = webPage.html
  
  def parse(): Option[Estate[DomainAlberletHu]] = {
    hasRealEstate match {
      case false => None
      case _ => parseRealEstate()
    }
  }

  def parseRealEstate(): Option[Estate[DomainAlberletHu]] = {
    val rentalType = parseGroupFirst(page, "td>Épület típusa.*?<b>(.*?)</b>", false);
    val rentalFee = asLong(parseGroupFirst(page, "td>Bérleti díj.*?<b>(.*?) HUF / hó<div"));
    val depositFee = asLong(parseGroupFirst(page, "td>Kaució.*?<b>(.*?) HUF</b>"));
    val utilitiesFee = asLong(parseGroupFirst(page, "td>Rezsiköltség.*?<b>(.*?) HUF / hó"));
    val commonFee = asLong(parseGroupFirst(page, "td>Közös költség.*?<b>(.*?) HUF / hó"));
    val area = asInt(parseGroupFirst(page, "td>Méret.*?<b>(.*?) m<sup>2</sup></b>"));
    val roomsCount = asInt(parseGroupFirst(page, "td>Szobák száma.*?<b>(.*?)szoba.*?</b>"));
    val halfRoomsCount = asInt(parseGroupFirst(page, "td>Szobák száma.*?<b>.*?szoba \\+(.*?) félszoba</b>"));
    val floor = asInt(parseGroupFirst(page, "td>Emelet.*?<b>(.*?)</b>"));
    val description = unescapeHtml((parseGroupFirst(page,
      "<h4 class=\"listing-data-trigger\">Részletek.*?<p>(.*?)</p>", true)));
    val address = parseGroupFirst(page, "<title>Kiadó albérlet \\| (.*?) - Albérlet.hu</title>");
    val street = parseGroupFirst(page, "<div class=\"listing-title\">.*?<h3>(.*?)</h3>.*?<h4>.*?(,|).*?</h4>", true).replaceAll("&nbsp;", "")

    val city = parseCity(page)
    val district = parseDistrict(page)

    val images = parseImages(page)

    val estate = Estate[DomainAlberletHu](
      url = webPage.url,
      fee = Some(EstateFee(rentalFee = rentalFee, depositFee = depositFee, utilitiesFee = utilitiesFee, commonFee = commonFee)),
      basicInfo = Some(EstateBasicInfo(area = area, roomsCount = roomsCount, halfRoomsCount = halfRoomsCount, floor = floor, description = Some(description))),
      images = images
    )

    Some(estate)
  }

  def getEstateUrls: List[Url] = {
    val p = Pattern.compile("href=\"(/kiado_alberlet/.*?)\"(.*?)>(.*?)</a>");
    val m = p.matcher(page);
    val result = ListBuffer[Url]()

    // find every estate URL
    while (m.find()) {
      val m1 = m.group(1);
      val m3 = m.group(3);
      val url = new Url(path = "http://www.alberlet.hu" + m1, label = m3);

      result.append(url)
    }

    result.toList
  }

  protected def hasRealEstate = (page.indexOf("<div class=\"listing-data") != -1)

  protected def unescapeHtml(input: String): String = StringEscapeUtils.unescapeHtml(input)

  protected def cityd = parseGroupFirst(page, "<div class=\"listing-title\">.*?<h3>.*?</h3>.*?<h4>(.*?)</h4>", true);

  protected def parseDistrict(input: String): String = {
    if (cityd.indexOf("Kerület") > -1)
      parseGroupFirst(page, "<div class=\"listing-title\">.*?<h3>.*?</h3>.*?<h4>.*?,(.*?)</h4>", true).replaceAll("&nbsp;", "")
    else ""
  }

  protected def parseCity(input: String): String = {
    if (cityd.indexOf("Kerület") > -1)
      parseGroupFirst(page, "<div class=\"listing-title\">.*?<h3>.*?</h3>.*?<h4>(.*?),.*?</h4>", true);
    else cityd.replaceAll("&nbsp;", "");
  }

  protected def parseImages(input: String): List[EstateImage] = {
    val list = new ListBuffer[EstateImage]()
    val gallery = parseGroupFirst(page, "<div class=\"listing-gallery\"(.*?)<div class=\"content-tabs\"", true);
    if (gallery != null && !gallery.equals("")) {
      val p = Pattern.compile("href=\"(.*?800x600\\.jpg).*?data-medium-image=\"(.*?\\.jpg)");
      val m = p.matcher(page);
      while (m.find()) {
        if (m.groupCount() == 2) {
          list.append(EstateImage(url = Url(path = m.group(1)), thumbnail = Some(Url(path = m.group(2)))))
        }
      }
    }

    list.toList
  }

  protected def isValid(url: Url): Boolean =
    if (url.path.indexOf("/tab:") > -1 ||
      url.path.indexOf("/direction:") > -1 ||
      url.path.indexOf("/sort:") > -1 ||
      url.path.indexOf("/view:") > -1 ||
      url.path.indexOf("/keres:") > -1)
      return false;
    else true

}
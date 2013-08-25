package web

import web.base.SimpleHttpServer
import com.sun.net.httpserver.HttpExchange
import scala.util.parsing.json.JSON
import scala.util.parsing.json.JSONObject
import com.google.gson.Gson
import scala.collection.mutable.ListBuffer
import org.apache.commons.lang.math.RandomUtils

/**
 * Mocked real estate webserver for generating test data<br>
 * Handled urls:<br>
 * - URL_TEST
 *   For testing porpuses
 *
 * URL patterns for retrieving url's and estates.
 *
 * - /estatelist/
 *   Retrieves url list. It may contain other url list page, or estate.
 * - /estatelist/$RND
 *   Retrieves url list. It may contain other url list page, or estate. It's usually retrived via the /estatelist/ link
 * - /estate/$ID
 *   Retrieves the estate, defined by $ID. This page can also provide another estate list
 */
class EstateServer(urlCount: Int, subUrlFactor: Double = 0.5, estateFactor: Double = 0.5)
  extends SimpleHttpServer {

  def get(implicit exchange: HttpExchange) = {
    case url: String if (url == EstateServer.URL_TEST) => EstateServer.URL_TEST_ANSWER
    case urlParam: String => urlMatch(urlParam.split("/").toList)
  }

  def urlMatch(url: List[String]): PartialFunction[Any, Any] = {
    case EstateServer.URL_ESTATELIST => retrieveUrlList()

    case EstateServer.URL_ESTATELIST :: rnd :: _ => retrieveUrlList().mkString("\n") // no 3rd level
    case EstateServer.URL_ESTATELIST :: rnd => retrieveUrlList(rnd.asInstanceOf[String]).mkString("\n")
    case EstateServer.URL_ESTATE :: id => retrieveEstate(id.asInstanceOf[String])
    case _ => throw new RuntimeException("Failed to match url: " + url.mkString)
  }

  def retrieveUrlList(subLevel: String = ""): List[String] = {
    val result = new ListBuffer[String]

    while (result.length < urlCount)
      result.append(EstateServer.URL_ESTATELIST + "/" + result.length)
    
    generateSubUrls(subLevel, result)
    generateEstateUrls(subLevel, result)
    result.toList
  }

  def retrieveEstate(id: String): String =
    "url=" + EstateServer.URL_ESTATE + "/" + id + ";label=" + id

  protected[web] def generateSubUrls(subLevel: String, result: scala.collection.mutable.ListBuffer[String]): Unit = {
    def randomUrl(subLevel: String) = EstateServer.URL_ESTATELIST + "/" + subLevel + "/" + RandomUtils.nextInt(urlCount)
    
    if (subLevel != "") {
      val subUrlCount = (result.size + Math.floor(subUrlFactor * subLevel.toInt )).toInt
      while (result.length < subUrlCount)
        result.append(randomUrl(subLevel))
    }
  }

  protected[web] def generateEstateUrls(subLevel: String, result: scala.collection.mutable.ListBuffer[String]): Unit = {
    val maxEstateUrlCount = result.length +
      (if (subLevel == "") urlCount else Math.floor((1 + estateFactor) * subLevel.toInt).toInt)
    
    while (result.length < maxEstateUrlCount) {
      result.append(EstateServer.URL_ESTATE + "/" + result.length)
    }
  }
}

object EstateServer {
  val URL_TEST = "/test"
  val URL_TEST_ANSWER = "estate server::hello"

  val URL_ESTATELIST = "estatelist"
  val URL_ESTATE = "estate"
}
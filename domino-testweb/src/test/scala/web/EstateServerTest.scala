package web

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
import scala.collection.mutable.ListBuffer
import org.apache.commons.lang.RandomStringUtils

class EstateServerTest {
  val URL_COUNT = 100;
  val SUB_URL_FACTOR = 0.5
  val ESTATE_FACTOR = 0.5

  val target = new EstateServer(URL_COUNT, SUB_URL_FACTOR, ESTATE_FACTOR)

  @Before
  def setup() = {
    target.stop(0)
  }

  @Test
  def testRetrieveTopLevelUrls() = {
    val result = target.retrieveUrlList()

    assertEquals(URL_COUNT * 2, result.length)
    assertEquals(EstateServer.URL_ESTATELIST + "/0", result(0))
    assertEquals(EstateServer.URL_ESTATELIST + "/" + (URL_COUNT - 1).toString, result(URL_COUNT - 1))
  }

  @Test
  def testGenerateEstateUrls() = {
    testGenerateEstateWithParam("0")
    testGenerateEstateWithParam(URL_COUNT.toString)
  }
  
  def testGenerateEstateWithParam(urlCount: String) = {
    val list = new ListBuffer[String]()
    val result = target.generateEstateUrls(urlCount, list)
    
    assertEquals( (urlCount.toInt * (1 + ESTATE_FACTOR)).toInt, list.length)
  }
  
  @Test
  def testGenerateSubUrls() = {
    testGenerateSubUrlsWithParam(0)
    testGenerateSubUrlsWithParam(URL_COUNT)
  }
  
  def testGenerateSubUrlsWithParam(urlCount: Int) = {
    val list = new ListBuffer[String]()
    val result = target.generateSubUrls(urlCount.toString, list)
    assertEquals((urlCount * SUB_URL_FACTOR).toInt, list.length)
  }
  
  @Test
  def testRetrieveEstate() = {
    val id = RandomStringUtils.randomNumeric(2)
    val result = target.retrieveEstate(id)
    
    assert(result.contains("url=" + EstateServer.URL_ESTATE + "/" + id))
    assert(result.contains("label=" + id))
  }
}
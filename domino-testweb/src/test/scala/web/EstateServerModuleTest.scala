package web

import scala.annotation.elidable
import scala.annotation.elidable.ASSERTION

import org.junit.After
import org.junit.Before
import org.junit.Test

class EstateServerModuleTest {

  val localhost = "http://127.0.0.1:8080"
  val server = new EstateServer(10)

  @Before
  def setup() = {
    server.start
  }
  
  @After
  def after() = {
    server.stop(0)
  }
  
  @Test
  def testServerStartUp() = {
    val result = scala.io.Source.fromURL(localhost + EstateServer.URL_TEST).getLines.mkString("\n")

    assert(result == EstateServer.URL_TEST_ANSWER)
  }

}
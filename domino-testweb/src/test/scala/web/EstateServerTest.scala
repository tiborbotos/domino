package web

import org.junit.Test

class EstateServerTest {
  
  @Test
  def testServerStartUp() = {
    val server = new EstateServer()
    server.start
    
    val result = scala.io.Source.fromURL("http://127.0.0.1:8080/").getLines.mkString("\n")
    
    server.stop(0);
    
    println(result);
    assert(result ==  EstateServer.DEFAULT_ANSWER )
  }
  
}
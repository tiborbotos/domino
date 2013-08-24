package web

import web.base.SimpleHttpServer
import com.sun.net.httpserver.HttpExchange

class EstateServer extends SimpleHttpServer {

  def get(implicit exchange: HttpExchange) = {
    case url: String if (url == "/") => EstateServer.DEFAULT_ANSWER
    
    case 
  }
}

object EstateServer {
  val DEFAULT_ANSWER = "estate server::hello"
}
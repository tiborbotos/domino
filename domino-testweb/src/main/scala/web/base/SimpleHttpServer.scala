package web.base

import com.sun.net.httpserver.HttpExchange
import scala.collection.mutable.HashMap

/**
 * Introduces http server with the possibility to respond as a PartialFunction reply
 * 
 * @author Tibor Botos
 */
abstract class SimpleHttpServer extends SimpleHttpServerBase {

  def handle(exchange: HttpExchange) = {
    implicit val e = exchange
    val resp = (get orElse reportProblem)(exchange.getRequestURI().getPath())
    if (resp == None)
      respond(exchange, 404)
    else
      respond(exchange, 200, resp.toString)
  }

  def reportProblem(implicit exchange: HttpExchange): PartialFunction[Any, Any] = {
    case _ => None
  }

  def get(implicit exchange: HttpExchange): PartialFunction[Any, Any]
}

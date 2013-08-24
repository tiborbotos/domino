package web.base

import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpHandler
import java.net.InetSocketAddress
import com.sun.net.httpserver.HttpExchange

/**
 * Basic http server
 * http://stackoverflow.com/questions/8193904/sbt-test-dependencies-in-multiprojects-make-the-test-code-available-to-dependen
 */
abstract class SimpleHttpServerBase(val socketAddress: String = "127.0.0.1",
                                    val port: Int = 8080,
                                    val backlog: Int = 0) extends HttpHandler {
  
  private val address = new InetSocketAddress(socketAddress, port)
  private val server = HttpServer.create(address, backlog)
  server.createContext("/", this)

  def redirect(url: String) =
    <html>
      <head>
          <meta http-equiv="Refresh" content={"0," + url}/>
      </head>
      <body>
        You are being redirected to: {url}
      </body>
    </html>

  def respond(exchange: HttpExchange, code: Int = 200, body: String = "") {
    val bytes = body.getBytes
    exchange.sendResponseHeaders(code, bytes.size)
    exchange.getResponseBody.write(bytes)
    exchange.getResponseBody.write("\r\n\r\n".getBytes)
    exchange.getResponseBody.close()
    exchange.close()
  }

  def start() = server.start()

  def stop(delay: Int = 1) = server.stop(delay)
}

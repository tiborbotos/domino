package interactor.crawler

import org.junit.Test
import org.junit.Assert
import org.junit.Before
import akka.actor._
import model.Url
import interactor.Master

class EstateProducerTest {
	
		val system = ActorSystem("PiSystem")
		// create the master
		val master = system.actorOf(Props(new Master), name = "master")
	
	@Test
	def testActor() {
		master ! Url(path = "http://www.alberlet.hu")
		
		val url = Url(path = "https://www.domain.com/index.php")
		
		Assert.assertEquals("https://www.domain.com", url.domain)
	}
}

//http://doc.akka.io/docs/akka/2.0.2/intro/getting-started-first-scala.html
//http://doc.akka.io/docs/akka/snapshot/scala/actors.html
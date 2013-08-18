package repository

import org.junit.Test
import org.junit.Before
import model.estate.Estate
import model.Id
import model.Url
import org.junit.Assert
import model.estate.Estate
import model.estate.Estate
import repository.hashmap.HashMapEstateRepository

class HashMapEstateRepositoryTest {

  val target = new HashMapEstateRepository()

  @Before
  def before() = {
    target.clear
  }

  @Test
  def testSave() = {
    val estate = Estate(url = Url(path = "http://www.domain.com"))

    val saved = target.save(estate)

    val list = target.findAll()
    Assert.assertFalse(list.isEmpty)
    Assert.assertEquals(saved.id.get, list(0).id.get)
  }

  @Test
  def testUpdate() = {
    val estate = Estate(url = Url(path = "http://www.domain.com"))

    val saved = target.save(estate)

    val updated = target.save(saved.copy(url = Url(path = "https://www.abc.org")))

    val list = target.findAll()
    Assert.assertEquals(1, list.size)
    Assert.assertEquals("https://www.abc.org", list(0).url.path)
  }

  @Test
  def testClear() = {
    val estate = createEstate

    target.delete(estate)

    val list = target.findAll()
    Assert.assertTrue(list.isEmpty)
  }

  @Test
  def testFindAll() = {
    target.save(createEstate)
    target.save(createEstate)
    target.save(createEstate)

    val list = target.findAll()
    Assert.assertEquals(3, list.size)
  }

  def createEstate: Estate[Any] = {
    val estate = Estate(url = Url(path = "http://www.domain.com"))
    target.save(estate)
  }
}
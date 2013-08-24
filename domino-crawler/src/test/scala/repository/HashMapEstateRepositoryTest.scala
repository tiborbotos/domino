package repository

import org.apache.commons.lang.RandomStringUtils
import org.junit.Assert
import org.junit.Test

import model.Url
import model.estate.Estate
import repository.hashmap.HashMapEstateRepository

class HashMapEstateRepositoryTest extends HashMapRepositoryTestBase[Estate[Any]] {

  lazy val repository = new HashMapEstateRepository

  def createEntity = target.save(Estate(url = Url(path = RandomStringUtils.randomAscii(128))))

  def target: HashMapEstateRepository = repository

  @Test
  def testUpdate() = {
    val estate = Estate(url = Url(path = RandomStringUtils.randomAscii(128)))
    val newUrlPath = RandomStringUtils.randomAscii(128)
    val saved = target.save(estate)

    val updated = target.save(saved.copy(url = Url(path = newUrlPath)))

    val list = target.findAll()
    Assert.assertEquals(1, list.size)
    Assert.assertEquals(newUrlPath, list(0).url.path)
  }
}
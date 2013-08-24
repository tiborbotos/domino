package repository

import repository.hashmap.HashMapRepository
import model.Url
import repository.hashmap.HashMapUrlRepository
import org.apache.commons.lang.RandomStringUtils
import org.junit.Test
import junit.framework.Assert

class HashMapUrlRepositoryTest extends HashMapRepositoryTestBase[Url] {

  lazy val repository = new HashMapUrlRepository

  def createEntity: Url = target.save(new Url(path = RandomStringUtils.randomAscii(128)))

  def target: HashMapUrlRepository = repository

  @Test
  def testUpdate = {
    val url = createEntity
    val saved = target.save(url)
    val newPath = RandomStringUtils.randomAscii(128)

    val updated = target.save(saved.copy(path = newPath))

    val list = target.findAll()
    Assert.assertEquals(1, list.size)
    Assert.assertEquals(newPath, list(0).path)

  }
}
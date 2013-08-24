package repository

import repository.hashmap.HashMapRepository
import model.Identifiable
import org.junit.Before
import org.junit.Assert
import org.junit.Test

abstract class HashMapRepositoryTestBase[T <: Identifiable] {

  def createEntity: T

  def target: HashMapRepository[T]

  @Before
  def before() = {
    target.clear
  }

  @Test
  def testSave() = {
    val estate = createEntity

    val saved = target.save(estate)

    val list = target.findAll()
    Assert.assertFalse(list.isEmpty)
    Assert.assertEquals(saved.id.get, list(0).id.get)
  }

  /** update based on T type, therefore child class must implement this*/
  def testUpdate
//  @Test
//  def testUpdate() = {
//    val estate = createEntity
//    val saved = target.save(estate)
//
//    val updated = target.save(saved.copy(url = Url(path = "https://www.abc.org")))
//
//    val list = target.findAll()
//    Assert.assertEquals(1, list.size)
//    Assert.assertEquals("https://www.abc.org", list(0).url.path)
//  }

  @Test
  def testClear() = {
    val estate = createEntity

    target.delete(estate)

    val list = target.findAll()
    Assert.assertTrue(list.isEmpty)
  }

  @Test
  def testFindAll() = {
    target.save(createEntity)
    target.save(createEntity)
    target.save(createEntity)

    val list = target.findAll()
    Assert.assertEquals(3, list.size)
  }

  @Test
  def testDeleteById() = {
    val saved = target.save(createEntity)

    target.deleteById(saved.id.get)

    val list = target.findAll()
    Assert.assertEquals(0, list.size)
  }
}
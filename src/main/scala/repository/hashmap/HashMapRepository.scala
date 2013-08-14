package repository.hashmap

import java.util.concurrent.ConcurrentHashMap
import model.Id
import model.Identifiable
import scala.collection.JavaConversions._
import model.Id.toEstateId
import model.Id.toLong
import repository.Repository
import scala.Option.option2Iterable

class HashMapRepository[T <: Identifiable ] extends Repository[T] {

	val repository:ConcurrentHashMap[Long, Option[T]] = new ConcurrentHashMap
	
	override def save (entity: T):T = {
		def getNewId():Long = repository.size + 1
		
		val newId:Id =  entity.id.getOrElse(getNewId)

		val result = entity.updateId(newId).asInstanceOf[T]
		repository.put(newId, Some(result))
		
		result
	}

	override def clear () {
		repository.clear
	}
	
	override def delete (entity: T) = 
		entity.id match {
			case Some(id) => repository.put(id, None)
			case None => 
		}
	
	override def deleteById(id: Id) = repository.remove(id)
	
	override def findAll(offset: Int = 0, limit: Int = 0) : List[T] = {
		repository.flatMap(item => item._2).toList
	}

}
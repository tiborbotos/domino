package repository

import model.Identifiable
import model.Id

trait Repository[T <: Identifiable] {
	def save (url: T) : T

	def clear
	
	def delete (url: T)
	
	def deleteById(id: Id)
	
	def findAll(offset: Int = 0, limit: Int = 0) : List[T]
}
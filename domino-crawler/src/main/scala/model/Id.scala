package model

/**
 * Id class used as an inner "database" id
 */
case class Id(id: Long)

object Id {

  implicit def toLong(value: Id): Long = value.id

  implicit def toEstateId(value: Long): Id = Id(value)

}

trait Identifiable {
  def id: Option[Id]

  def updateId(newId: Id): Identifiable
}
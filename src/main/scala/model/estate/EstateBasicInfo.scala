package model.estate

case class EstateBasicInfo (area: Option[Int],
		roomsCount: Option[Int], 
		halfRoomsCount: Option[Int], 
		floor: Option[Int],
		description: Option[String])
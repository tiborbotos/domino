package model.estate

import model.Url
import model.Id
import model.Identifiable

/**
 * Data object for storing all the estate related data
 */
case class Estate[+Domain] (override val id: Option[Id] = None,
		url: Url, 
		address: Option[EstateAddress] = None,
		fee: Option[EstateFee] = None,
		basicInfo: Option[EstateBasicInfo] = None,
		statusInfo: EstateStatusInfo = new EstateStatusInfo(),
		images: List[EstateImage] = List()
	) extends Identifiable {
	
	override def updateId(newId: Id) = this.copy( id = Some(newId))
	
}

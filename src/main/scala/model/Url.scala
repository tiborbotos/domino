package model

import java.util.Date

/**
 * URL data tag of the estate
 */
case class Url(override val id: Option[Id] = None,
		path: String, 
		label: String = "",
		urlFound: Date = new Date,
		urlParsed: Date = new Date,
		urlActive: Boolean = true // is the estate still rentable, or the url exists at all 
		) extends Identifiable {
	
	override def updateId(newId: Id) = this.copy( id = Some(newId))
	
	def domain: String = 
		if (path.indexOf("/") == -1 || path.indexOf("//") == -1)
			"" 
		else path.substring(0, path.indexOf("/", path.indexOf("//") + 2))
	
	override def toString = "Url(" + path + ")"
}
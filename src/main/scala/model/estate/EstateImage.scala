package model.estate

import model.Url
import model.Id

case class EstateImage ( id: Option[Id] = None, 
		url: Url, 
		thumbnail: Option[Url] = None)
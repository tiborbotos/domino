package model.estate

case class EstateAddress(
	fullAddress: String,
	city: String,
	district: Option[String] = None,
	address: Option[String] = None,
	country: Option[String] = None
	)

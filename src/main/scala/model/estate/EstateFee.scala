package model.estate

case class EstateFee(rentalFee: Option[Long],
	depositFee: Option[Long],
	utilitiesFee: Option[Long],
	commonFee: Option[Long])
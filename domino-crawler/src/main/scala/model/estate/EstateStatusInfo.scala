package model.estate

import java.util.Date

case class EstateStatusInfo ( firstFound: Date = new Date(), // when the estate found for the first time
		lastChecked: Date = new Date(), // is the url is accessible
		lastUpdated: Date = new Date(), // when was the last data update
		isActive: Boolean = true // is the estate still rentable
		)
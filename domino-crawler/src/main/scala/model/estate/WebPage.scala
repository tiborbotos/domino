package model.estate

import model.Url
import model.Domain

/**
 * Typed case class for storing a information and data of a web page.<br>
 * The WebPage is connected to a {@link EstateParser} by the DOMAIN type parameter 
 */
case class WebPage[+DOMAIN <: Domain] (html: String, url: Url)
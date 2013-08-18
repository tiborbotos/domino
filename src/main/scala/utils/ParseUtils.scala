package utils

import java.util.regex.Pattern
import scala.Long

sealed abstract class ParseUtils {}
object ParseUtils {
  def parseGroupFirst(page: String, pattern: String, dotall: Boolean = false): String = {
    val p: Pattern = (dotall) match {
      case true => Pattern.compile(pattern, Pattern.DOTALL);
      case _ => Pattern.compile(pattern);
    }

    val m = p.matcher(page);
    if (m.find()) {
      m.group(1).trim()
    } else
      ""
  }

  def asLong(value: String, defaultValue: Option[Long] = Some(0)): Option[Long] = {
    try {
      return Some(value.toLong)
    } catch {
      case e: NumberFormatException =>
        return defaultValue
    }
  }

  def asInt(value: String, defaultValue: Option[Int] = Some(0)): Option[Int] = {
    try {
      return Some(value.toInt)
    } catch {
      case e: NumberFormatException =>
        return defaultValue
    }
  }
}
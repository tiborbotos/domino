package model

/**
 * Domain class
 * Used only it's derived class for marking WebPage and EstateParser classes types 
 */
abstract class Domain(url: Url)

/**
 * Domain class for alberlet.hu
 */
class DomainAlberletHu extends Domain(url = Url(path = DomainAlberletHu.path))
object DomainAlberletHu { def path = "http://www.alberlet.hu" }

class DomainLocalhost extends Domain(url = Url(path = DomainLocalhost.path))
object DomainLocalhost { def path = "http://127.0.0.1:8080/estatelist" }
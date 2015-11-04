package hello

import scala.collection.mutable.Map


/**
 * @author santkumk
 */
class ChecksumAccumulator {
  private var sum = 0
  def add(b:Byte):Unit = sum += b
  def checksum():Int = (sum & 0xFF) + 1
}

/**
 * when a singleton object share a same name with the class it is called that class's companion object.
 * A class & its companion object must be define in the same source file.
 */
object ChecksumAccumulator{
  private val cache = Map[String,Int]()
  
  def calculate(s:String):Int = {
    if(cache.contains(s))cache(s)
    else{
      val acc = new ChecksumAccumulator
      for(c <- s) acc.add(c.toByte)
      val cs = acc.checksum()
      cache += (s -> cs)
      cs
    }
  }
}


/**
 * a singleton object that does not share the same name with a companion class is called as standalone object.
 * can be used as collection of utility methods, entry point of scala app
 * 
 * Java has the ability to create classes at runtime. These classes are known as Synthetic Classes or Dynamic Proxies.
 * the name of the synthetic class is objectname plus a $ sign. 
 * 
 */
object singleton{
  def main(args: Array[String]):Unit = println("checksumAccumulator")
}

/**
 * scala provides a trait, scala.Application alternative to the above (singleton with def main)
 * issue - cmd line options missing 
 */
/*object scalaApp extends Application{
  println("scala application")
}*/
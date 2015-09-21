package hello

object classHierarchies {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val l1 = new NonEmpty(3,Empty, Empty)           //> l1  : hello.NonEmpty = {.3.}
  l1 incl 4                                       //> res0: hello.IntSet = {.4{.3.}}
}


abstract class IntSet{
 def contains(x: Int):Boolean
 def incl(x: Int): IntSet
}

//class Empty extends IntSet{
//object here means single instance ~ singleton
object Empty extends IntSet{
	def contains(x: Int):Boolean = false
	def incl(x: Int): IntSet = new NonEmpty(x,Empty, Empty)
	override def toString() = "."
}

class NonEmpty(elem: Int, l: IntSet, r: IntSet) extends IntSet{
	
	def contains(x:Int): Boolean =
		if(x<elem) l contains x
		else if(x > elem) r contains x
		else true
	
	def incl(x: Int): IntSet =
		if( x < elem) new NonEmpty(x, l incl elem, r)
		else if(x > elem) new NonEmpty(x, l, r incl elem)
		else this
	
	override def toString = "{" + l + elem + r + "}"
	
}
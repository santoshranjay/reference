package hello

import scala.annotation.tailrec


object WS2 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  //sum of integers between a and b
  def sum1(a: Int, b: Int): Int = {
  	if(a>b) 0 else a + sum1(a+1,b)
  }                                               //> sum1: (a: Int, b: Int)Int
  sum1(5,7)                                       //> res0: Int = 18
  //tail recursive version of above sum
  //@tailrec
  def sum(f:Int => Int, a:Int, b:Int):Int = {
  	def loop(acc: Int, a: Int):Int = {
  		if(a>b) acc
  		else loop(acc+f(a),a+1)
  	}
  	loop(0,a)
  }                                               //> sum: (f: Int => Int, a: Int, b: Int)Int
  
  sum(x => x, 5, 4)                               //> res1: Int = 0
}
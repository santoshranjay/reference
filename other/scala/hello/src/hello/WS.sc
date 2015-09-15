package hello

object WS {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
	def and(x:Boolean, y: =>Boolean) = if(x) y else false
                                                  //> and: (x: Boolean, y: => Boolean)Boolean
	def or(x:Boolean, y: => Boolean) = if(x)true else y
                                                  //> or: (x: Boolean, y: => Boolean)Boolean
	and(true,false)                           //> res0: Boolean = false
	and(false,false)                          //> res1: Boolean = false
	and(true,true)                            //> res2: Boolean = true
	
	or(true,false)                            //> res3: Boolean = true
	or(false,true)                            //> res4: Boolean = true
	or(false,false)                           //> res5: Boolean = false
	
	def abs(x:Double) = if(x<0) -x else x     //> abs: (x: Double)Double
	
	def sqrt(x:Double) = {

    def sqrtIter(guess: Double): Double =
      if (isGoodEnough(guess)) guess else sqrtIter(improve(guess))
	
		def isGoodEnough(guess:Double) = abs(guess * guess-x)/x < 0.0001
		
		def improve(guess:Double) = (guess + x/guess ) /2
		
		sqrtIter(1.0)
	
	}                                         //> sqrt: (x: Double)Double
	
	sqrt(25)                                  //> res6: Double = 5.000023178253949
}
package hello

object product {
	//for the points on a given interval
	
	def prod(f: Int => Int)(a: Int, b: Int):Int = {
		if(a > b) 1
		else f(a) * prod(f)(a+1, b)
	}                                         //> prod: (f: Int => Int)(a: Int, b: Int)Int
	prod(x=>x*x)( 3,4)                        //> res0: Int = 144

	def fact(x: Int) = prod(x => x)(1,x)      //> fact: (x: Int)Int
	
	fact(5)                                   //> res1: Int = 120
	
	//generalize the sum and product
	
	def mapReduce(f:Int => Int, combine: (Int,Int) => Int, zero: Int)( a:Int , b:Int): Int = {
		if(a > b) zero
		else combine(f(a),mapReduce(f,combine,zero)(a+1,b))
	}                                         //> mapReduce: (f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b:
                                                  //|  Int)Int
                                                  
	//prod & fact w.r.t generalize function for sum/product
	def product(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f,(x,y) => x*y, 1)(a,b)
                                                  //> product: (f: Int => Int)(a: Int, b: Int)Int
	product(x=>x*x)(3,4)                      //> res2: Int = 144
}
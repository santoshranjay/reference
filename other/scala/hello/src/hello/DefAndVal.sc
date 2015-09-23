package hello

object DefAndVal {
  //http://stackoverflow.com/questions/18887264/what-is-the-difference-between-def-and-val-to-define-a-function
  /*
	1. Method def even evaluates on call and creates new function every time (new instance of Function1).
	2. With def you can get new function on every call
	3. val evaluates when defined, def - when called
	4. lazy val, evaluates when called firs time
	5. val evaluates when defined.def evaluates on every call, so performance could be worse then with val for multiple
	 calls. You'll get the same performance with a single call. And with no calls you'll get no overhead from def,
	 so you can define it even if you will not use it in some branches.
	With a lazy val you'll get a lazy evaluation: you can define it even if you will not use it in some branches,
	and it evaluates once or never, but you'll get a little overhead from double check locking on every access to your
	lazy val.
	As @SargeBorsch noted you could define method, and this is the fastest option:
	def even(i: Int): Boolean = i % 2 == 0
	But if you need a function (not method) for function composition or for higher order functions (like filter(even))
	compiler will generate a function from your method every time you are using it as function, so performance could by
	slightly worse than with val.
	*/

	def even: Int => Boolean = _%2 ==0        //> even: => Int => Boolean
	even eq even                              //> res0: Boolean = false
	val evenv: Int => Boolean = _%2 == 0      //> evenv  : Int => Boolean = <function1>
	evenv eq evenv                            //> res1: Boolean = true
	
	//different call different results
	def test:()=>Int = {
	 val r = util.Random.nextInt();
	 () => r
	}                                         //> test: => () => Int
	test()                                    //> res2: Int = 1862439887
	test()                                    //> res3: Int = 1927298777
	
	//different calls same result
	val testv:()=>Int = {
	 val r = util.Random.nextInt()
	 () => r
	 }                                        //> testv  : () => Int = <function0>
	 testv()                                  //> res4: Int = -1489004231
	 testv()                                  //> res5: Int = -1489004231
}
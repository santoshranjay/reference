package hello

object Book {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

	// scala treats String as higher level sequences of characters that can be queried with predicates.
	//the predicate _.isUpper is an example of function literal in scala
   "fadsfYfsad".exists(_.isUpper)                 //> res0: Boolean = true
   
   /*
   scala has two kind of variables,
   1. val (final, can never be reassigned during lifetime). but the object to which it refers could potentially still be changed.
   2. var (non-final)
   */
   val x = 2+2 //infer type to Int (x), does not infer function parameter type
                                                  //> x  : Int = 4
   //x = 3 + 4 //error
   var y = 2 +3                                   //> y  : Int = 5
   y = 3+3
   y                                              //> res1: Int = 6
   
   // return type Unit ~ void
   def greet() = println("Hello")                 //> greet: ()Unit
   greet()                                        //> Hello
 
  //loop (while) from imperative style to functional (function literal)
  "dfsdsa".foreach(ch => print(ch))               //> dfsdsa
  //if a function literal consist of one statement that takes a single argument, you need not explicitily name and
  // specify the argument
  "dfdff".foreach(print)                          //> dfdff
  //syntax of function literal
  (x: Int, y: Int) => x + y                       //> res2: (Int, Int) => Int = <function2>
  
  //for, right arry, left arg is val not var
  for (arg <- Array("hello","world")) println(arg)//> hello
                                                  //| world
                                                  
	//Ex. parameterize the instance with type[1] and values [2]
	val stra = new Array[String](3)           //> stra  : Array[String] = Array(null, null, null)
	stra(0) = "hello"; stra(1) = "world"; stra(2) = "!"
	//Rule: if a method take only one paramenter, can be called without a . or (). e.g, to
	for(i <- 0 to 2) print(stra(i))           //> helloworld!
	
	//all operations are method call in scala
	1 + 2                                     //> res3: Int(3) = 3
	(1).+(2)                                  //> res4: Int(3) = 3
	
	//FP Rule: methods should not have side effects.
	// use list. a scala Array is mutable sequence of objects that all share the same type e.g. Array[String].
	//scala list are immutable.
	val l = List(0,1,2)                       //> l  : List[Int] = List(0, 1, 2)
	//list operator/md :: - cons, prepend the elem, ::: - concatnate
	//val lp = List(1,2)::List(0)
	//Rule: if a method name ends with a : the md is invoked on the right operand
	val lp = 0 :: List(1,2)                   //> lp  : List[Int] = List(0, 1, 2)
	lp.foreach(print)                         //> 012
	val lc = List(1,2):::List(3,4)            //> lc  : List[Int] = List(1, 2, 3, 4)
	lc.foreach(print)                         //> 1234
	//usefule list methods
	val thrill = "fil" :: "nil" :: "until"::Nil
                                                  //> thrill  : List[String] = List(fil, nil, until)
 //count the no. of string that have lenght > 4
	thrill.count(x => x.length > 4)           //> res5: Int = 1
	//return a list adding y in each element
	thrill.map(x => x + "y")                  //> res6: List[String] = List(fily, nily, untily)
	
	//use tuples, tuple are immutable and can contain different types of elements
	//useful when we need to return multiple types of objects from a method. ex,
	val tup = (99, "Hello")                   //> tup  : (Int, String) = (99,Hello)
	print(tup._1);print(tup._2)               //> 99Hello
	
	//use sets & maps
	//for set & maps scala models mutability in the class hierarchy.scala.collection.mutable/immutable.set/map,
	// by default immutable need to import for mutable
	val romanMap = Map(1 -> "I", 2 -> "II")   //> romanMap  : scala.collection.immutable.Map[Int,String] = Map(1 -> I, 2 -> I
                                                  //| I)
	print(romanMap(2))
	//FP:Rule - try programming without var (in val only)
	//function is with side effect if it return type is Unit
	
	
}
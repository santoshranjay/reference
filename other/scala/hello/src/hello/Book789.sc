package hello

object Book789 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  //for loop
  //yield - generate a value to remember for each iteration
  //syntax for-yield - for clauses yield body
  
  val snum = Array("_a","_b","c","d", "_e")       //> snum  : Array[String] = Array(_a, _b, c, d, _e)
  
  def sval = for {
  	s <- snum
  	if(s startsWith "_")
  }yield s                                        //> sval: => Array[String]
  
  sval                                            //> res0: Array[String] = Array(_a, _b, _e)
  
  //exception handling
  //unlike java scala doesn't require to catch a checked exception or declare them in a throw clause (@thorws optional)
  //catch is like pattern mathcing
  // catch {
  // case ex:FileNotFoundException => //handle code
  //finally - similar to java
  //loan pattern - concise way of this
  // in java finally overrule any of the previous value/return.
  def f():Int = try{return 1}finally{return 2}    //> f: ()Int
  f                                               //> res1: Int = 2
  def g():Int = try{1}finally{2}                  //> g: ()Int
  g                                               //> res2: Int = 1
  
  //function to found scala file which does not start with _
  def findNormScala(ind: Int, args: Array[String]):String = {
   if(ind >= args.length) "None"
   if(args(ind) startsWith "_") findNormScala(ind+1, args)
   else if(args(ind) endsWith ".scala") args(ind)
   else findNormScala(ind+1, args)
   }                                              //> findNormScala: (ind: Int, args: Array[String])String

	// one diff in variable scoping in java & scala is that unlike java, scala allows to create a variable in the
	// inner scope with the same name as in outer scope hiding the one in outer scope
	val a = 1;                                //> a  : Int = 1
	{
	val a = 2
	print(a)
	}                                         //> 2
	print(a)
	
	//Functions - flavors
	//methods which are functions that are members of some object
	//function nested within function
	//function literals
	//function values
	
	//repeated parameters
	
}
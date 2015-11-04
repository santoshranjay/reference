package hello

object Book456 {
  /*
  In the absence of any return statement scala return the last value computed by the method.
  classes in scala can't have static members
  */
  
  //if you leave the = sign before the body of a function its result type will definitely be Unit
  def g() {"this string gets lost"}               //> g: ()Unit
  //is ~ to
  def gs():Unit = "this string gets lost"         //> gs: ()Unit
  
  // scala includes a special sytax for raw string ("""). the interior of raw string can contain any character,
  //special char, newline , quot mark except this syntax itself i.e., """.
  
  println(""" Print \. and /> ! ~%$ \\/\" - """)  //>  Print \. and /> ! ~%$ \\/\" - 
  //provide strinpMargin method to rmove the leading space from each line (in multiline string literal).
  //start with pipe |
  println("""|Welcome to Ultamix 3000.
  					 		|Type "HELP"  for help. """.stripMargin)
                                                  //> Welcome to Ultamix 3000.
                                                  //| Type "HELP"  for help. 
 //symbol literal, written by 'ident (e.g., 'cymbal). these are mapped to the instance of predefined class scala.Symbol
 //expanded by the compiler to a factory method invocation Symbol("cymbal")
 //use : want to update a record in database by field name.
 //symbols are interned
 //todo
 val sym = 'symb                                  //> sym  : Symbol = 'symb
 
 //literals
 val x = 0xFF                                     //> x  : Int = 255
 // octal - val ox = 02
 //The only operators that can be used as prefix operator +, - , ! and ~
 
 //post fix operators are methods that take no arguments. . can be left here if method take no arguments.
 "hello" toUpperCase                              //> res0: String = HELLO
 //object equality ==. reference equality in scala is eq / ne..
 val t = "he" + "llo" == "hello"                  //> t  : Boolean = true
 
 //A literal identifier is an arbitrary string enclosed in back ticks e.g., `yield` (Thread.`yield`)
}

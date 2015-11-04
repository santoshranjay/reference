package hello

object FFP {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  //todo
  //a method that requires a function as a parameter
//the function's type is (Int,Int) => Int
//e.g. maps from 2 Ints to an Int
def doWithOneAndTwo(f: (Int, Int) => Int) = {
  f(1, 2)
}                                                 //> doWithOneAndTwo: (f: (Int, Int) => Int)Int

//Explicit type declaration
val call1 = doWithOneAndTwo((x: Int, y: Int) => x + y)
                                                  //> call1  : Int = 3

//The compiler expects 2 ints so x and y types are inferred
val call2 = doWithOneAndTwo((x, y) => x + y)      //> call2  : Int = 3

//Even more concise syntax
val call3 = doWithOneAndTwo(_ + _)                //> call3  : Int = 3

println(call1, call2, call3)                      //> (3,3,3)
  
}
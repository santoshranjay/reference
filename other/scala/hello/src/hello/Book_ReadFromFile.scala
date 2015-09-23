package hello

import scala.io.Source
/**
 * @author santkumk
 */
class Book_ReadFromFile {
  
  def main(args: Array[String]) = {
    if(args.length > 0)
      for(line <- Source.fromFile(args(0)).getLines()) println(line.length + ":" + line)
     else
       Console.err.println("No Filename")
  }

  //todo
  //print formatted character count for the lines of a given file 
}


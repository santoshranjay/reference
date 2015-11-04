package hello

import scala.io.Source
/**
 * @author santkumk
 */
object ReadFromFile {
  
  def main(args: Array[String])  {
    
    if(args.length > 0)
//      for(line <- Source.fromFile(args(0)).getLines()) println(line.length + ":" + line)
      printFormatted2(args(0))
     else
       Console.err.println("Please provide the file path.")
  }

  
  /**
   * print formatted character count for the lines of a given file.the file is being read in memory first then 
   * iterated twice, first iteration to get the maximum width of the lines and second iteration to print each line
   * with proper padding according to the max width of lines.
   */
  def printFormatted(file: String) = {
      val lines = Source.fromFile(file).getLines().toList
      val longestLine = lines.reduceLeft( (a,b) => if(a.length > b.length) a else b)
      val widhtOfLongestLine = widthOfLength(longestLine)
      for(line <- lines){
        val padding = " " * (widhtOfLongestLine - widthOfLength(line))
        println(padding  + line.length + "|" + line)
      }
    }
  
  def widthOfLength(line: String):Int = line.length.toString.length
  
  /**
   * without storing in memory, iterate the file twice
   */
  def printFormatted2(file:String) = {
    var max = ""
    for(line <- Source.fromFile(file).getLines){
      max = if(line.length > max.length) line else max
    }
    for(line <- Source.fromFile(file).getLines){
      val padding = " " * (widthOfLength(max) - widthOfLength(line))
      println(padding + line.length + " | " + line)
    }
    
  }
}


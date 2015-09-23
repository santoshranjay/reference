package hello

object rationals {

new Rational(1,2)                                 //> res0: hello.Rational = 1/2

def x = new Rational(1,3)                         //> x: => hello.Rational
val y = new Rational(5,7)                         //> y  : hello.Rational = 5/7
val z = new Rational(3,2)                         //> z  : hello.Rational = 3/2

//1.//x.sub(y).sub(z)
x - y - z                                         //> res1: hello.Rational = -79/42


}

class Rational(x:Int, y:Int){
	def numer = x
	def denom = y
	
	//1.//def add(that: Rational) = new Rational(numer * that.denom + denom * that.numer, denom * that.denom)
	def +(that: Rational) = new Rational(numer * that.denom + denom * that.numer, denom * that.denom)
	
	override def toString() = numer + "/" + denom
	
	//1.//def neg = new Rational(-numer,denom)
	def unary_- = new Rational(-numer,denom)
	
	//1.// def sub(that: Rational) = new Rational(numer*that.denom - that.numer* denom, denom * that.denom)
	//2.//def sub(that: Rational) = add(that.neg)
	def -(that: Rational) = this + -that
		
	
}
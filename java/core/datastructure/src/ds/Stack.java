package ds;

import java.util.Arrays;
import java.util.List;

public class Stack<E> {

	private E[] stack;
	private volatile int  size;
	
	@SuppressWarnings("unchecked")
	public Stack(int size){
		stack = (E[])new Object[size];
		size = 0;
	}
	
	public void push(E e){
		ensureCapacity();
		stack[size++]=e;
	}
	
	public E pop(){
		if(isEmpty())return null;
		E e = stack[--size];
		stack[size]=null;
		return e;
	}
	private void ensureCapacity() {
		if(size==stack.length)stack = Arrays.copyOf(stack, 2*stack.length);
//		if(size < stack.length/2) stack = Arrays.copyOf(stack, stack.length/2);
	}
	
	public boolean isEmpty(){return size ==0;}
	
	@Override
	public String toString() {
		return Arrays.asList(stack).toString();
	}
	
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4,6,3,2,4,7);
		Stack<Integer> stack = new Stack<>(2);
		for (Integer integer : list) {
			stack.push(integer);
		}
		System.out.println(stack);
		while(!stack.isEmpty())System.out.println(stack.pop());
	}
}

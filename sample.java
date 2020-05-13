package chapter15;

import java.util.Scanner;
import java.util.Stack;

public class algebraicSolver {
	static Stack<Double> numbers = new Stack<>();
	static Stack<String> operators = new Stack<>();
	static String answer;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("What is the expression you want me to solve?");
		answer = in.nextLine();
		in.close();
		boolean lastIsNumber = false;
		String c;
		String testParenthesis = answer.substring(0);
		int counter = 0;
		try {
			for(int i = 0; i < answer.length(); i++)
				if(answer.charAt(i) == '+' || answer.charAt(i) == '-' || answer.charAt(i) == '/' || answer.charAt(i) == '*' || answer.charAt(i) == 'x') {
					counter++;
					if(counter == 2)
						throw new Exception();
				} else
					counter = 0;
		} catch (Exception e) {
			System.err.println("There cannot be 2 operators in a row");
			return;
		}
		try {
			for(int i = 0; i < answer.length(); i++) {
				char p = answer.charAt(i);
				if(!(p=='.'||p=='0'||p=='1'||p=='2'||p=='3'||p=='4'||p=='5'||p=='6'||p=='7'||p=='8'||p=='9'||p=='*'||p=='x'||p=='/'||p=='+'||p=='-'||p=='('||p==')'||p=='['||p==']'||p=='}'||p=='{'))
					throw new Exception();
			}
		} catch (Exception e) {
			System.err.println("Inputted incorrect character");
			return;
		}
		Stack<String> parenthesis = new Stack<>();
		while(testParenthesis.length() != 0)
			try {
				c = testParenthesis.substring(0,1);
				testParenthesis = testParenthesis.substring(1);
				if(c.equals("(") || c.equals("{") || c.equals("[")) 
					parenthesis.push(c);
				if(c.equals(")") || c.equals("]") || c.equals("}")) {
					String p = parenthesis.pop();
					if((p.equals("(") && !c.equals(")")) || (p.equals("[") && !c.equals("]")) || (p.equals("{") && !c.equals("}")))  
						throw new Exception();
				}
			} catch (Exception e) {
				System.err.println("The parenthesis do not match");
				return;
			}
		if(!parenthesis.isEmpty()) {
			System.err.println("The parenthesis do not match");
			return;
		}
		lastIsNumber = false;
		boolean decimalPoint = false;
		try {
			for(int i = 0; i < answer.length(); i++) {
				c = answer.substring(i,i+1);
				try {
					Integer.parseInt(c);
					lastIsNumber = true;
				} catch (NumberFormatException e) {
					if(c.equals("."))
						if(!decimalPoint && lastIsNumber)
							decimalPoint = true;
						else
							throw new Exception();
					else {
						lastIsNumber = false;
						decimalPoint = false;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("You placed 2 decimal points in a single number or you forgot to put a number before a decimal point");
			return;
		}
		lastIsNumber = false;
		decimalPoint = false;
		int decimalPlace = 0;
		while(answer.length() != 0) {
			c = answer.substring(0,1);
			answer = answer.substring(1);
			try {
				Integer.parseInt(c);
				if(lastIsNumber == false)
					numbers.push(Double.parseDouble(c));
				else
					if(decimalPoint) {
						numbers.push((Double) (numbers.pop() + Math.pow(0.1, decimalPlace)*Double.parseDouble(c)));
						decimalPlace++;
					} else 
						numbers.push((Double) (numbers.pop())*10 + Double.parseDouble(c));
				lastIsNumber = true;
			} catch (NumberFormatException e) {
				decimalPoint = false;
				lastIsNumber = false;
				if (c.equals("(") || c.equals("{") || c.equals("["))
					operators.push(c);
				else if (c.equals("x") || c.equals("+") || c.equals("*") || c.equals("/") || c.equals("-")) {
					while (!operators.empty() && precedence(operators.peek(), c))
						evaluateTop();
					operators.push(c);
				} else if (c.equals(")")) {
					while (!operators.peek().equals("("))
						evaluateTop();
					operators.pop();
				} else if (c.equals("]")) {
					while (!operators.peek().equals("["))
						evaluateTop();
					operators.pop();
				} else if (c.equals("}")) {
					while (!operators.peek().equals("{"))
						evaluateTop();
					operators.pop();
				} else if (c.equals(".")) {
					lastIsNumber = true;
					decimalPoint = true;
					decimalPlace = 1;
				}
			}
			/*
			System.out.print(numbers);
			System.out.println(operators);
			*/
		}
		while(!operators.empty()) {
			evaluateTop();
			/*
			System.out.print(numbers);
			System.out.println(operators);
			*/
		}
		System.out.println(numbers.peek());
	}
	public static void evaluateTop() {
		Double b = numbers.pop();
		Double a = numbers.pop();
		String operator = operators.pop();
		if(operator.equals("*") || operator.equals("x"))
			numbers.push(a*b);
		else if (operator.equals("/"))
			numbers.push(a/b);
		else if (operator.equals("+"))
			numbers.push(a+b);
		else if (operator.equals("-"))
			numbers.push(a-b);
	}
	/**
	 * To calculate the order of operations
	 * @param stack the operator in the stack
	 * @param compare the operator needed to compare
	 * @return if operator in stack is higher than the operator compared to
	 */
	public static boolean precedence(String stack, String compare) {
		int a = 0,b = 0;
		if(stack.equals("*") || stack.equals("/") || stack.equals("x")) 
			a = 1;
		else if(stack.equals("+") || stack.equals("-")) 
			a = 0;
		else
			a = -1;
		if(compare.equals("*") || compare.equals("/") || compare.equals("x"))
			b = 1;
		else if(compare.equals("+") || compare.equals("-"))
			b = 0;
		else
			b = -1;
		return (a>=b);
	}
}

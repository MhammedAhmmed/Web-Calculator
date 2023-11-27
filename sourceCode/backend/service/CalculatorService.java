package com.Calculator.demo.service;
public class CalculatorService {
	public String add(double firstOperand, double secondOperand){
		return handelAnswer(firstOperand + secondOperand);
	}
	public String subtraction(double firstOperand, double secondOperand){
		return handelAnswer(firstOperand - secondOperand);
	}
	public String multiplication(double firstOperand, double secondOperand){ // for multiplication and square
		return handelAnswer(firstOperand * secondOperand);
	}
	public String division(double firstOperand, double secondOperand){ // for division & multiplicationInverse
		if(secondOperand  == 0)
			return "Cannot divide by zero";
		return handelAnswer(firstOperand / secondOperand);
	}
	public String modulo(double firsOperand, double secondOperand){
		if(secondOperand == 0){
			return "Result is undefined";
		}
		return handelAnswer(firsOperand % secondOperand);
	}
	public String squareRoot(double operand){
		if(operand < 0)
			return "Invalid input";
		return handelAnswer(Math.sqrt(operand));
	}
	private String handelAnswer(double answer){
		if(Math.floor(answer) == Math.ceil(answer))
			return Long.toString((long) answer);
		return Double.toString(answer);
	}
}

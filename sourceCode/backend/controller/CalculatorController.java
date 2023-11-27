package com.Calculator.demo.controller;
import com.Calculator.demo.service.CalculatorService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CalculatorController {
    CalculatorService calculatorService = new CalculatorService();
        @GetMapping("/addition/{firstAndSecondOperand}")
        public ResponseEntity<String> addition(@PathVariable String firstAndSecondOperand){
            String []operands = firstAndSecondOperand.split(" ");
            double firstOperand = Double.parseDouble(operands[0]);
            double secondOperand = Double.parseDouble(operands[1]);
            return new ResponseEntity<>(calculatorService.add(firstOperand,secondOperand), HttpStatus.OK);
        }
        @GetMapping("/subtraction/{firstAndSecondOperand}")
        public ResponseEntity<String> subtraction(@PathVariable String firstAndSecondOperand){
            String []operands = firstAndSecondOperand.split(" ");
            double firstOperand = Double.parseDouble(operands[0]);
            double secondOperand = Double.parseDouble(operands[1]);
            return new ResponseEntity<>(calculatorService.subtraction(firstOperand,secondOperand), HttpStatus.OK);
        }
        @GetMapping("/division/{firstAndSecondOperand}")
        public ResponseEntity<String> division(@PathVariable String firstAndSecondOperand){
            String []operands = firstAndSecondOperand.split(" ");
            double firstOperand = Double.parseDouble(operands[0]);
            double secondOperand = Double.parseDouble(operands[1]);
            return new ResponseEntity<>(calculatorService.division(firstOperand,secondOperand), HttpStatus.OK);
        }
        @GetMapping("/multiplication/{firstAndSecondOperand}")
        public ResponseEntity<String> multiplication(@PathVariable String firstAndSecondOperand){
            String []operands = firstAndSecondOperand.split(" ");
            double firstOperand = Double.parseDouble(operands[0]);
            double secondOperand = Double.parseDouble(operands[1]);
            return new ResponseEntity<>(calculatorService.multiplication(firstOperand,secondOperand), HttpStatus.OK);
        }
        @GetMapping("/modulo/{firstAndSecondOperand}")
        public ResponseEntity<String> modulo(@PathVariable String firstAndSecondOperand){
            String []operands = firstAndSecondOperand.split(" ");
            double firstOperand = Double.parseDouble(operands[0]);
            double secondOperand = Double.parseDouble(operands[1]);
            return new ResponseEntity<>(calculatorService.modulo(firstOperand,secondOperand), HttpStatus.OK);
        }
        @GetMapping("/square/{singleOperand}")
        public ResponseEntity<String> square(@PathVariable String singleOperand){
            double Operand = Double.parseDouble(singleOperand);
            return new ResponseEntity<>(calculatorService.multiplication(Operand,Operand), HttpStatus.OK);
        }
        @GetMapping("/squareRoot/{singleOperand}")
        public ResponseEntity<String> squareRoot(@PathVariable String singleOperand){
            double Operand = Double.parseDouble(singleOperand);
            return new ResponseEntity<>(calculatorService.squareRoot(Operand), HttpStatus.OK);
        }
        @GetMapping("/multiplicationInverse/{singleOperand}")
        public ResponseEntity<String> multiplicationInverse(@PathVariable String singleOperand){
            double Operand = Double.parseDouble(singleOperand);
            return new ResponseEntity<>(calculatorService.division(1.0,Operand), HttpStatus.OK);
        }
}

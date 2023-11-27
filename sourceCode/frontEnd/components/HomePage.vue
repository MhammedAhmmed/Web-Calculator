<template>
    <div class="fullPage">
        <div class="calculator">
            <form action="">
                <h3>{{ equation }}</h3>
                <h2>{{ display }}</h2>

                <div>
                    <button class="opeator" type="button" @click="UpadatedDisplay('%')">%</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('CE')">CE</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('C')">C</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('DEL')">&#9003;</button>
                </div>
                <div>
                    <button class="opeator" type="button" @click="UpadatedDisplay('1/x')">¹/ₓ</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('x²')">x&#178;</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('√x')">&radic;</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('÷')">&#xf7;</button>
                </div>
                <div>
                    <button type="button" @click="UpadatedDisplay('7')">7</button>
                    <button type="button" @click="UpadatedDisplay('8')">8</button>
                    <button type="button" @click="UpadatedDisplay('9')">9</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('x')">x</button>
                </div>
                <div>
                    <button type="button" @click="UpadatedDisplay('4')">4</button>
                    <button type="button" @click="UpadatedDisplay('5')">5</button>
                    <button type="button" @click="UpadatedDisplay('6')">6</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('-')">-</button>
                </div>
                <div>
                    <button type="button" @click="UpadatedDisplay('1')">1</button>
                    <button type="button" @click="UpadatedDisplay('2')">2</button>
                    <button type="button" @click="UpadatedDisplay('3')">3</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('+')">+</button>
                </div>
                <div>
                    <button type="button" @click="UpadatedDisplay('∓')">∓</button>
                    <button type="button" @click="UpadatedDisplay('0')">0</button>
                    <button type="button" @click="UpadatedDisplay('.')">.</button>
                    <button class="opeator" type="button" @click="UpadatedDisplay('=')">=</button>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            equation: '',
            display: '0',
            operandCounter: 0,
            firstOperand: '',
            secondOperand: '',
            operator: '',
            formatEquation: '',
            preValue: '',
            result: '',
            operand: ''
        }
    },
    methods: {
        async UpadatedDisplay(value) {
            if (this.isDigit(value)) {
                this.handelFirstOperand(value)
                this.handelSecondOperand(value)
            }
            else if (this.isDeleteOperator(value)) {
                this.deletOperators(value)
            }
            else if (value === '∓') {
                this.updateNumberSing()
            }
            else if (value === '=') {
                if ((value === '=' && this.preValue === '=') || this.operandCounter === 1)
                    return;
                if (this.ValiditeEquation(value))
                    await this.sendToBackendBinary(value);
                else {
                    this.display = 'Invalid input';
                    this.deletOperators('CE');
                    return;
                }
            }

            else { //operator
                if (this.isBinaryOperation(value)) {
                    if (this.operandCounter === 3) { // exist pre operator so calculate first the pre equation
                        if (this.ValiditeEquation(value))
                            await this.sendToBackendBinary(value);
                    } else {
                        this.operator = value
                        this.operandCounter = 2
                    }
                }
                else { //unaryOperation
                    if (this.ValiditeEquation(value))
                        await this.sendToBackendUnary(value)
                    else {
                        this.display = 'Invalid input'
                        this.deletOperators('CE')
                        return
                    }
                }
            }
            this.handleDisplay();

            this.equation = this.firstOperand + this.operator + this.secondOperand
            this.preValue = value
        },
        handelFirstOperand(value) {
            if (this.operandCounter === 0 || this.operandCounter === 1) {
                if (!(value === '.' && this.containDot(this.firstOperand))) {
                    this.firstOperand += value
                    this.operandCounter = 1
                }
            }
        },
        handelSecondOperand(value) {
            if (this.operandCounter === 2 || this.operandCounter === 3) {
                if (!(value === '.' && this.containDot(this.secondOperand))) {
                    this.secondOperand += value
                    this.operandCounter = 3
                }
            }
        },
        handleDisplay() {
            if (this.operandCounter === 1) {
                this.display = this.firstOperand
            }
            else if (this.operandCounter === 3) {
                this.display = this.secondOperand
            }
            else if (this.display === 'Cannot divide by zero' || this.display === 'Result is undefined' || this.display === 'Invalid input') {
                return
            }
            else { // 2
                this.display = ''
            }
        },
        ValiditeEquation(value) {
            if (value === '=' || this.isBinaryOperation(value)) {
                if (this.firstOperand != '' && this.operator != '' && this.secondOperand != '' && this.firstOperand != '.' && this.secondOperand != '.')
                    return true
            }
            else if (this.isunaryOperation(value)) {
                if (this.operandCounter === 1 || this.operandCounter === 3) {
                    return true
                }
            }
            return false
        },
        updateNumberSing() {
            if (this.operandCounter === 1) {
                if (this.firstOperand != '' && this.firstOperand != '0') {
                    if (this.firstOperand.charAt(0) === '-') { //negative number
                        this.firstOperand = this.firstOperand.substring(1, this.firstOperand.length)
                    }
                    else {
                        this.firstOperand = '-' + this.firstOperand
                    }
                }
            } else {
                if (this.secondOperand != '' && this.secondOperand != '0') {
                    if (this.secondOperand.charAt(0) === '-') {
                        this.secondOperand = this.secondOperand.substring(1, this.secondOperand.length)
                    }
                    else {
                        this.secondOperand = '-' + this.secondOperand
                    }
                }
            }
        },
        deletOperators(value) {
            if (value === 'C' || value === 'CE') {
                this.equation = '',
                    this.firstOperand = '',
                    this.secondOperand = '',
                    this.operator = '',
                    this.preValue = '',
                    this.operandCounter = 0,
                    this.operand = '',
                    this.formatEquation = ''
            }
            else {  //DEL
                if (this.operandCounter === 1) {
                    if (this.firstOperand != '') {
                        this.firstOperand = this.firstOperand.substring(0, this.firstOperand.length - 1)
                    }
                }
                else {
                    if (this.secondOperand != '') {
                        this.secondOperand = this.secondOperand.substring(0, this.secondOperand.length - 1)
                        if (this.secondOperand === '')
                            this.operandCounter = 2
                    }
                    else {
                        this.operator = ''
                        this.operandCounter = 1
                    }
                }
            }
        },
        isDigit(value) {
            return (value === '1' || value === '2' || value === '3' ||
                value === '4' || value === '5' || value === '6' ||
                value === '7' || value === '8' || value === '9' ||
                value === '0' || value === '.')

        },
        isunaryOperation(value) {

            return (value === '1/x' || value === 'x²' || value === '√x')
        },
        isBinaryOperation(value) {
            return (value === '÷' || value === 'x' || value === '+' || value === '-' || value === '%')
        },
        isDeleteOperator(value) {
            return (value === 'C' || value === 'CE' || value === 'DEL')
        },
        containDot(num) { // handel dot (.)
            for (let i = 0; i < num.length; i++) {
                if (num.charAt(i) === '.')
                    return true;
            }
            return false;
        },
        async sendToBackendBinary(value) { // 5 + 4                
            this.formatEquation = this.firstOperand + ' ' + this.secondOperand
            if (this.operator === '+') {
                const url = `http://localhost:5050/equation/addition/${this.formatEquation}`;
                var data = await fetch(url);
                var response = await data.text();
            }
            if (this.operator === '-') {
                const url = `http://localhost:5050/equation/subtraction/${this.formatEquation}`;
                data = await fetch(url);
                response = await data.text();
            }
            if (this.operator === '÷') {
                const url = `http://localhost:5050/equation/division/${this.formatEquation}`;
                data = await fetch(url);
                response = await data.text();
            }
            if (this.operator === 'x') {
                const url = `http://localhost:5050/equation/multiplication/${this.formatEquation}`;
                data = await fetch(url);
                response = await data.text();
            }
            if (this.operator === '%') {
                const url = `http://localhost:5050/equation/modulo/${this.formatEquation}`;
                data = await fetch(url);
                response = await data.text();
            }
            if (response === 'Cannot divide by zero' || response === 'Result is undefined') {
                this.display = response;
                this.deletOperators('C');
            }
            else {
                if (value === '=') {
                    this.display = response;
                    this.firstOperand = response
                    this.operator = ''
                    this.secondOperand = ''
                    this.operandCounter = 1
                }
                else {
                    this.firstOperand = response;
                    this.operator = value
                    this.secondOperand = ''
                    this.operandCounter = 2
                    this.equation = this.firstOperand + this.operator
                }
            }
        },
        async sendToBackendUnary(value) {
            if (this.operandCounter === 1)
                this.operand = this.firstOperand;
            else // operandCounter = 3
                this.operand = this.secondOperand

            if (value === 'x²') {
                const url = `http://localhost:5050/equation/square/${this.operand}`;
                var data = await fetch(url);
                var response = await data.text();
            }
            if (value === '√x') {
                const url = `http://localhost:5050/equation/squareRoot/${this.operand}`
                data = await fetch(url);
                response = await data.text();
            }
            if (value === '1/x') {
                const url = `http://localhost:5050/equation/multiplicationInverse/${this.operand}`
                data = await fetch(url);
                response = await data.text();
            }

            if (response === 'Cannot divide by zero' || response === 'Invalid input') {
                this.display = response;
                this.deletOperators('CE');
            }
            else {
                this.display = response;
                if (this.operandCounter === 1) {
                    this.firstOperand = response
                    this.equation = this.firstOperand
                }
                else { // operandCounter = 3
                    this.secondOperand = response
                    this.equation = this.firstOperand + this.operator + this.secondOperand
                }
            }
        },
    }
}
</script>


<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.fullPage {

    width: 100%;
    height: 100vh;
    background: #aed5df;
    display: flex;
    align-items: center;
    justify-content: center;

}

.calculator {
    background: black;
    border-radius: 25px;
    padding: 20px;

}

button {
    border: 0;
    outline: 0;
    width: 55px;
    height: 55px;
    border-radius: 10px;
    background: rgb(37, 154, 158);
    box-shadow: -8px -8px 15px rgba(255, 255, 255, 0.15), 5px 5px 15px rgba(0, 0, 0, 0.1);
    color: white;
    margin: 10px;
    font-size: 17px;

}

.opeator {
    background-color: rgb(141, 34, 127);
}

h2,
h3 {
    text-align: right;
    margin: 20px 0;
    padding: 10px;
    justify-content: flex-end;
    flex: 1;
    color: white;
    background-color: rgba(250, 248, 248, 0.143);
    border-radius: 20px;
}
</style>

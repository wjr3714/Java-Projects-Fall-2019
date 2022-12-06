package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;
import java.util.*;

/**
 * A calculation represented by a binary operator and its two operands.
 *
 * @see ExpressionNode
 * @see DendronNode
 * @see ParseTree
 * @see Machine
 *
 * @author William J. Reid (wjr3714)
 */
public class BinaryOperation implements ExpressionNode {

    /** Operator symbol used for addition. */
    public static final String ADD = "+";

    /** Operator symbol used for division. */
    public static final String DIV = "/";

    /** Operator symbol used for multiplication. */
    public static final String MUL = "*";

    /** Operator symbol used for subtraction. */
    public static final String SUB = "-";

    /** All legal binary operators for use with parsers. */
    public static final Collection<String> OPERATORS = Arrays.asList("+", "/", "*", "-");

    //Constructor Instance Fields
    private String operator;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    /**
     * Create a Binary Operation node.
     * @param operator A string representation of the operand.
     * @param leftChild The left operand.
     * @param rightChild The right operand.
     */
    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild){
        assert OPERATORS.contains(operator) && leftChild != null && rightChild != null;
        this.operator = operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }


    /**
     * Compute the result of applying the operator to both operands.
     *
     * @param symTab The symbol table, if needed, to fetch variable values
     * @return The result of the evaluation
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {

        int leftSubNode = leftChild.evaluate(symTab);
        int rightSubNode = rightChild.evaluate(symTab);

        // For default case in switch statement (need a return statement if a case is not provided).
        int empty = (int) Double.NaN;

        switch (operator) {
            case ADD:
                return leftSubNode + rightSubNode;
            case DIV:
                try{
                    return leftSubNode / rightSubNode;
                }
                //Error Check: Division by zero
                catch (ArithmeticException errorDivisionByZero){
                String info = "Cannot divide by zero \n" + leftSubNode + " / " + rightSubNode ;
                Errors.report(Errors.Type.DIVIDE_BY_ZERO, info);
                }
            case MUL:
                return leftSubNode * rightSubNode;
            case SUB:
                return leftSubNode - rightSubNode;
            default:
                return empty;
        }

    }

    /**
     * Show the code rooted at this node, using infix format, on standard output. Infix notation is when operators are
     * written in-between their operands. For example: ( X + Y )
     */
    @Override
    public void infixDisplay() {
        System.out.print("( ");
        leftChild.infixDisplay();
        System.out.print(" " + operator + " ");
        rightChild.infixDisplay();
        System.out.print(" )");
    }

    /**
     * Emit the necessary Machine instructions to compute of the binary operation. This is done by popping two values
     * off the stack, applying the operator, and pushing the answer.
     *
     * @return The Machine Instructions for this node required to perform the operation
     */
    @Override
    public List<Machine.Instruction> emit() {

        // Contains the instructions for the operators: +, /, *, -
        ArrayList<Machine.Instruction> instructionsList = new ArrayList<>();

        // Add instructions for addition
        if (this.operator.contains("+")){
            instructionsList.addAll(leftChild.emit());
            instructionsList.addAll(rightChild.emit());
            Machine.Add addOperation = new Machine.Add();
            instructionsList.add(addOperation);
        }
        // Add instructions for division
        else if(this.operator.contains("/")) {
            instructionsList.addAll(leftChild.emit());
            instructionsList.addAll(rightChild.emit());
            Machine.Divide divideOperation = new Machine.Divide();
            instructionsList.add(divideOperation);
        }
        // Add instructions for multiplication
        else if(this.operator.contains("*")) {
            instructionsList.addAll(leftChild.emit());
            instructionsList.addAll(rightChild.emit());
            Machine.Multiply multiplyOperation = new Machine.Multiply();
            instructionsList.add(multiplyOperation);
        }
        // Add instructions for subtraction
        else if(this.operator.contains("-")) {
            instructionsList.addAll(leftChild.emit());
            instructionsList.addAll(rightChild.emit());
            Machine.Subtract subtractOperation = new Machine.Subtract();
            instructionsList.add(subtractOperation);
        }

        return instructionsList;
    }
}

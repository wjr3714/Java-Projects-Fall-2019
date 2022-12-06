package dendron.tree;

import dendron.machine.Machine;
import java.util.*;

/**
 * A calculation represented by a unary operator and its operand.
 *
 * @see ExpressionNode
 * @see DendronNode
 * @see ParseTree
 * @see Machine
 *
 * @author William J. Reid (wjr3714)
 */
public class UnaryOperation implements ExpressionNode {

    /** Operator symbol used for negation. */
    public static final String NEG = "_";

    /** Operator symbol used for square root. */
    public static final String SQRT = "#";

    /** Container of all legal unary operators, for use by parsers */
    public static final Collection<String> OPERATORS = Arrays.asList("_", "#");

    /** The string representation of the operation. */
    private String operator;

    /** The operand. */
    private ExpressionNode expr;

    /**
     * Create Unary Operation Node.
     * @param operator The string representation of the operation.
     * @param expr The operand.
     */
    public UnaryOperation(String operator, ExpressionNode expr){
        assert OPERATORS.contains(operator) && expr != null;
        this.operator = operator;
        this.expr = expr;
    }


    /**
     * Compute the result of evaluating the expression and applying the operator to it.
     * @param symTab The symbol table to fetch variable values
     * @return The result of the evaluation
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {

        int unaryExpression = expr.evaluate(symTab);

        // For default case (need a return statement if a case is not provided).
        int empty = (int) Double.NaN;

        if (operator.equals(NEG)){
            return unaryExpression * -1;
        }
        else if (operator.equals(SQRT)){
            return (int) Math.sqrt(unaryExpression);
        }
        else{
            return empty;
        }
    }

    /**
     * Print, on standard output, the infixDisplay of the child nodes preceded by the operator.
     * Note: without an intervening blank.
     */
    @Override
    public void infixDisplay() {
        System.out.print(operator);
        expr.infixDisplay();
    }

    /**
     * Emit the necessary Machine instructions to compute of the unary operation. This is done by popping two values
     * off the stack, applying the operator, and pushing the answer.
     *
     * @return The Machine Instructions for this node required to perform the operation
     */
    @Override
    public List<Machine.Instruction> emit() {

        ArrayList<Machine.Instruction> instructionList = new ArrayList<>();

        if(this.operator.contains("_")){
            instructionList.addAll(expr.emit());
            Machine.Negate negate = new Machine.Negate();
            instructionList.add(negate);
        }
        else if(this.operator.contains("#")){
            instructionList.addAll(expr.emit());
            Machine.SquareRoot squareRoot = new Machine.SquareRoot();
            instructionList.add(squareRoot);
        }

        return instructionList;
    }
}

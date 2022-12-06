/*
 * file: ParseTree.java
 */

package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;
import java.util.*;

/**
 * Operations that are done on a Dendron code parse tree.
 *
 * @see Assignment
 * @see BinaryOperation
 * @see Constant
 * @see Machine
 * @see Print
 * @see Program
 * @see UnaryOperation
 * @see Variable
 *
 * @author William J. Reid (wjr3714)
 */
public class ParseTree {

    /** The token list (Strings). */
    private Program tokens;

    /** The table where values are stored. */
    private Map<String, Integer> symTab;

    /** The index that keeps track of the iterator or parser's location. */
    private int nodeIndex;


    /**
     * Parse the entire list of program tokens. The program is * sequence of actions (statements), each of which
     * modifies something in the program's set of variables. The resulting parse tree is stored internally.
     *
     * @param program The token list (Strings)
     */
    public ParseTree(List<String> program) {
        this.tokens = new Program();
        this.symTab = new HashMap<>();
        this.nodeIndex = 0;


        /*
           Before parsing through the Dendron file, check for as many errors as possible.
           These are the so called "Checked Errors". The other errors accounted for outside of the constructor are the
           "Unchecked Errors", such as division by zero.
        */

        // Error Check: Illegal tokens in Dendron file.
        for (String s : program) {
            if (!s.matches("^[a-zA-Z].*") && !s.matches("[0-9].*") && !s.equals("+") && !s.equals("-")
                    && !s.equals("*") && !s.equals("/") && !s.equals("#") && !s.equals("_") && !s.equals(":=")
                    && !s.equals("@")) {

                String info = "The token '" + s + "' is not of the following types: variable, integer, " +
                        "unary or binary operator.";

                Errors.report(Errors.Type.ILLEGAL_VALUE, info);
            }
        }

        // Error Check: A variable must always follow ":="
        for (int i = 0; i < program.size()-1; i++) {
            if (program.get(i).equals(":=")) {
                if (!program.get(i + 1).matches("^[a-zA-Z].*")) {
                    String info = "A variable must always follow '" + program.get(i) + "', and '" +
                            program.get(i + 1) + "' is not of type 'variable'.";
                    Errors.report(Errors.Type.ILLEGAL_VALUE, info);
                }
            }

            //  Error Check: An operator, variable, or integer must always follow the print statement "@"
            //  For example, this ensures two "@" are not next to each other.
            if (program.get(i).equals("@") ){
                int counter = 0;
                String[] operators = new String[]{"+","-","*","/","#","_"};
                for (String operator : operators) {
                    counter++;
                    if (program.get(i + 1).matches("^[a-zA-Z0-9_].*") || program.get(i + 1).equals(operator)) {
                        break;
                    }
                    else if (counter == operators.length) {
                        String info = "A variable, integer, unary or binary operator must always follow \nthe print character '" + program.get(i) + "', and the expression '" +
                                program.get(i + 1) + "' is not any of these types.";
                        Errors.report(Errors.Type.ILLEGAL_VALUE, info);
                    }
                }
            }
        }

        // Error Check: Cannot end instructions on ":="
        if (program.get(program.size() - 1).equals(":=")){
            String info = " '" + program.get(program.size() - 1) + "' requires a variable to follow but none was provided.";
            Errors.report(Errors.Type.PREMATURE_END, info);
        }
        // Error Check: Cannot end instructions on "@"
        else if (program.get(program.size() - 1).equals("@")){
            String info = " '" + program.get(program.size() - 1) + "' requires an expression containing a variable, " +
                    "constant, unary or binary operator to follow but none was provided.";
            Errors.report(Errors.Type.PREMATURE_END, info);
        }

        // If the preliminary error checkpoints passed, parse the entire list of program tokens.
        while (program.size() != 0 && nodeIndex < program.size()) {
            this.tokens.addAction(parseAction(program));
        }
    }

    /**
     * Parse the next action (statement) in the list.
     *
     * @param program the list of tokens
     * @return a parse tree for the action
     */
    private ActionNode parseAction(List<String> program) {


        // Create Assignment & Print objects
        if (program.get(nodeIndex).equals(":=")) {
            nodeIndex++;

            // Error Check: Ensure that a variable is always set equal to a value (e.g., not left blank).
            try {
                Assignment assignment = new Assignment(program.get(nodeIndex++), parseExpr(program));
                return assignment;
            }
            catch (IndexOutOfBoundsException iobe){
                String info = "The variable '" + program.get(nodeIndex-1) + "' is called but not acted upon." ;
                Errors.report(Errors.Type.PREMATURE_END, info);
            }
        }
        else if (program.get(nodeIndex).equals("@")){
            nodeIndex++;
            Print print = new Print(parseExpr(program));
            return print;
        }
        else {
            String info = "Unexpected token '" + program.get(nodeIndex) + "' encountered while parsing through Dendron source file." ;
            Errors.report(Errors.Type.ILLEGAL_VALUE, info);
            return null;
        }
        return null;
    }

    /**
     * Parse the next expression in the list.
     *
     * @param program the list of tokens
     * @return a parse tree for this expression
     */
    private ExpressionNode parseExpr(List<String> program) {

        // Check is the node is a valid integer (Constant), otherwise, it must be a binary operation,
        // unary operation or a variable.

        // Check is the node is a valid integer (Constant).
        try {
            ExpressionNode constant = new Constant(Integer.parseInt(program.get(nodeIndex)));
            nodeIndex++;
            return constant;
        }
        // If not an integer, the expression node must be either a binary operation, unary operation or a variable.
        catch (NumberFormatException exception) {

            // Variables: always strings starting with alphabetic characters.
            if(program.get(nodeIndex).matches("^[a-zA-Z].*")) {
                Variable var = new Variable(program.get(nodeIndex++));
                return var;
            }
            // Unary Operation
            else if (UnaryOperation.OPERATORS.contains(program.get(nodeIndex))) {
                UnaryOperation unaryOperation = new UnaryOperation(program.get(nodeIndex++), parseExpr(program));
                return unaryOperation;
            }
             // Binary Operation
            else if (BinaryOperation.OPERATORS.contains(program.get(nodeIndex))) {
                BinaryOperation binaryOperation = new BinaryOperation(program.get(nodeIndex++), parseExpr(program), parseExpr(program));
                return binaryOperation;
            }
            // Error Check: Unexpected token
            else{
                String info = "Unexpected token '" + program.get(nodeIndex) + "' encountered while parsing through Dendron source file." ;
                Errors.report(Errors.Type.ILLEGAL_VALUE, info);
                return null;
            }
        }
    }

    /**
     * Print the program the tree represents in a more typical infix style, and with one statement per line.
     *
     * @see dendron.tree.ActionNode#infixDisplay()
     */
    public void displayProgram() {
        System.out.print("\nThe Program, with expressions in infix notation: ");
        tokens.infixDisplay();
    }

    /**
     * Run the program represented by the tree directly.
     *
     * @see dendron.tree.ActionNode#execute(Map)
     */
    public void interpret() {
        System.out.println("\n\nInterpreting the parse tree... ");
        tokens.execute(symTab);
        System.out.println("Interpretation complete.");
    }

    /**
     * Build the list of machine instructions for the program represented by the tree.
     *
     * @return the Machine.Instruction list
     * @see Machine.Instruction#execute()
     */
    public List<Machine.Instruction> compile() {
        return new ArrayList<>(tokens.emit());
    }

}
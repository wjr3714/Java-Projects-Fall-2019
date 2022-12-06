package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An ActionNode that represents the assignment of the value of an expression to a variable.
 *
 * @see ActionNode
 * @see DendronNode
 * @see ParseTree
 * @see Machine
 *
 * @author William J. Reid (wjr3714)
 */
public class Assignment implements ActionNode {

    /** The name of the variable being assigned a new value. */
    private String ident;

    /** Expression on the right hand side of the of the assignment statement. */
    private ExpressionNode rhs;

    /**
     * Create an assignment node.
     * @param ident  The variable that is getting assigned a new value
     * @param rhs Expression on the right hand side of the of the assignment statement.
     */
    public Assignment(String ident, ExpressionNode rhs){
        this.ident = ident;
        this.rhs = rhs;
    }

    /**
     * Evaluate the RHS expression. The result is then assigned to the variable.
     *
     * @param symTab The table where variable values are stored.
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        if (!ident.matches("^[a-zA-Z].*")){
            Errors.report(Errors.Type.UNINITIALIZED, null);
        }
        try{
            int assign = rhs.evaluate(symTab); // Get new value (RHS)
            symTab.put(ident, assign); // Assign new value to variable
        }
        catch (NullPointerException nPexception){
            String info = ident + " has not been initialized.";
            Errors.report(Errors.Type.UNINITIALIZED, info);
        }

    }

    /**
     * The infix form of the assignment is the follwoing; a variable followed by an assignment arrow (":=") followed by
     * the infix form of the RHS expression.
     */
    @Override
    public void infixDisplay() {
        System.out.print("\n" + ident + " := ");
        rhs.infixDisplay();
    }

    /**
     * Generate a list of instructions that, when executed, represents the intent of this DendronNode and its descendants.
     * This method returns a STORE instruction for the current variable. This is preceded by the RHS node that
     * eventually pushes the value of the expression onto the stack.
     *
     * @return The Machine Instructions for this node, which is the STORE instruction.
     */
    @Override
    public List<Machine.Instruction> emit() {

        // Store assignment
        Machine.Store storeInstruction = new Machine.Store(ident);

        // Instructions list
        ArrayList<Machine.Instruction> instructionsList = new ArrayList<>(rhs.emit());

        // Generate list of instructions
        instructionsList.add(storeInstruction);

        return instructionsList;
    }
}

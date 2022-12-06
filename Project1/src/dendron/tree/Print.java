package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An action node that represents the displaying of an expression.
 *
 * @see ActionNode
 * @see DendronNode
 * @see ParseTree
 * @see Machine
 *
 * @author William J. Reid (wjr3714)
 */
public class Print implements ActionNode  {

    /** The expression to be evaluated and printed. */
    private ExpressionNode printee;

    /**
     * Create a print node.
     * @param printee The expression to be evaluated and printed
     */
    public Print(ExpressionNode printee){
        this.printee = printee;
    }

    /**
     * Evaluate the expression and display the result on the console.
     *
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        try{
            int p = printee.evaluate(symTab);
            // Precede the result by three equal signs (per JavaDocs)
            System.out.println("=== " + p);
        }
        catch (NullPointerException nPexception){
            System.out.print("\nThe variable '");
            printee.infixDisplay();
            System.out.print("' was not initialized (and is not in the symbol table). \nError type: ");
            Errors.report(Errors.Type.UNINITIALIZED, null);
        }
    }

    /**
     * Show this statement on standard output as the word "Print" followed by the infix form of the expression.
     */
    @Override
    public void infixDisplay() {
        System.out.print("\nPrint ");
        printee.infixDisplay();
    }

    /**
     * This method returns the code emitted by the printee node. It does this by pushing the value of the printee
     * expression onto the stack, followed by a PRINT instruction.
     *
     * @return The list of instructions that compute the value to be printed, and the print itself.
     */
    @Override
    public List<Machine.Instruction> emit() {
        Machine.Print printInstruction = new Machine.Print();
        ArrayList<Machine.Instruction> instructionList = new ArrayList<>(printee.emit());
        instructionList.add(printInstruction);
        return instructionList;
    }
}

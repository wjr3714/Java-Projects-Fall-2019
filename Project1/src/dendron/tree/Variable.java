package dendron.tree;

import dendron.machine.Machine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The ExpressionNode for a variable.
 *
 * @see ExpressionNode
 * @see DendronNode
 * @see ParseTree
 * @see Machine
 *
 * @author William J. Reid (wjr3714)
 */
public class Variable implements ExpressionNode {

    /** The name of the variable. */
    public String name;

    /**
     * Set the name for the new variable.
     * @param name The name of the variable.
     */
    public Variable(String name){
        this.name = name;
    }


    /**
     * Evaluate a variable by retrieving its value.
     *
     * @param symTab The table containing all variables' values
     * @return The variable's current value in the symbol table
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return symTab.get(name);
    }

    /**
     * Print on standard output the Variable's name.
     */
    @Override
    public void infixDisplay() {
        System.out.print(this.name);
    }

    /**
     * Emit a LOAD instruction that pushes the Variable's value onto the stack.
     *
     * @return the Machine Instructions for loading this variable.
     */
    @Override
    public List<Machine.Instruction> emit() {

        Machine.Load variableInstruction = new Machine.Load(name);

        ArrayList<Machine.Instruction> instructionList = new ArrayList<>();

        instructionList.add(variableInstruction);

        return instructionList;
    }
}

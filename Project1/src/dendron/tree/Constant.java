package dendron.tree;

import dendron.machine.Machine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An expression node representing a constant (integer value).
 *
 * @see ExpressionNode
 * @see DendronNode
 * @see ParseTree
 * @see Machine
 *
 * @author William J. Reid (wjr3714)
 */
public class Constant implements ExpressionNode {

    /** Variable where the integer constant will be stored. */
    private int value;

    /**
     * Store the integer value.
     *
     * @param value The value to be stored as a constant.
     */
    public Constant(int value){
        this.value = value;
    }

    /**
     * Evaluate the constant.
     *
     * @param symTab Symbol table, if needed, to fetch variable values (not used in this case).
     * @return The constant's value.
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return value;
    }

    /**
     * Print this Constant's value on standard output.
     */
    @Override
    public void infixDisplay() {
        System.out.print(value);
    }

    /**
     * Emit an instruction to push the value onto the stack.
     *
     * @return A list containing the instruction.
     */
    @Override
    public List<Machine.Instruction> emit() {

        // ArrayList that will contain the instructions
        List<Machine.Instruction> instructionList = new ArrayList<>();

        // Push Constant
        Machine.PushConst constantInstruction = new Machine.PushConst(value);

        // Add instructions
        instructionList.add(constantInstruction);

        return instructionList;
    }
}

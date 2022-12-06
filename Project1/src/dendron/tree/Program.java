package dendron.tree;

import dendron.machine.Machine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An ActionNode used to represent a sequence of other ActionNodes. This node is the root of the entire program tree.
 *
 * @see ActionNode
 * @see DendronNode
 * @see ParseTree
 * @see Machine
 *
 * @author William J. Reid (wjr3714)
 */
public class Program implements ActionNode {

    /** The ActionNode children. */
    private ArrayList<ActionNode> rootNodes;

    /**
     * Constructs and empty sequence of ActionNode children.
     */
    public Program(){
        this.rootNodes = new ArrayList<>();
    }

    /**
     * Add a child of this Program node.
     * @param newNode The node representing the action that will execute last.
     */
    public void addAction(ActionNode newNode){
        rootNodes.add(newNode);
    }

    /**
     * Execute each ActionNode in this object, in a First In, First Out (FIFO) fashion.
     *
     * @param symTab The table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        // Execute each node in a First In, First Out (FIFO) fashion
        for (ActionNode actionnode : rootNodes){
            actionnode.execute(symTab);
        }
    }

    /**
     * Show the infix displays of all children on standard output, in a First In, First Out (FIFO) fashion.
     */
    @Override
    public void infixDisplay() {
        for (ActionNode actionnode : rootNodes){
            actionnode.infixDisplay();
        }
    }

    /**
     * Create a list of instructions emitted by each child, in a First In, First Out (FIFO) fashion.
     *
     * @return the concatenated Machine Instructions from all children.
     */
    @Override
    public List<Machine.Instruction> emit() {

        List<Machine.Instruction> instructionList = new ArrayList<>();

        // Add instructions from all children
        for (ActionNode actionnode : rootNodes){
            instructionList.addAll(actionnode.emit());
        }

        return instructionList;
    }

}

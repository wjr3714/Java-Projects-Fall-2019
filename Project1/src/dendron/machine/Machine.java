/*
 * file: Machine.java
 */

package dendron.machine;

import java.util.List;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import dendron.Errors;

/**
 * An abstraction of a computing machine that reads instructions
 * and executes them. It has an instruction set, a symbol table
 * for variables (instead of general-purpose memory), and a
 * value stack on which calculations are performed.
 *
 * (Everything is static to avoid the need to master the subtleties
 * of nested class instantiation or to pass the symbol table and
 * stack into every instruction when it executes.)
 *
 *
 * @author James Heliotis
 * @author William J. Reid (wjr3714)
 */
public class Machine {

    /** Do not instantiate this class. */
    private Machine() {}

    public static interface Instruction {
        /**
         * Run this instruction on the Machine, using the Machine's
         * value stack and symbol table.
         */
        void execute();

        /**
         * Show the instruction using text so it can be understood
         * by a person.
         * @return a short string describing what this instruction will do
         */
        @Override
        String toString();
    }

    private static Map< String, Integer > table = null;
    private static Stack< Integer > stack = null;

    /**
     * Reset the Machine to a pristine state.
     * @see Machine#execute
     */
    private static void reset() {
        stack = new Stack<>();
        table = new HashMap<>();
    }

    /**
     * Generate a listing of a program on standard output by
     * calling the toString() method on each instruction
     * contained therein, in order.
     *
     * @param program the list of instructions in the program
     */
    public static void displayInstructions(List< Machine.Instruction > program ) {

        System.out.println( "\nCompiled code:" );
        for ( Machine.Instruction instr: program ) {
            System.out.println( instr );
        }
        System.out.println();
    }

    /**
     * Run a "compiled" program by executing in order each instruction
     * contained therein.
     * Report on the final size of the stack (should normally be empty)
     * and the contents of the symbol table.
     * @param program a list of Machine instructions
     */
    public static void execute( List< Instruction > program ) {
        reset();
        System.out.println("Executing compiled code...");
        for ( Instruction instr: program ) {
            instr.execute();
        }
        System.out.println( "Machine: execution ended with " +
                stack.size() + " items left on the stack." );
        System.out.println();
        Errors.dump( table );
    }

    /**
     * The ADD instruction
     */
    public static class Add implements Instruction {
        /**
         * Run the microsteps for the ADD instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push( op1 + op2 );
        }

        /**
         * Show the ADD instruction as plain text.
         * @return "ADD"
         */
        @Override
        public String toString() {
            return "ADD";
        }
    }

    /**
     * The STORE instruction
     */
    public static class Store implements Instruction {
        /** stores name of target variable */
        private String name;

        /**
         * Create a STORE instruction
         * @param ident The name of the target variable
         */
        public Store( String ident ) {
            this.name = ident;
        }
        /**
         * Run the microsteps for the STORE instruction.
         */
        @Override
        public void execute() {
            table.put( this.name, stack.pop() );
        }
        /**
         * Show the STORE instruction as plain text.
         * @return "STORE" followed by the target variable name
         */
        @Override
        public String toString() {
            return "STORE " + this.name;
        }
    }


    /**
     * The SUBTRACT instruction
     */
    public static class Subtract implements Instruction{
        /**
         * Run the microsteps for the SUBTRACT instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 - op2);
        }

        /**
         * Show the NEGATE instruction as plain text.
         * @return "NEG"
         */
        @Override
        public String toString() {
            return "SUB";
        }
    }

    /**
     * The MULTIPLY instruction
     */
    public static class Multiply implements Instruction{
        /**
         * Run the microsteps for the MULTIPLY instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 * op2);
        }

        /**
         * Show the MULTIPLY instruction as plain text.
         * @return "MUL"
         */
        @Override
        public String toString() {
            return "MUL";
        }
    }

    /**
     * The DIVIDE instruction
     */
    public static class Divide implements Instruction{
        /**
         * Run the microsteps for the DIVIDE instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();

            try{
                stack.push(op1 / op2);
            }
            catch (ArithmeticException errorDivisionByZero){
                String info = "Cannot divide by zero \n" + op1 + " / " + op2 ;
                Errors.report(Errors.Type.DIVIDE_BY_ZERO, info);
            }
        }

        /**
         * Show the DIVIDE instruction as plain text.
         * @return "DIV"
         */
        @Override
        public String toString() {
            return "DIV";
        }
    }

    /**
     * The SQUAREROOT instruction
     */
    public static class SquareRoot implements Instruction{
        /**
         * Run the microsteps for the SQUAREROOT instruction.
         */
        @Override
        public void execute() {
            double op1 = stack.pop(); // Math.pow requires double as parameter
            stack.push( (int) Math.pow(op1,0.5) ); // Our declared stack can only hold integers (rationale for casting)
        }

        /**
         * Show the SQRT instruction as plain text.
         * @return "SQRT"
         */
        @Override
        public String toString() {
            return "SQRT";
        }
    }

    /**
     * The NEGATE instruction
     */
    public static class Negate implements Instruction{
        /**
         * Run the microsteps for the NEGATE instruction.
         */
        @Override
        public void execute() {
            int op1 = stack.pop() * (-1);
            stack.push(op1);
        }

        /**
         * Show the NEGATE instruction as plain text.
         * @return "NEG"
         */
        @Override
        public String toString() {
            return "NEG";
        }
    }

    /**
     * The LOAD instruction
     */
    public static class Load implements Instruction{

        /** The name of target variable. */
        private String varName;

        /**
         * Create a LOAD instruction
         * @param varName The name of the target variable.
         */
        public Load(String varName){
            // Error Check: Variable name must begin with alphabetic character or "_".
            if (varName.matches("^[a-zA-Z].*")){
                this.varName = varName;

            }
            else{
                String info = "Invalid name for variable.";
                Errors.report(Errors.Type.ILLEGAL_VALUE, info);
            }
        }

        /**
         * Run the microsteps for the LOAD instruction.
         */
        @Override
        public void execute() {
            // Error Check: Uninitialized variable.
            if (table.get(varName) == null){
                String info = varName + " has not been initialized.";
                Errors.report(Errors.Type.UNINITIALIZED, info);
            }
            stack.push(table.get(varName));
        }

        /**
         * Show the LOAD instruction as plain text.
         * @return "LOAD" followed by the target variable's name.
         */
        @Override
        public String toString() {
            return "LOAD " + this.varName;
        }
    }

    /**
     * The PUSH instruction.
     */
    public static class PushConst implements Instruction {
        /** The constant's value to be pushed. */
        private int constant;

        /**
         * Create a PUSH instruction.
         * @param constant The integer constant being pushed to the stack.
         */
        public PushConst(int constant) {
            this.constant = constant;
        }

        /**
         * Run the microsteps for the PUSH instruction.
         */
        @Override
        public void execute() {
            stack.push(constant);
        }

        /**
         * Print the PUSH instruction in plain text.
         * @return "PUSH" followed by the value pushed on to the stack.
         */
        @Override
        public String toString() {
            return "PUSH " + this.constant;
        }
    }

    /**
     * The PRINT instruction
     */
    public static class Print implements Instruction{
        /**
         * Create a PRINT instruction
         */
        public Print(){} // Empty constructor.

        /**
         * Run the microsteps for the PRINT instruction.
         */
        @Override
        public void execute() {
            System.out.println("*** " + stack.pop());
        }

        /**
         * Show the PRINT instruction as plain text.
         * @return "PRINT"
         */
        @Override
        public String toString() { // The sample output provided shows nothing after the "PRINT" keyword.
            return "PRINT";
        }
    }
}

/*
 * KalPTUI.java
 * PROVIDED file for the Kal GUI lab in csci142.
 */

package calendar.view_controller.text;

import calendar.model.Appointment;
import calendar.model.Calendar;
import edu.rit.cs.ConsoleApplication;
import edu.rit.cs.Observer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Created by jeh on 3/7/17.
 */
public class KalPTUI extends ConsoleApplication implements Observer {

    private Calendar model;

    private boolean busted = false;

    @Override
    public void init() {
        try {
            List< String > cmdLineArgs = this.getArguments();
            if ( cmdLineArgs.isEmpty() ) {
                this.model = new Calendar( 28 );
            }
            else {
                this.model = Calendar.fromFile( cmdLineArgs.get( 0 ) );
            }
//            this.model.addObserver( this );
        }
        catch( IOException e ) {
            System.err.println( e.getMessage() );
            this.busted = true;
        }
    }

    @Override
    public void go( Scanner consoleIn, PrintWriter consoleOut ) {
        if ( !this.busted ) {
            consoleOut.println( "Hi, this is Kal!" );
            Controller commandProcessor = new Controller( model, consoleIn,
                                                              consoleOut );
            commandProcessor.mainLoop();
        }
        consoleOut.println( "Calendar shutting down." );
    }

    @Override
    public void stop() {

    }

    public void update( Calendar o, Object arg ) {
        Calendar model = o; // (Calendar)o;
        int date = (Integer)arg;
        List< Appointment > appts = model.appointmentsOn( date );
        appts.forEach( System.out::println );
    }

    public static void main( String[] args ) {
        ConsoleApplication.launch( KalPTUI.class, args );
    }

    /**
     * The observed subject calls this method on each observer that has
     * previously registered with it. This version of the design pattern
     * follows the "push model" in that typically the observer must then
     * query the subject parameter to find out what has happened.
     * Therefore it is often the case that the observed subject calls
     * this method with an argument value of <code>this</code>, but
     * this convention is by no means a requirement. Similarly, if this
     * is a simple signal with no data attached, or if it can safely
     * be assumed that the observer already has a reference to the subject,
     * the subject parameter may be null.
     * But as always this would have to be agreed to by both sides.
     *
     * @param o object that informs this object about something.
     * @param something object that represents new information about subject
     * @see <a href="https://sourcemaking.com/design_patterns/observer">
     * the Observer design pattern</a>
     */
    @Override
    public void update(Object o, Object something) {
        Calendar cal = (Calendar) o;
        int date = (Integer) something;
        List< Appointment > appoints = cal.appointmentsOn(date);
        appoints.forEach( System.out::println );
    }
}

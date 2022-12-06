package calendar.model;

import edu.rit.cs.Observer;
import java.io.*;
import java.util.*;


/**
 * A single-month calendar
 * @see Observer
 * @author William J. Reid (wjr3714)
 */
public class Calendar implements Observer {

    /** Number of days in the month. */
    private int monthSize;

    /** Stores all the appointments of the month. */
    private List<List<Appointment>> appointments;

    /** Name of file where appointments will be saved/written to. */
    public static String file;

    /**
     * Create a month calendar of the given size.
     * @param monthSize The size of the calendar.
     */
    public Calendar(int monthSize){
        this.monthSize = monthSize;
        appointments = new ArrayList<>(monthSize);
        for (int i = 0; i < monthSize; i++){
            appointments.add(new ArrayList<>());
        }
    }

    /**
     * Add an appointment unless the appointment at the given date and time already exists.
     * @param date The appointment's date
     * @param time The appointment's time
     * @param what The appointment's description
     */
    public void	add(int date, Time time, String what){
        Appointment appoint = new Appointment(date, time, what);
        appointments.get(date).add(appoint);
    }

    /**
     * Add a new appointment.
     * @param appt The already-created Appointment object
     */
    public void	add(Appointment appt){
        int date = appt.getDate();
        this.appointments.get(date-1).add(appt); //Accounts for no day 0 but for index zero in array.
    }

////    The method [addObserver] seemed to be the cause of why my code hit a null point exception every time. Could not figure out why.
//    /**
//     *
//     * @param observer
//     */
//    public void addObserver(Observer<Calendar> observer){
//        observers.add(observer);
//    }

    /**
     * Check which appointments are in place for a certain date.
     * @param date the date to look up in the calendar
     * @return a list of Appointment objects for the given date, sorted by time.
     */
    public List<Appointment> appointmentsOn(int date){
        ArrayList appointmnt = new ArrayList(appointments.get(date - 1));
        Collections.sort(appointmnt);
        return appointmnt;
    }

    /**
     * Create a month calendar from a file. The first line is the number of days.
     * Each succeeding line contains three comma-separated values: date,time,description-string
     * @param fileName The name of the file containing appointments
     * @return A new calendar containing the appointments listed in the file
     * @throws IOException if the file cannot be opened or read, or if a formatting error occurs.
     */
    public static Calendar fromFile(String fileName) throws IOException{
        file = fileName;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line = reader.readLine();
            Calendar calendar = new Calendar(Integer.parseInt(line));
            line = reader.readLine();

            while (line != null) {
                String[] data = line.split(",");
                calendar.appointments.get(Integer.parseInt(data[0]) - 1).add(new Appointment(Integer.parseInt(data[0]), Time.fromString(data[1]), data[2]));
                line = reader.readLine();
            }
            return calendar;
        }
        catch (FileNotFoundException exceptioin){
            System.err.println("File not found.");
        }

        return null; // To make Java happy.

    }

    /**
     * Get the number of days in the month.
     * @return The number of days in the month.
     */
    public int numDays(){
        return this.monthSize;
    }

    /**
     * Remove an existing appointment.
     * @param appt The appointment to be removed.
     */
    public void	remove(Appointment appt){
        for (int i = 0; i < appointmentsOn(appt.getDate()).size(); i++) {
            if(((appointmentsOn(appt.getDate()).get(i).getTime().toString()).equals(appt.getTime().toString()))){
                appointmentsOn(appt.getDate()).remove(i);
            }
        }
    }

    /**
     * Save all appointments created in the file from which appointments were originally read.
     * @throws IOException if the file cannot be opened or read, or if a formatting error occurs
     */
    public void	toFile() throws IOException{
        if (file == null){
            throw new IOException("Calendar not loaded from file."); // Per JavaDocs.
        }
        try (PrintWriter calFile = new PrintWriter(file)){
            calFile.println(monthSize);
            for (int i = 1; i <= this.monthSize; i++){
                for (Appointment appointment : this.appointmentsOn(i)) {
                    calFile.println(appointment.csvFormat());
                }
            }
        }
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
        Calendar calendar = (Calendar) o;
        int date = (Integer) something;
        List< Appointment > appoints = calendar.appointmentsOn(date);
        appoints.forEach( System.out::println );
    }
}

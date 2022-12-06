package calendar.model;


/**
 * A single appointment in a single-month calendar.
 * @see Comparable<Appointment>
 * @author William J. Reid (wjr3714)
 */
public class Appointment implements Comparable<Appointment>{

    /** Date of the new appointment. */
    private int date;

    /** Time of the new appointment. */
    private Time time;

    /** Description of appointment. */
    private String text;


    /**
     * Create new appointment.
     * @param date Date of the new appointment.
     * @param time Time of the new appointment.
     * @param what Description of appointment.
     */
    public Appointment(int date, Time time, String what){
        this.date = date;
        this.time = time;
        this.text = what;
    }

    /**
     * Compare this appointment to another one.
     * @param other The appointment to compare with.
     * @return -1 is this appointment date is lower, 1 is this appointment date is higher, if the dates are the same,
     * then a negative number if this appointment's time is earlier; 0 if the two appointments' times and dates are equal.
     */
    public int compareTo(Appointment other){
        if (this.date < other.getDate() || this.getTime().compareTo(other.getTime()) < 0) {
            return -1;
        }
        else if (this.date == other.getDate() && this.getTime().compareTo(other.getTime()) == 0){
                return 0;
        }
        return 1;
    }

    /**
     * Create a string suitable for storing in a calendar file that can later be read and acted upon.
     * @return a String representation of the appointment suitable to be read later.
     */
    public String csvFormat(){
        return this.date + "," + this.time + "," + this.text;
    }

    /**
     * Check if the two appointments are equal.
     * @param other The appointment to compare with.
     * @return True if the the appointments are equal, false otherwise.
     */
    public boolean equals(Object other){
        if (other instanceof Appointment){
            Appointment appointment = (Appointment) other;
            return appointment.getDate() == (this.getDate()) && (appointment.getTime().toString().equals(this.getTime().toString()));
        }
        return false;
    }

    /**
     * Create an appointment from a text description.
     * @param inputLine The text description of the appointment
     * @return A new appointment with the description passed.
     */
    public static Appointment fromString(String inputLine){
        String[] in = inputLine.split(",");
        return new Appointment(Integer.parseInt(String.valueOf(in[0])), Time.fromString(in[1]), in[2]);
    }

    /**
     * Get the date of the appointment.
     * @return The date of the appointment.
     */
    public int getDate(){
        return this.date;
    }

    /**
     * Get the description of the appointment.
     * @return The description of the appointment.
     */
    public String getText(){
        return this.text;
    }

    /**
     * Get the time of the appointment.
     * @return The time of the appointment.
     */
    public Time getTime(){
        return this.time;
    }

    /**
     * Return the hash code of the appointment.
     * @return Hashcode of appointment.
     */
    public int hashCode(){
        return (int) (this.date + Math.pow(this.date, this.text.length())) + this.text.length()+12;
    }

    /**
     * Create the String representation of the appointment.
     * @return The String representation of the appointment.
     */
    public String toString(){
        return "on " + this.date + " at " + this.time + " -- " + this.text;
    }

}

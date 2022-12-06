package calendar.view_controller.fx;

import calendar.model.Appointment;
import calendar.model.Calendar;
import edu.rit.cs.Observer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Graphical User Interface representing a calendar.
 * @author William J. Reid (wjr3714)
 */
public class KalGUI extends Application implements Observer {

    /** The appointment created or removed. */
    private Appointment appointment;

    /** The 1-month calendar */
    private Calendar calendar;

    /** List containing appointments. */
    private List<Appointment> listOfAppointments;


    /**
     * Creates the stage for the calendar.
     * @param stage The stage of the calendar.
     */
    @Override
    public void start(Stage stage) {

        BorderPane borderPane = new BorderPane();

        // Create Pane
        FlowPane fPane = new FlowPane();
        fPane.setPrefWidth(450);
        fPane.setPrefHeight(300);

        //Create Calendar Days
        for (int i = 1; i <= calendar.numDays(); i++) {

            Button button = new Button("" + i);

            // Formatting...
            button.setPrefHeight(75);
            button.setPrefWidth(75);
            button.setTextAlignment(TextAlignment.RIGHT);

            // Set text for button.
            button.setId(Integer.toString(i));
            fPane.getChildren().add(button);

            // Set button functions.
            final int ii = i;
            button.setOnAction(event -> new Day(Integer.toString(ii)));
        }

        // Title
        borderPane.setCenter(fPane);
        HBox b = new HBox(new Label("Kalendar"));
        b.setAlignment(Pos.TOP_CENTER);

        // Formatting...
        borderPane.setTop(b);
        b.setSpacing(75);
        borderPane.setRight(new VBox());

        // Save Button
        Button save = new Button("Save");
        HBox saveBox = new HBox(save);
        saveBox.setFillHeight(true);
        saveBox.setAlignment(Pos.BOTTOM_CENTER);
        borderPane.setBottom(saveBox);

        // When the SAVE button is clicked... Save contents to file if the calendar was opened from a file.
        save.setOnAction(saveToFile -> {
            try {
                calendar.toFile();
            } catch (IOException ioe) {
                System.err.print(ioe);
            }
        });

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Kalendar");

        stage.show();
    }


    /**
     * Initializes the calendar.
     */
    @Override
    public void init() {
        try {
            Application.Parameters p = getParameters();
            if(p.getRaw().size() == 0){
                this.calendar = new Calendar(28);
            }
            else {
                this.calendar = Calendar.fromFile(p.getRaw().get(0));
            }
        }
        catch( IOException e ) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * The observed subject calls this method on each observer that has previously registered with it. This version of
     * the design pattern follows the "push model" in that typically the observer must then query the subject parameter
     * to find out what has happened. Therefore it is often the case that the observed subject calls this method with
     * an argument value of <code>this</code>, but this convention is by no means a requirement. Similarly, if this is
     * a simple signal with no data attached, or if it can safely be assumed that the observer already has a reference
     * to the subject, the subject parameter may be null. But as always this would have to be agreed to by both sides.
     *
     * @param o object that informs this object about something.
     * @param something object that represents new information about subject
     * @see <a href="https://sourcemaking.com/design_patterns/observer">
     * the Observer design pattern</a>
     */
    @Override
    public void update(Object o, Object something) {
        listOfAppointments = this.calendar.appointmentsOn((Integer) something);
    }

    /**
     * This class provides the ability to VIEW the appointments of a particular day, ADD additional appointments, and
     * REMOVE existing appointments.
     */
    class Day extends Stage {

        /**
         * Provides the ability to VIEW the appointments of a particular day, ADD additional appointments, and
         * REMOVE existing appointments.
         * @param calendarDay The calendar day for which appointments are to be viewed or edited.
         */
        Day(String calendarDay) {

            BorderPane borderPane = new BorderPane();

            ToggleGroup toggleGroup = new ToggleGroup();

            VBox box1 = new VBox();
            borderPane.setCenter(box1);

            HBox box = new HBox();
            borderPane.setTop(box);

            // Add Appointment Button
            Button add = new Button("Add");
            add.setPrefWidth(193);

            // Add Appointment Button
            Button remove = new Button("Remove");
            remove.setPrefWidth(193);

            // Close Dialog Button
            Button close = new Button("Close");
            close.setPrefWidth(193);
            box.getChildren().addAll(add, remove, close);

            // // When the CLOSE button is clicked... Close the pop-up window.
            close.setOnAction(closeEvent -> this.close());

            // List of appointments.
            listOfAppointments = calendar.appointmentsOn(Integer.parseInt(calendarDay));
            if(listOfAppointments.size()!= 0){
                for (Appointment appointment : listOfAppointments) {
                    RadioButton radioButton = new RadioButton(appointment.toString());

                    radioButton.setUserData(appointment);
                    radioButton.setToggleGroup(toggleGroup);

                    box1.getChildren().addAll(radioButton);
                }
            }

            // When the ADD button is clicked...
            add.setOnMouseClicked(event -> {
                TextInputDialog newAppointment = new TextInputDialog("Example: 10:00 AM, Soccer practice");

                newAppointment.setTitle("Add Appointment");
                newAppointment.setContentText("Enter appointment:");

                Optional<String> input = newAppointment.showAndWait();

                if (input.isPresent()){
                    appointment = Appointment.fromString(calendarDay + "," + input.get());
                    calendar.add(appointment);

                    RadioButton radioButton = new RadioButton(appointment.toString());
                    radioButton.setUserData(appointment);
                    radioButton.setToggleGroup(toggleGroup);

                    box1.getChildren().addAll(radioButton);
                }
            });

            // When the REMOVE button is clicked...
            remove.setOnMouseClicked(event -> {
                if((toggleGroup.getSelectedToggle() != null)){
                    appointment = (Appointment) toggleGroup.getSelectedToggle().getUserData();
                    calendar.remove(appointment);
                    box1.getChildren().remove(toggleGroup.getSelectedToggle());
                }
            });

            this.setTitle("Appointments");
            this.setWidth(575);
            this.setHeight(425);
            this.setScene(new Scene(borderPane));
            this.show();
        }
    }
}

//  Description: SelectPane displays a list of available courses
//  from which a user can select and compute total number of students in multiple courses.

import java.util.ArrayList;

import javafx.event.ActionEvent; //**Need to import
import javafx.event.EventHandler; //**Need to import
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * SelectPane displays a list of available courses from which a user can select
 * and compute total number of students for selected courses.
 */
public class SelectPane extends BorderPane {

	// declare instance varibales
	private ArrayList<Course> courseList;
	private ScrollPane scrol;
	private VBox container;
	private Label lbl_total;
	private int total = 0;

	public SelectPane(ArrayList<Course> list) {
		/* ------------------------------ */
		/* Instantiate instance variables */
		/* ------------------------------ */

		this.courseList = list;
		this.courseList = list;
		lbl_total = new Label("The total number of students for the selected course(s) 0");

		// Wrap checkboxContainer in ScrollPane so formatting is
		// correct when many courses are added
		container = new VBox();
		scrol = new ScrollPane(container);

		// Setup layout
		// create an empty pane where you can add check boxes later
		// SelectPane is a BorderPane - add the components here
		setTop(new Label("Select course(s)"));
		setCenter(scrol);
		setBottom(lbl_total);

	} // end of SelectPane constructor

	// This method uses the newly added parameter Course object
	// to create a CheckBox and add it to a pane created in the constructor
	// Such check box needs to be linked to its handler class
	public void updateCourseList(Course newcourse) {
		// Create checkbox for new course with appropriate text
		CheckBox check = new CheckBox(newcourse.toString());
		// Bind checkbox toggle action to event handler
		// Passes the number of students in each course to the handler. When the
		// checkbox is
		// toggled, this number will be added/subtracted from the total number of
		// selected students
		check.setOnAction(new SelectionHandler(newcourse.getNumStudents()));

		// Add new checkbox to checkbox container
		container.getChildren().add(check);
	} // end of updateCourseList method

	/**
	 * SelectionHandler This class handles a checkbox toggle event. When the
	 * checkbox is toggled, this number will be added/subtracted from the total
	 * number of selected students.
	 */
	private class SelectionHandler implements EventHandler<ActionEvent> {

		// Instance variable for number of students associated with a given
		// course/checkbox
		private int numStudents;

		public SelectionHandler(int nums) {
			this.numStudents = nums; // Set instance variable
		} // end of SelectionHandler constructor

		// over-ride the abstract method handle
		public void handle(ActionEvent event) {
			// Get the object that triggered the event, and cast it to a checkbox, since
			// only a course checkbox
			// can trigger the SelectionHandler event. The cast is necessary to have access
			// to the isSelected() method

			// Update the label with the new number of selected students
			if (((CheckBox) event.getSource()).isSelected()) {
				total += numStudents;
			} else {
				total -= numStudents;
			}
			lbl_total.setText("The total number of students for the selected course(s) " + total);
		} // end handle method
	} // end of SelectHandler class
} // end of SelectPane class

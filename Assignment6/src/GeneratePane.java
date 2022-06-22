//  Description: GeneratePane creats a pane where a user can enter
//  course information and create a list of available courses.

/* --------------- */
/* Import Packages */
/* --------------- */
import java.util.ArrayList;

import javafx.event.ActionEvent; //**Need to import
import javafx.event.EventHandler; //**Need to import

// JavaFX classes
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * GeneratePane builds a pane where a user can enter a course information and
 * create a list of available courses.
 */
public class GeneratePane extends HBox {

	/* ------------------ */
	/* Instance variables */
	/* ------------------ */
	ArrayList<Course> courseList;
	private SelectPane selectPane; // The relationship between GeneratePane and SelectPane is Aggregation
	// declare and init
	private Label lbl_msg, lbl_crsName, lbl_inst, lbl_univ, lbl_numSt;
	private TextField txt_crsName, txt_inst, txt_univ, txt_numSt;
	private Button btn_create;
	private TextArea ta;

	/**
	 * CreatePane constructor
	 *
	 * @param list   the list of courses
	 * @param sePane the SelectPane instance
	 */
	public GeneratePane(ArrayList<Course> list, SelectPane sePane) {
		/* ------------------------------ */
		/* Instantiate instance variables */
		/* ------------------------------ */

		// initialize each instance variable (textfields, labels, textarea, button,
		// etc.)
		// and set up the layout
		courseList = list;
		selectPane = sePane;
		lbl_msg = new Label();
		lbl_msg.setTextFill(Color.RED);
		lbl_crsName = new Label("Course Name");
		lbl_inst = new Label("Name of instructor");
		lbl_univ = new Label("Name of university");
		lbl_numSt = new Label("Number of students");
		txt_crsName = new TextField();
		txt_inst = new TextField();
		txt_univ = new TextField();
		txt_numSt = new TextField();
		btn_create = new Button("Add a Course");
		ta = new TextArea("No Course");
		// create a GridPane to hold labels & text fields.
		// you can choose to use .setPadding() or setHgap(), setVgap()
		// to control the spacing and gap, etc.
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(80, 10, 10, 10));
		gridPane.setHgap(5);
		gridPane.setVgap(5);
		gridPane.add(lbl_crsName, 0, 0);
		gridPane.add(lbl_inst, 0, 1);
		gridPane.add(lbl_univ, 0, 2);
		gridPane.add(lbl_numSt, 0, 3);
		gridPane.add(txt_crsName, 1, 0);
		gridPane.add(txt_inst, 1, 1);
		gridPane.add(txt_univ, 1, 2);
		gridPane.add(txt_numSt, 1, 3);
		// Set both left and right columns to 50% width (half of window)
		ColumnConstraints halfWidth = new ColumnConstraints();
		halfWidth.setPercentWidth(50);
		gridPane.getColumnConstraints().addAll(halfWidth, halfWidth);
		// You might need to create a sub pane to hold the button

		// Set up the layout for the left half of the GeneratePane.
		VBox leftPane = new VBox();
		leftPane.setPadding(new Insets(10, 0, 50, 0));
		leftPane.getChildren().addAll(lbl_msg, gridPane, btn_create);
		// the right half of the GeneratePane is simply a TextArea object
		// Note: a ScrollPane will be added to it automatically when there are no more
		// space
		// Add the left half and right half to the GeneratePane
		getChildren().addAll(leftPane, ta);

		// Note: GeneratePane extends from HBox
		// register/link source object with event handler
		// Bind button click action to event handler
		btn_create.setOnAction(new ButtonHandler());

	} // end of constructor

	/**
	 * ButtonHandler ButtonHandler listens to see if the button "Add a course" is
	 * pushed or not, When the event occurs, it asks for course and instructor name,
	 * number of students enrolled, and its university information from the relevant
	 * text fields, then create a new course and adds it to the courseList.
	 * Meanwhile it will display the course's information inside the text area.
	 * using the toString method of the Course class. It also does error checking in
	 * case any of the text fields are empty, or a non-numeric value was entered for
	 * number of student
	 */
	private class ButtonHandler implements EventHandler<ActionEvent> {

		/**
		 * handle Override the abstract method handle()
		 */
		public void handle(ActionEvent event) {
			// Declare local variables
			Course newCourse;
			int numberOfStudents = 0;
			// If any field is empty, set isEmptyFields flag to true
			if (txt_crsName.getText().isEmpty() || txt_numSt.getText().isEmpty() || txt_univ.getText().isEmpty()
					|| txt_inst.getText().isEmpty()) {
				// Display error message if there are empty fields
				lbl_msg.setText("Please fill all fields");
			} else // for all other cases
			{
				// If all fields are filled, try to add the course
				try {
					/*
					 * Cast students Field to an integer, throws NumberFormatException if
					 * unsuccessful
					 */
					// Data is valid, so create new Course object and populate data
					// Loop through existing courses to check for duplicates
					// and if exist do not add it to the list and display a message
					newCourse = new Course(txt_crsName.getText(), new Instructor("", txt_inst.getText(), "", 0),
							txt_univ.getText(), Integer.parseInt(txt_numSt.getText()));
					if (courseList.isEmpty()) {
						ta.setText("");
					}
					for (Course crs : courseList) { // if course exist throw exception
						if (crs.getName().equals(txt_crsName.getText())) {
							throw new Exception();
						}
					}

					// else course is not a duplicate, so display it and add it to list
					courseList.add(newCourse);
					selectPane.updateCourseList(newCourse);
					ta.appendText(newCourse.toString());
					// reset
					txt_crsName.setText("");
					txt_inst.setText("");
					txt_univ.setText("");
					txt_numSt.setText("");
					lbl_msg.setText("Course added");
				} // end of try
				catch (NumberFormatException e) {
					// If the number of students entered was not an integer, display an error
					lbl_msg.setText("Please enter an integer for the number of student(s)");
				} catch (Exception e) {
					// Catches generic exception and displays message
					// Used to display 'Course is not added - already exist' message if course
					// already exist
					lbl_msg.setText("Course not added - already exist");
				}

			}
		} // end of handle() method
	} // end of ButtonHandler class
} // end of GeneratePane class

package seedu.address.ui.job;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.job.Job;
import seedu.address.model.job.Priority;
import seedu.address.model.job.Status;
import seedu.address.ui.UiPart;
import seedu.address.ui.machine.MachineCard;


/**
 * An UI component that displays information of a {@code Job}.
 */
public class JobCard extends UiPart<Region> {

    private static final String FXML = "JobListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Job job;

    @FXML
    private HBox cardPane;
    @FXML
    private Label jobName;
    @FXML
    private FlowPane jobInformation;
    @FXML
    private FlowPane jobStartTime;
    @FXML
    private FlowPane details;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;
    @FXML
    private Label jobDescription;
    @FXML
    private Label jobDuration;

    public JobCard(Job job, int displayIndex) {
        super(FXML);
        this.job = job;
        jobName.setText(job.getJobName().fullName);

        Label informationLabel =
            new Label("Added by " + job.getOwner().getName().fullName + " at " + job.getAddedTime().showTime());
        Label startTimeLabel;
        if (job.getStatus() == Status.ONGOING) {
            startTimeLabel = new Label("Started at: " + job.getStartTime().showTime());
        } else {
            startTimeLabel = new Label("The starting time is not applicable.");
        }
        Label priorityLabel = new Label("Priority: " + job.getPriority().toString());
        Label statusLabel = new Label("Status: " + job.getStatus().toString());

        details.setHgap(2);

        jobInformation.getChildren().add(informationLabel);
        jobInformation.setHgap(2);

        startTimeLabel.setStyle(
            "-fx-font: 12 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #006064;" + "-fx-padding: 2;"
                + "-fx-text-alignment: center");
        jobStartTime.getChildren().add(startTimeLabel);
        jobStartTime.setHgap(2);

        if (job.getPriority() == Priority.URGENT) {
            priorityLabel.setStyle(
                "-fx-font: 14 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #b71c1c;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
        }

        if (job.getPriority() == Priority.HIGH) {
            priorityLabel.setStyle(
                "-fx-font: 14 arial;" + "-fx-text-fill: #000000;" + "-fx-background-color: #ffca28;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
        }
        if (job.getPriority() == Priority.NORMAL) {
            priorityLabel.setStyle(
                "-fx-font: 14 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #00897b;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
        }
        tags.getChildren().add(priorityLabel);

        statusLabel.setStyle(
            "-fx-font: 12 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #a1887f;" + "-fx-padding: 2;"
                + "-fx-text-alignment: center");
        tags.getChildren().add(statusLabel);

        job.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        jobDescription.setText(job.getJobNote().toString());
        jobDuration.setText("Time Remaining: " + job.getReadableDurationString());


        /* No longer need request deletion it goes under status now.
        ** Leaving the code here for future reference will delete this
        * * by v1.4
        //TODO dont know why the color is not changing...
        if (job.requestDeletion()) {
            Label requestDeletionLabel = new Label("Requested Deletion");
            requestDeletionLabel.setBackground(new Background(
                    new BackgroundFill(
                            Paint.valueOf("#dd0404"),
                            new CornerRadii(2),
                            new Insets(0))));
            requestDeletionLabel.setTextFill(Paint.valueOf("#F00"));
            tags.getChildren().add(requestDeletionLabel);
        }
        */
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MachineCard)) {
            return false;
        }

        // state check
        JobCard jobCard = (JobCard) other;
        return job.equals(jobCard.job);
    }
}

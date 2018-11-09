package seedu.address.ui.job;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.job.Job;
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
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressIndicator progressIndicator;

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

        setPriorityColor(priorityLabel);
        setStatusColor(statusLabel);

        tags.getChildren().add(priorityLabel);
        tags.getChildren().add(statusLabel);

        job.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        jobDescription.setText(job.getJobNote().toString());
        jobDuration.setText("Duration: " + job.getReadableDurationString());


        setProgress(job.getPercentageCompletion());


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

    private void setPriorityColor(Label label) {
        switch (job.getPriority()) {
        case URGENT:
            label.setStyle(
                "-fx-font: 14 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #b71c1c;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
            break;
        case HIGH:
            label.setStyle(
                "-fx-font: 14 arial;" + "-fx-text-fill: #000000;" + "-fx-background-color: #ffca28;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
            break;
        case NORMAL:
        default:
            label.setStyle(
                "-fx-font: 14 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #00897b;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");

        }
    }


    private void setStatusColor(Label label) {
        switch (job.getStatus()) {
        case ONGOING:
            label.setStyle("-fx-font: 12 arial;" + "-fx-text-fill: rgb(255,255,255);" + "-fx-background-color: #43b581;"
                + "-fx-padding: 2;" + "-fx-text-alignment: center");
            break;
        case QUEUED:
            label.setStyle(
                "-fx-font: 12 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #bc7000;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
            break;
        case DELETING:
            label.setStyle(
                "-fx-font: 12 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #a12b2f;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
            break;
        case FINISHED:
            label.setStyle(
                "-fx-font: 12 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #7289da;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
            break;
        case CANCELLED:
        default:
            label.setStyle(
                "-fx-font: 12 arial;" + "-fx-text-fill: #ffffff;" + "-fx-background-color: #839090;" + "-fx-padding: 2;"
                    + "-fx-text-alignment: center");
        }
    }

    private void setProgress(float progress) {
        switch (job.getStatus()) {
        case ONGOING:
            progressBar.setProgress(job.getPercentageCompletion());
            progressIndicator.setProgress(job.getPercentageCompletion());
            break;
        case FINISHED:
            progressBar.setProgress(1);
            progressIndicator.setProgress(1);
            break;
        default:
            progressBar.setProgress(0);
            progressIndicator.setProgress(0);
        }

    }
}

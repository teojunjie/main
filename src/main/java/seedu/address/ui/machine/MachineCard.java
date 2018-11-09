package seedu.address.ui.machine;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.job.TimeStamp;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineStatus;
import seedu.address.ui.UiPart;
import seedu.address.ui.job.JobListPanel;


/**
 * A UI component that displays information of a {@code Machine}
 */
public class MachineCard extends UiPart<Region> {

    private static final String FXML = "MachineListCard.fxml";
    private static final Logger logger = LogsCenter.getLogger(MachineCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Machine machine;

    private JobListPanel jobListPanel;

    @FXML
    private HBox cardPane;
    @FXML
    private Label machineName;
    @FXML
    private Label id;
    @FXML
    private FlowPane machineStatus;
    @FXML
    private FlowPane totalDuration;
    @FXML
    private StackPane jobsListPlaceHolder;

    public MachineCard(Machine machine, int displayIndex) {
        super(FXML);
        this.machine = machine;
        id.setText(displayIndex + ". ");
        machineName.setText(machine.getName().fullName);
        Label machineStatusLabel = new Label(machine.getStatus().toString());
        Label durationLabel = new Label("Time until released: " + TimeStamp.showAsDuration(machine.getTotalDuration()));
        machineStatusLabel.setStyle(
            "-fx-font: 12 arial;" + "-fx-text-fill: black;" + "-fx-padding: 2;" + "-fx-text-alignment: center");
        machineStatusLabel.setBackground(new Background(
            new BackgroundFill(Paint.valueOf(machine.getStatus().equals(MachineStatus.ENABLED) ? "#0ec10e" : "#dd0404"),
                new CornerRadii(2), new javafx.geometry.Insets(0))));

        Label machineAvailabilityLabel =
            new Label(machine.getStatus().equals(MachineStatus.ENABLED) ? "Available" : "Unavailable");
        machineAvailabilityLabel.setStyle(
            "-fx-font: 12 arial;" + "-fx-text-fill: black;" + "-fx-padding: 2;" + "-fx-text-alignment: center");

        machineAvailabilityLabel.setBackground(new Background(
            new BackgroundFill(Paint.valueOf(machine.getStatus().equals(MachineStatus.ENABLED) ? "#0ec10e" : "#dd0404"),
                new CornerRadii(2), new javafx.geometry.Insets(0))));

        machineStatus.getChildren().add(machineStatusLabel);
        machineStatus.getChildren().add(machineAvailabilityLabel);
        machineStatus.setHgap(4);
        totalDuration.getChildren().add(durationLabel);

        jobListPanel = new JobListPanel(machine.getJobsAsFilteredObservableList());
        jobsListPlaceHolder.getChildren().add(jobListPanel.getRoot());
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
        MachineCard machineCard = (MachineCard) other;
        return id.getText().equals(machineCard.id.getText()) && machine.equals(machineCard.machine);
    }
}

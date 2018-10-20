package seedu.address.storage.job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobNote;
import seedu.address.model.job.Priority;
import seedu.address.model.job.Status;

import seedu.address.model.machine.Machine;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedPerson;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.machine.XmlAdaptedMachine;

/**
 * JAXB-friendly version of the Job.
 */
public class XmlAdaptedJob {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job's %s field is missing!";

    private static final Logger logger = LogsCenter.getLogger(XmlAdaptedJob.class);

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private XmlAdaptedMachine machine;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private XmlAdaptedPerson owner;
    @XmlElement(required = true)
    private Priority priority;
    @XmlElement(required = true)
    private float duration;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private String note;

    @XmlElement
    private Status status;


    /**
     * Constructs an XmlAdaptedJob.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJob() {
    }

    /**
     * Constructs an {@code XmlAdaptedJob} with the given job details.
     */

    public XmlAdaptedJob(String name, XmlAdaptedMachine machine, String time, XmlAdaptedPerson owner,
                         Priority priority, float duration, String note, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.machine = machine;
        this.startTime = time;
        this.owner = owner;
        this.priority = priority;
        this.duration = duration;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.note = note;
    }

    /**
     * Converts a given Job into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedJob
     */
    public XmlAdaptedJob(Job source) {
        name = source.getJobName().fullName;
        machine = new XmlAdaptedMachine(source.getMachine());
        owner = new XmlAdaptedPerson(source.getOwner());

        startTime = source.getStartTime().toString();

        priority = source.getPriority();
        status = source.getStatus();
        duration = source.getDuration();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        note = source.getJobNote().toString();
    }

    /**
     * Converts this jaxb-friendly adapted job object into the model's Job object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job
     */
    public Job toModelType() throws IllegalValueException {
        //TODO handle exceptions properly here

        JobName modelJobName = new JobName(name);
        Machine modelJobMachine = machine.toModelType();
        Person modelJobOwner = owner.toModelType();
        final Priority modelPriority = priority;
        //TODO: no validation on duration yet
        final float modelDuration = duration;
        JobNote modelJobNote = new JobNote(note);
        Set<Tag> modelTags = new HashSet<>();
        for (XmlAdaptedTag tag : tagged) {

            modelTags.add(tag.toModelType());
        }

        return new Job(modelJobName,
                modelJobMachine,
                modelJobOwner,
                modelPriority,
                modelDuration,
                modelJobNote,
                modelTags);

    }
}



package seedu.address.storage.admin;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.Username;

/**
 * JAXB-friendly version of the Admin.
 */
public class XmlAdaptedAdmin {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Admin's %s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedAdmin.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAdmin() {}

    /**
     * Constructs an {@code XmlAdaptedAdmin} with the given admin details.
     */
    public XmlAdaptedAdmin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given Admin into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAdmin
     */
    public XmlAdaptedAdmin(Admin source) {
        username = source.getUsername().toString();
        password = source.getPassword().toString();
    }

    /**
     * Converts this jaxb-friendly adapted admin object into the model's Admin object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted admin
     */
    public Admin toModelType() throws IllegalValueException {

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }

        final Username modelUsername = new Username(username);

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }

        final Password modelPassword = new Password(password);

        return new Admin(modelUsername, modelPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAdmin)) {
            return false;
        }

        XmlAdaptedAdmin otherAdmin = (XmlAdaptedAdmin) other;
        return Objects.equals(username, otherAdmin.username)
                && Objects.equals(password, otherAdmin.password);
    }
}

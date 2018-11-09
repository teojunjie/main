package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;

import org.mindrot.jbcrypt.BCrypt;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.Username;


/**
 * Lets the admin login to MakerManager
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_SUCCESS = "login success!";
    public static final String MESSAGE_WRONG_DETAILS = "Login failed! Wrong Username/Password.";
    public static final String MESSAGE_ALREADY_LOGGED_IN = "Login failed! "
            + "Please Logout of current account before logging in again.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Login used for admin access.\n"
            + "Example: login USERNAME PASSWORD\n";

    private final Username username;
    private final Password password;

    public LoginCommand(Username username, Password password) {
        requireNonNull(username);
        requireNonNull(password);
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isLoggedIn()) {
            throw new CommandException(MESSAGE_ALREADY_LOGGED_IN);
        }

        Admin currentAdmin = model.findAdmin(username);

        if (currentAdmin == null) {
            throw new CommandException(MESSAGE_WRONG_DETAILS);
        }

        if (!BCrypt.checkpw(password.toString(), currentAdmin.getPassword().toString())) {
            throw new CommandException(MESSAGE_WRONG_DETAILS);
        }

        model.setLogin(currentAdmin);
        model.adminLoginCommitAddressBook();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && username.equals(((LoginCommand) other).username)
                && password.equals(((LoginCommand) other).password)); // state check
    }
}

package seedu.address.logic.commands.PaymentCommandTest;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaymentCommand.PaymentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tutee.Tutee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalTrackO;

import static seedu.address.logic.commands.PaymentCommand.PaymentCommand.MESSAGE_VIEW_TUTEE_PAYMENT_SUCCESS;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 *  * {@code PaymentCommand}.
 */
public class PaymentCommandTest {

    private Model model = new ModelManager(getTypicalTrackO(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Tutee retrievedTutee = model.getFilteredTuteeList().get(INDEX_FIRST_TUTEE.getZeroBased());
        PaymentCommand paymentCommand = new PaymentCommand(INDEX_FIRST_TUTEE);

        String expectedMessage = String.format(MESSAGE_VIEW_TUTEE_PAYMENT_SUCCESS, retrievedTutee.getName()
                , retrievedTutee.getPayment());

        ModelManager expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());

        assertCommandSuccess(paymentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTuteeList().size() + 1);
        PaymentCommand paymentCommand = new PaymentCommand(outOfBoundIndex);

        assertCommandFailure(paymentCommand, model, Messages.MESSAGE_INVALID_TUTEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        PaymentCommand getFirstCommand = new PaymentCommand(INDEX_FIRST_TUTEE);
        PaymentCommand getSecondCommand = new PaymentCommand(INDEX_SECOND_TUTEE);

        // same object -> returns true
        assertTrue(getFirstCommand.equals(getFirstCommand));

        // same values -> returns true
        PaymentCommand getFirstCommandCopy = new PaymentCommand(INDEX_FIRST_TUTEE);
        assertTrue(getFirstCommand.equals(getFirstCommandCopy));

        // different types -> returns false
        assertFalse(getFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getFirstCommand.equals(null));

        // different tutee -> returns false
        assertFalse(getFirstCommand.equals(getSecondCommand));
    }
}

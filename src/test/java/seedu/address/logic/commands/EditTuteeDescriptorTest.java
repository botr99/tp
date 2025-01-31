package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PRACTICAL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditTuteeDescriptor;
import seedu.address.testutil.EditTuteeDescriptorBuilder;

public class EditTuteeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTuteeDescriptor descriptorWithSameValues = new EditTuteeDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditTuteeDescriptor editedAmy = new EditTuteeDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditTuteeDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different school -> returns false
        editedAmy = new EditTuteeDescriptorBuilder(DESC_AMY).withSchool(VALID_SCHOOL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different level -> returns false
        editedAmy = new EditTuteeDescriptorBuilder(DESC_AMY).withLevel(VALID_LEVEL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditTuteeDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditTuteeDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_PRACTICAL).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}

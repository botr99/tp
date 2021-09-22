package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Some remark.");

        // same object
        assertEquals(remark, remark);

        // same values
        Remark remarkCopy = new Remark(remark.value);
        assertEquals(remark, remarkCopy);

        // different types
        assertNotEquals(remark, 1);

        // null
        assertNotEquals(remark, null);

        // different remark
        Remark differentRemark = new Remark("Another remark.");
        assertNotEquals(remark, differentRemark);
    }
}

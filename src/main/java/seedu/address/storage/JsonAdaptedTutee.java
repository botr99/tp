package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Fee;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.Tutee;


/**
 * Jackson-friendly version of {@link Tutee}.
 */
class JsonAdaptedTutee {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutee's %s field is missing!";

    private final String name;
    private final String phone;
    private final String level;
    private final String address;
    private final String fee;
    private final String lastPaymentDateAsString;
    private final String remark;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTutee} with the given tutee details.
     */
    @JsonCreator
    public JsonAdaptedTutee(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                            @JsonProperty("level") String level, @JsonProperty("address") String address,
                            @JsonProperty("fee") String fee,
                            @JsonProperty("lastPaymentDate") String lastPaymentDateAsString,
                            @JsonProperty("remark") String remark,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {

        this.name = name;
        this.phone = phone;
        this.level = level;
        this.address = address;
        this.fee = fee;
        this.lastPaymentDateAsString = lastPaymentDateAsString;
        this.remark = remark;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Tutee} into this class for Jackson use.
     */
    public JsonAdaptedTutee(Tutee source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        level = source.getLevel().value;
        address = source.getAddress().value;
        fee = source.getFee().value;
        lastPaymentDateAsString = source.getFee().lastPaymentDateAsString;
        remark = source.getRemark().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted tutee object into the model's {@code Tutee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutee.
     */
    public Tutee toModelType() throws IllegalValueException {
        final List<Tag> tuteeTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            tuteeTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (level == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName()));
        }
        if (!Level.isValidLevel(level)) {
            throw new IllegalValueException(Level.MESSAGE_CONSTRAINTS);
        }
        final Level modelLevel = new Level(level);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        if (fee == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Fee.class.getSimpleName()));
        }
        if (!Fee.isValidFee(fee)) {
            throw new IllegalValueException(Fee.MESSAGE_CONSTRAINTS);
        }

        final Address modelAddress = new Address(address);

        final Fee modelFee = new Fee(fee, LocalDateTime.parse(lastPaymentDateAsString,
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(tuteeTags);
        return new Tutee(modelName, modelPhone, modelLevel, modelAddress, modelFee, modelRemark, modelTags);
    }

}

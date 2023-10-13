package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * Represents when a Person was last modified in the address book.
 * Guarantees: immutable; is valid as declared in LocalDateTime java API
 */
public class LastModifiedDateTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Last modified should consist of a DateTime Object or the date as a string that is correct" +
                    " (for de-serialization)" +
                    ", and creation should not be exposed to user.";

    /**
     * Default LastModifiedDateTime when a more meaningful one cannot be found.
     */
    public static final LocalDateTime DEFAULT_LAST_MODIFIED = LocalDateTime.of(
            10, 10, 10, 10, 10);

    public static final DateTimeFormatter LASTMODIFIED_FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    .withResolverStyle(ResolverStyle.STRICT)
                    .withLocale(Locale.ENGLISH);
    private LocalDateTime lastModified;



    /**
     * Constructs a @code LastModifiedDateTime from a @code LocalDateTime object
     * @param lastModified the @code LocalDateTime represented by this class
     */
    public LastModifiedDateTime(LocalDateTime lastModified) {
        requireNonNull(lastModified);
        this.lastModified = lastModified;
        // no other sanity checking should be required, as the
        // LocalDateTime object encapsulates already.
        // No sanity checking on the SEMANTICS of the provided LocalDateTime is provided.
        // Doing so goes beyond the responsibilities of this class.
    }

    /**
     * Constructs a @code LastModifiedDateTime from a @code String formatted as an accepted format,
     * The accepted formatter may be found as @code LastModifiedDateTime.LASTMODIFIED_FORMATTER.
     * @param lastModified a valid & correctly formatted string representation of a date & time
     * @return the corresponding @code LastModifiedDateTime object
     */
    public static LastModifiedDateTime fromString(String lastModified) {
        requireNonNull(lastModified);
        checkArgument(isValidLastModifiedDateTime(lastModified), MESSAGE_CONSTRAINTS);
        return new LastModifiedDateTime(LocalDateTime.parse(lastModified,LASTMODIFIED_FORMATTER));
    }


    /**
     * Returns if a string representation of a date & time is valid (matching the format of this class)
     */
    public static boolean isValidLastModifiedDateTime(String lastModified) {

        // Try-catch is used for control flow here, bad but needed due to limitations of
        // java API
        try {
            LocalDateTime.parse(lastModified,LASTMODIFIED_FORMATTER);
            return true;
        } catch (DateTimeParseException e){
            return false;
        }
        // While normally not recommended, there is no method exposed in the java.time API
        // to validate parsing without resorting to a try-catch block.
        // So we have to do this.
        // The closest is DateTimeFormatter#parse(CharSequence text, ParsePosition position)
        // But no good way to validate that all expected fields are there
        // Except to go even deeper into time API
        // Which will probably obfuscate code even more
    }

    @Override
    public String toString() {
        return lastModified.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LastModifiedDateTime)) {
            return false;
        }
        // safe as we did type validation above
        LastModifiedDateTime otherLastModifiedDateTime = (LastModifiedDateTime) other;
        return lastModified.equals(otherLastModifiedDateTime.lastModified);
    }

    @Override
    public int hashCode() {
        return lastModified.hashCode();
    }
}

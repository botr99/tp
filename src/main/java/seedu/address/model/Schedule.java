package seedu.address.model;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.exceptions.ScheduleClashException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tutee.Tutee;

/**
 * Represents the user's Schedule of lessons for the week.
 */
public class Schedule {

    public static final String SCHEDULE_CLASH_MESSAGE = "Schedule clash for the lesson: %1$s";

    /* Stores a sorted map of Lessons to tutees' names */
    private TreeMap<Lesson, String> sortedLessonsMap = new TreeMap<>();

    /**
     * Initialises the map with data retrieved from the tutee list.
     *
     * @param tutees List of tutees to process to populate the map.
     * @throws ScheduleClashException When there is a clash in lessons in the tutee list.
     */
    public Schedule(List<Tutee> tutees) throws ScheduleClashException {
        initSortedLessonsMap(tutees);
    }

    private void initSortedLessonsMap(List<Tutee> tutees) throws ScheduleClashException {
        for (Tutee tutee : tutees) {
            List<Lesson> lessons = tutee.getLessons();
            String tuteeName = tutee.getName().toString();
            for (Lesson lesson : lessons) {
                add(lesson, tuteeName);
            }
        }
    }

    /**
     * Clears the Schedule.
     */
    public void clear() {
        sortedLessonsMap = new TreeMap<>();
    }

    /**
     * Gets the map stored in Schedule.
     *
     * @return A copy of the TreeMap.
     */
    public TreeMap<Lesson, String> getSortedLessonsMap() {
        return new TreeMap<>(sortedLessonsMap);
    }

    /**
     * Adds a key-value pair to the map.
     *
     * @param lesson The corresponding key.
     * @param tuteeName The corresponding value.
     * @throws ScheduleClashException When the lesson clashes with the Schedule.
     */
    public void add(Lesson lesson, String tuteeName) throws ScheduleClashException {
        if (isClash(lesson)) {
            throw new ScheduleClashException(String.format(SCHEDULE_CLASH_MESSAGE, lesson));
        }
        sortedLessonsMap.put(lesson, tuteeName);
    }

    private boolean isClash(Lesson lesson) {
        return sortedLessonsMap.containsKey(lesson);
    }

    /**
     * Removes a key-value pair from the map.
     *
     * @param lesson The corresponding key.
     * @param tuteeName The corresponding value.
     * @return True if the key-value pair is removed; false otherwise.
     */
    public boolean remove(Lesson lesson, String tuteeName) {
        return sortedLessonsMap.remove(lesson, tuteeName);
    }

    @Override
    public String toString() {
        Set<Map.Entry<Lesson, String>> entrySet = sortedLessonsMap.entrySet();

        if (entrySet.isEmpty()) {
            return "There are no lessons scheduled for the week.";
        }

        final StringBuilder builder = new StringBuilder();

        DayOfWeek currentDay = null;
        boolean isCurrentDayNotDisplayed = true;

        for (Map.Entry<Lesson, String> entry : entrySet) {
            Lesson lesson = entry.getKey();
            String tuteeName = entry.getValue();
            DayOfWeek lessonDay = lesson.getTime().getDayOfOccurrence();

            if (!lessonDay.equals(currentDay)) {
                currentDay = lessonDay;
                isCurrentDayNotDisplayed = true;
            }

            if (isCurrentDayNotDisplayed) {
                builder.append("\n")
                        .append(currentDay.getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
                        .append("\n");
                isCurrentDayNotDisplayed = false;
            }

            builder.append("\u2022 ")
                    .append(lesson.toCondensedString())
                    .append("(")
                    .append(tuteeName)
                    .append(")\n");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && sortedLessonsMap.equals(((Schedule) other).getSortedLessonsMap())); // state check
    }

}

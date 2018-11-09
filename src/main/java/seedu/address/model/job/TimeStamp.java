package seedu.address.model.job;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Timestamp for modelling time
 */
public class TimeStamp {
    private static final long MILLIS_IN_MINUTES = 60000;
    private static final long MILLIS_IN_HOURS = MILLIS_IN_MINUTES * 60;
    private static final long MILLIS_IN_DAYS = MILLIS_IN_HOURS * 24;
    private static final long MILLIS_IN_YEARS = MILLIS_IN_DAYS * 365;
    private Calendar calendar;

    public TimeStamp() {
        this.calendar = Calendar.getInstance();
    }

    public TimeStamp(long millis) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(millis);
    }

    public static long hoursToMillis(float hours) {
        return (long) (hours * MILLIS_IN_HOURS);
    }

    /**
     * Return the calendar
     *
     * @return
     */
    public Calendar getCalendar() {
        return calendar;
    }

    public Date getDate() {
        return this.getCalendar().getTime();
    }

    public static boolean compareTime(TimeStamp time1, TimeStamp time2) {
        return time1.getDate().getTime() <= time2.getDate().getTime();
    }

    public static long timeDifference(TimeStamp time1, TimeStamp time2) {
        return time2.getDate().getTime() - time1.getDate().getTime();
    }

    public static TimeStamp timeAdd(TimeStamp time1, TimeStamp time2) {
        return new TimeStamp(time2.getDate().getTime() + time1.getDate().getTime());
    }

    /**
     * Shows the time
     *
     * @return
     */
    public String showTime() {
        return this.getCalendar().get(Calendar.DAY_OF_MONTH) + "/" + (this.getCalendar().get(Calendar.MONTH) + 1) + " "
            + this.getCalendar().get(Calendar.HOUR) + ":" + this.getCalendar().get(Calendar.MINUTE) + ":" + this
            .getCalendar().get(Calendar.SECOND);
    }

    /**
     * Shows the duration formatted
     *
     * @return
     */
    public String showAsDuration() {
        long duration = this.getDate().getTime();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0000"));
        if (duration < MILLIS_IN_MINUTES) {
            return calendar.get(Calendar.SECOND) + " Seconds";
        } else if (duration == MILLIS_IN_MINUTES) {
            return calendar.get(Calendar.MINUTE) + " Minute";
        } else if (duration < MILLIS_IN_HOURS) {
            return calendar.get(Calendar.MINUTE) + " Minutes";
        } else if (duration < MILLIS_IN_HOURS * 2) {
            return calendar.get(Calendar.HOUR_OF_DAY) + " Hour " + calendar.get(Calendar.MINUTE) + " Minutes";
        } else if (duration < MILLIS_IN_DAYS) {
            return calendar.get(Calendar.HOUR_OF_DAY) + " Hours " + calendar.get(Calendar.MINUTE) + " Minutes";
        } else if (duration < MILLIS_IN_DAYS * 2) {
            return (calendar.get(Calendar.DAY_OF_YEAR) - 1) + " Day " + calendar.get(Calendar.HOUR_OF_DAY) + " Hours ";
        } else if (duration < MILLIS_IN_YEARS) {
            return (calendar.get(Calendar.DAY_OF_YEAR) - 1) + " Days " + calendar.get(Calendar.HOUR_OF_DAY) + " Hours ";
        } else {
            return (calendar.get(Calendar.YEAR) - 1970) + " Years " + (calendar.get(Calendar.DAY_OF_YEAR) - 1)
                + " Days ";
        }
    }

    /**
     * Shows the time
     */
    public static String showAsDuration(long millis) {
        TimeStamp t = new TimeStamp(millis);
        return t.showAsDuration();
    }

}

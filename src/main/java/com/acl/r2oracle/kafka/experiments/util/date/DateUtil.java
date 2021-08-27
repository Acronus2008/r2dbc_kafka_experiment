package com.acl.r2oracle.kafka.experiments.util.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class DateUtil {
    public static final int LEGAL_AGE = 18;
    private static final String DEFAULT_TIME_ZONE = "America/Santiago";
    private static final String ISO_DATE_FORMAT = "uuuu-MM-dd'T'HH:mm:ss.SSSZ";

    private static final Function<Calendar, Calendar> buildCalendar = instance -> {
        instance.add(Calendar.HOUR_OF_DAY, -3);
        return instance;
    };

    public static final Predicate<Long> compareCurrentDateWithADate = currentDateInMilliseconds -> {
        Calendar yearEarlier = buildCalendar.apply(Calendar.getInstance());
        yearEarlier.add(Calendar.DATE, -365);
        return currentDateInMilliseconds < yearEarlier.getTimeInMillis();
    };

    public static final Function<String, Calendar> calendarYearBefore = timeZone -> {
        System.setProperty("user.timezone", Objects.isNull(timeZone) ? DEFAULT_TIME_ZONE : timeZone);
        return buildCalendar.apply(Calendar.getInstance());
    };

    public static final Function<String, Calendar> calendarDateYearBefore = daysValues -> {
        Calendar yearEarlier = buildCalendar.apply(Calendar.getInstance());
        yearEarlier.add(Calendar.DATE, -Integer.parseInt(daysValues));
        return yearEarlier;
    };

    /**
     * Determina si una persona es mayor de edad
     *
     * @param birthDate
     * @return
     */
    public static Boolean isOlder(Calendar birthDate) {
        // año de la fecha de nacimiento
        int year = birthDate.get(Calendar.YEAR);
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        // año de la fecha actual
        int currentYear = currentDate.get(Calendar.YEAR);

        return ((currentYear - year) >= LEGAL_AGE);
    }

    /**
     * Determina si una persona es mayor de edad
     *
     * @param birthDate
     * @return
     */
    public static Boolean isOlder(Date birthDate) {
        // año de la fecha de nacimiento
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        int year = calendar.get(Calendar.YEAR);
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        // año de la fecha actual
        int currentYear = currentDate.get(Calendar.YEAR);

        return ((currentYear - year) >= LEGAL_AGE);
    }

    public static final BiFunction<String, String, String> formatDateAndYear = (timeZone, daysValues) -> new SimpleDateFormat("MM/dd/yyyy").format(calendarDateYearBefore.apply(daysValues).getTime());

    public static final Function<String, String> actualDateToString = timeZone -> new SimpleDateFormat("MM/dd/yyyy").format(calendarYearBefore.apply(timeZone).getTime());

    public static final BiFunction<GregorianCalendar, ZoneId, String> formatToISODate = (calendar, zone) ->
            calendar.toZonedDateTime().toLocalDateTime().atZone(zone).format(DateTimeFormatter.ofPattern(ISO_DATE_FORMAT, Locale.US));

    public static final Function<LocalDate, Date> formatLocalDateToDate = localDate -> Date.from(localDate.atStartOfDay(ZoneId.of(DEFAULT_TIME_ZONE)).toInstant());

    public static final Function<Calendar, String> parseCalendarToString = calendar -> new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
}

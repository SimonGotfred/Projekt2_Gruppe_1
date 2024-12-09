import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

public enum Discipline
{ // enum names are in danish as they are communicated to, and expected as input from, a danish user
    FRISVØMNING,
    RYGSVØMNING,
    BRYSTSVØMNING,
    BUTTERFLY,
    MEDLEY,

    UDSPRING,
    VANDPOLO,
    UNDERVANDSRUGBY,
    SYNKRONSVØMNING;

    // this enum is ordered according to scoring-type.
    // returns whether to handle marks within the discipline as time or points
    public boolean isTimeBased(){return this.ordinal() < UDSPRING.ordinal();}

    // string to communicate related scoring-type to user.
    public String scoringType()
    {
        if (this.isTimeBased()) return "tid";
        return "scoring";
    }

    // formats mark to string, since time-based disciplines should read as time (i.e. min:sec)
    public String formatMark(int mark)
    {
        if (this.isTimeBased()) return (mark/60) + ":" + String.format("%02d", mark % 60);
        return mark + "pt";
    }

    // Interpret string as an integer, as user inputs different format of mark for time/point based disciplines.
    public int markToInt(String mark) throws NumberFormatException, DateTimeParseException
    {
        if (isTimeBased()) // if time-based format mark as total seconds to store in integer.
        {
            mark = "0." + mark; // hard-set zero hours, as formatter wants hours, and marks are not expected to be over an hour.
            return (int) LocalTime.parse(mark, DateTimeFormatter.ofPattern("H.m.s")).getLong(ChronoField.SECOND_OF_DAY);
        }
        return Integer.parseInt(mark);
    }
}
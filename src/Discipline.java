import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

public enum Discipline
{
    FRISVØMNING,
    RYGSVØMNING,
    BRYSTSVØMNING,
    BUTTERFLY,
    MEDLEY,

    UDSPRING,
    VANDPOLO,
    UNDERVANDSRUGBY,
    SYNKRONSVØMNING;

    public boolean isTimeBased(){return this.ordinal() < UDSPRING.ordinal();}

    public String scoringType()
    {
        if (this.isTimeBased()) return "tid";
        return "scoring";
    }

    public String formatMark(int mark)
    {
        if (this.isTimeBased()) return (mark/60) + ":" + String.format("%02d", mark % 60);
        return mark + "pt";
    }

    public int markToInt(String mark) throws NumberFormatException, DateTimeParseException
    {
        if (isTimeBased())
        {
            mark = "0." + mark;
            return (int) LocalTime.parse(mark, DateTimeFormatter.ofPattern("H.m.s")).getLong(ChronoField.SECOND_OF_DAY);
        }
        return Integer.parseInt(mark);
    }
}
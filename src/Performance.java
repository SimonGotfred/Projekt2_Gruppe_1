import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Instances of Performance represent individual performances of an athlete within a given discipline.
public class Performance implements Comparable<Performance>
{
    public static void main(String[] args) // FOR TESTING
    {
        Performance p = null;
        boolean run = true;

        while (run)
        {
            try
            {
                p = new Performance();
                run = false;
            }
            catch (AbortToMenuCommand e)
            {
                System.out.println("\nFORFRA!!\n");
            }
        }

        System.out.println(p);

    } // END OF TESTING

    // default location, for when not attending contests.
    // Allowing the user to "hotkey" when location is not necessary
    private static final String defaultLocation = "Træning";

    // Performances should not be altered after they are created
    public final Discipline    discipline;
    public final LocalDateTime dateTime;
    public final String        location;
    public final String        note;
    public final int           mark;
    public final int           placement;


    public Performance() throws AbortToMenuCommand
    {
        // inquire the user for each attribute for the performance.
        this.discipline = Sorter.chooseDiscipline();
        this.dateTime   = inqDateTime();
        this.location   = inqLocation();
        this.placement  = inqPlacement();
        this.mark       = inqScoring();
        this.note       = inqNote();
    }

    // constructor taking arguments for each attribute. Primarily for loading from data.
    public Performance(Discipline discipline, LocalDateTime dateTime, String location, int mark, int placement, String note)
    {
        this.discipline = discipline;
        this.dateTime   = dateTime;
        this.location   = location;
        this.note       = note;
        this.mark       = mark;
        this.placement  = placement;
    }

    // inquire user for a discipline
    @Deprecated // Sorter.chooseDiscipline() handles this better.
    public static Discipline inqDiscipline() throws AbortToMenuCommand
    {
        String input = UI.inquire("Indtast disciplin: ");
        try
        {
            return Discipline.valueOf(input.toUpperCase());
        }
        catch (IllegalArgumentException e) // try again if discipline is not recognized
        {
            System.out.println("\t\"" + input + "\" er ikke en gyldig disciplin.");
            return inqDiscipline();
        }
    }

    // inquire user for date and time when the Performance occurred
    public LocalDateTime inqDateTime() throws AbortToMenuCommand
    {
        String input = UI.inquire("Indtast dato & tidspunkt (H:m d M yyyy), eller tryk 'enter' for nuværende tidspunkt:\n");

        // "hotkey" to note 'now' as the time - e.g. for when this is less relevant or not certainly know
        if (input.isEmpty()) return LocalDateTime.now();

        // formatter in expected format for input - structured as to allow partial completion by user input.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H.m d M yyyy");
        input = input.replace(':','.');

        // parse input - allowing for user to only input 'time', 'time&date', or 'time&date&year',
        // assuming current day/month/year for the rest
        try {return LocalDateTime.parse(input, formatter);}
        catch (DateTimeParseException _)
        {
            LocalDate now = LocalDate.now();
            try {return LocalDateTime.parse(input + " " + now.getYear(), formatter);}
            catch (DateTimeParseException _)
            {
                try {return LocalDateTime.parse(input + " " + now.getDayOfMonth() + " " + now.getMonthValue() + " " + now.getYear(), formatter);}
                catch (DateTimeParseException _)
                {
                    // if input cannot be parsed as either 'time', 'time&date', or 'time&date&year',
                    // display error and try again.
                    System.out.println("\t\"" + input + "\" er ikke et gyldigt format for dato & tidspunkt.");
                    return inqDateTime();
                }
            }
        }
    }

    // inquire user for location were performance occurred
    public String inqLocation() throws AbortToMenuCommand
    {
        String input = UI.inquire("Indtast navn for stævne, eller tryk 'enter' for træning:\n");

        // if nothing is entered, assume default location: "Træning"
        if (input.isEmpty()) return defaultLocation;

        return input;
    }

    // inquire user for competitors placement in a contest with this performance
    public int inqPlacement() throws AbortToMenuCommand
    {
        if (this.location.equals(defaultLocation)) return 0; // default to '0' if performance wasn't at a contest
        String input = UI.inquire("Indtast placering: ");

        try // parse input as an integer, or try again if failed to.
        {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException _)
        {
            System.out.println("\t\"" + input + "\" er ikke et gyldigt format for placering, indtast venligst et tal.");
            return inqPlacement();
        }
    }

    // inquire user for amount of points/time taken during performance
    public int inqScoring() throws AbortToMenuCommand // TODO: learn 'instanceof'
    {
        String input = UI.inquire("Indtast " + this.discipline.scoringType() + ": ");
        input = input.replace(':','.');

        try // convert whichever formatted input to an integer
        {
            return discipline.markToInt(input);
        }
        catch (RuntimeException e)
        {
            // if (e.getClass().isInstance(new DateTimeParseException("","",0)) || e.getClass().isInstance(new NumberFormatException("")))

            // if exception may have been regarding input format, try again.
            if (e instanceof DateTimeParseException || e instanceof NumberFormatException)
            {
                System.out.println("\t\"" + input + "\" er ikke et gyldigt format for " + discipline.scoringType() + ".");
                return inqScoring();
            }
            throw e; // if error was NOT regarding formatting - throw it further.
        }
    }

    // inquire user for potential further notes regarding performance
    public String inqNote() throws AbortToMenuCommand
    {
        System.out.println("\n"+this+"\n"); // display the performance as established so far
        return UI.inquire("Indtast yderligere note hvis ønsket, og tryk 'enter' for at færdiggøre:\n");
    }

    public String toString()
    {
        String string =  location + "-" + discipline.toString().toLowerCase() + "@@ " + discipline.formatMark(mark) + " Dato: " + dateTime.format(UI.dateTimeFormatter);

        // insert contest placement, if performance was during a contest.
        if (placement != 0) string = string.replace("@@", " #" + placement);
        else  string = string.replace("@@", "");

        return string;
    }

    // returns performance as string, with potential note
    public String toStringFull()
    {
        if (note != null && !note.trim().isEmpty()) return this + "\nNote: " + note;
        return this.toString();
    }

    public int compareTo(Performance other)
    {
        // sort by each discipline, though not "more or less" than each other
        // - ensures that performances within a different disciplines are not mixed together during sorting.
        if (this.discipline != other.discipline) return this.discipline.ordinal() - other.discipline.ordinal();
        if (discipline.isTimeBased()) return this.mark - other.mark; // low times sorted to "top"
        else return other.mark - this.mark; // high points sorted to "top"
    }
}

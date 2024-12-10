import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    private static final String defaultLocation = "Træning";

    public final Discipline    discipline;
    public final LocalDateTime dateTime;
    public final String        location;
    public final String        note;
    public final int           mark;
    public final int           placement;

    public Performance() throws AbortToMenuCommand {this(Sorter.chooseDiscipline());}
    public Performance(Discipline discipline) throws AbortToMenuCommand
    {
        this.discipline = discipline;
        this.dateTime   = inqDateTime();
        this.location   = inqLocation();
        this.placement  = inqPlacement();
        this.mark       = inqScoring();
        this.note       = inqNote();
    }

    public Performance(Discipline discipline, LocalDateTime dateTime, String location, int mark, int placement, String note)
    {
        this.discipline = discipline;
        this.dateTime   = dateTime;
        this.location   = location;
        this.note       = note;
        this.mark       = mark;
        this.placement  = placement;
    }

    public static Discipline inqDiscipline() throws AbortToMenuCommand
    {
        String input = UI.inquire("Indtast disciplin: ");
        try
        {
            return Discipline.valueOf(input.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\t\"" + input + "\" er ikke en gyldig disciplin.");
            return inqDiscipline();
        }
    }

    public LocalDateTime inqDateTime() throws AbortToMenuCommand
    {
        String input = UI.inquire("Indtast dato & tidspunkt (d M T.m), eller tryk 'enter' for nuværende tidspunkt:\n");

        if (input.isEmpty()) return LocalDateTime.now();

        input = input.replace(':','.');

        try
        {
            return LocalDateTime.parse(LocalDate.now().getYear() + " " + input, DateTimeFormatter.ofPattern("yyyy d M H.m"));
        }
        catch (DateTimeParseException e)
        {
            System.out.println("\t\"" + input + "\" er ikke et gyldigt format for dato & tidspunkt.");
            return inqDateTime();
        }
    }

    public String inqLocation() throws AbortToMenuCommand
    {
        String input = UI.inquire("Indtast navn for stævne, eller tryk 'enter' for træning:\n");

        if (input.isEmpty()) return defaultLocation;

        return input;
    }

    public int inqPlacement() throws AbortToMenuCommand
    {
        if (this.location.equals(defaultLocation)) return 0;
        String input = UI.inquire("Indtast placering: ");

        try
        {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\t\"" + input + "\" er ikke et gyldigt format for placering, indtast venligst et tal.");
            return inqPlacement();
        }
    }

    public int inqScoring() throws AbortToMenuCommand // TODO: learn 'instanceof'
    {
        String input = UI.inquire("Indtast " + this.discipline.scoringType() + ": ");
        input = input.replace(':','.');

        try
        {
            return discipline.markToInt(input);
        }
        catch (RuntimeException e)
        {
            // if (e.getClass().isInstance(new DateTimeParseException("","",0)) || e.getClass().isInstance(new NumberFormatException("")))
            if (e instanceof DateTimeParseException || e instanceof NumberFormatException)
            {
                System.out.println("\t\"" + input + "\" er ikke et gyldigt format for " + discipline.scoringType() + ".");
                return inqScoring();
            }
            throw e;
        }
    }

    public String inqNote() throws AbortToMenuCommand // TODO: beautify
    {
        System.out.println("\n"+this+"\n");
        return UI.inquire("Indtast yderligere note hvis ønsket, og tryk 'enter' for at færdiggøre:\n");
    }

    public String toString()
    {
        String string =  location + "-" + discipline.toString().toLowerCase() + "@@ " + discipline.formatMark(mark) + " Dato: " + dateTime.format(UI.dateTimeFormatter);

        if (placement != 0) string = string.replace("@@", " #" + placement);
        else  string = string.replace("@@", "");

        return string;
    }

    public String toStringFull()
    {
        if (note != null && !note.trim().isEmpty()) return this + "\nNote: " + note;
        return this.toString();
    }

    public int compareTo(Performance other)
    {   // sort by each discipline
        if (this.discipline != other.discipline) return this.discipline.ordinal() - other.discipline.ordinal();
        if (discipline.isTimeBased()) return this.mark - other.mark; // low times sorted to "top"
        else return other.mark - this.mark; // high points sorted to "top"
    }
}

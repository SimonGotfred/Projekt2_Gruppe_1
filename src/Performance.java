import java.time.LocalDateTime;

public class Performance implements Comparable<Performance>
{
    private static final String defaultLocation = "Træning";

    public  final Discipline discipline;
    public  LocalDateTime dateTime;
    private String          location;
    private String          note;
    private int             mark;

    public Performance(Discipline discipline)
    {
        this.discipline = discipline;
        dateTime = LocalDateTime.now();
        location = defaultLocation;
        mark = 0;
    }

    public Performance(String discipline){this(Discipline.valueOf(discipline));}

    public Discipline getDiscipline()               {return discipline;}

    public LocalDateTime getDateTime()              {return dateTime;}
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
    public void setDateTime(String date)
    {
        setDateTime(LocalDateTime.parse(date, MemberRegister.dateTimeFormatter));
    }

    public String getLocation()              {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getNote()          {return note;}
    public void setNote(String note) {this.note = note;}

    public int getMark() {return mark;} // TODO: interpret through discipline
    public void setMark(int mark) // TODO: interpret through discipline
    {
        this.mark = mark;
    }

    public String toString() // TODO: interpret through discipline
    {
        return super.toString();
    }

    public int compareTo(Performance other) // TODO: interpret through discipline
    {
        return this.mark - other.getMark();
    }
}

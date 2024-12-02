import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Competitor extends Member {

    public static void main(String[] args) {
        // Create a list of competitors
        List<Competitor> competitors = new ArrayList<>();
        competitors.add(new Competitor("Emil", LocalDate.of(1998,5,14),     "26799895","Crawl"));
        competitors.add(new Competitor("Sabrina", LocalDate.of(2005,5,14),  "26799891","Free Swim"));
        competitors.add(new Competitor("Alexander", LocalDate.of(2004,5,14),"25799895","Breast swim"));
        competitors.add(new Competitor("Simon", LocalDate.of(2008,5,14),    "26799795","Backwards"));

        // Separate competitors into two groups defined by age
        List<Competitor> under18 = new ArrayList<>();
        List<Competitor> over18 = new ArrayList<>();

        for (Competitor competitor : competitors) {
            if (competitor.isJunior()) {
                under18.add(competitor);
            } else {
                over18.add(competitor);
            }
        }

        // Display the groups using the custom displayDetails method
        System.out.println("Junior Competitors:");
        for (Competitor competitor : under18) {
            System.out.println(competitor.displayDetails());
        }

        System.out.println("Senior Competitors:");
        for (Competitor competitor : over18) {
            System.out.println(competitor.displayDetails());
        }
    }
    //Defines what a Competitor is
    ArrayList<Discipline> discipline;
    ArrayList<Performance> performances;

    // Constructor
    public Competitor(String name, LocalDate birthDate, String phoneNumber, String discipline) {
        super(name, birthDate, phoneNumber);
        this.discipline.add(Discipline.valueOf(discipline));
        this.performances = new ArrayList<Performance>();
    }

    // Custom method to display competitor details easy and simple
    public String displayDetails() {
        return "Name: " + name + ", Age: " + getAge() + ", Swim Time: " + discipline + " seconds";
    }

    public int compareTo (Competitor other) {
        if(this.performances.isEmpty()) return -1;
        if(other.performances.isEmpty()) return +1;
        return this.performances.getFirst().compareTo(other.performances.getFirst());

    }
}
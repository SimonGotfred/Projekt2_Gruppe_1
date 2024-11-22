import java.util.ArrayList;
import java.util.List;

public class Competitor {

    //Defines what a Competitor is
    private String name;
    private int age;
    private double swimTime;

    // Constructor
    public Competitor(String name, int age, double swimTime) {
        this.name = name;
        this.age = age;
        this.swimTime = swimTime;
    }

    // Method to check if the competitor is under 18
    public boolean isUnder18() {
        return age < 18;
    }

    // Custom method to display competitor details easy and simple
    public String displayDetails() {
        return "Name: " + name + ", Age: " + age + ", Swim Time: " + swimTime + " seconds";
    }

    public static void main(String[] args) {
        // Create a list of competitors
        List<Competitor> competitors = new ArrayList<>();
        competitors.add(new Competitor("Emil", 18, 40.3));
        competitors.add(new Competitor("Sabrina", 17, 50.1));
        competitors.add(new Competitor("Alexander", 14, 48.2));
        competitors.add(new Competitor("Simon", 200, 60.5));

        // Separate competitors into two groups defined by age
        List<Competitor> under18 = new ArrayList<>();
        List<Competitor> over18 = new ArrayList<>();

        for (Competitor competitor : competitors) {
            if (competitor.isUnder18()) {
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
}
import java.util.ArrayList;

public class Sorter {
    static ArrayList<Member> membersSort = MemberRegister.members;

    static ArrayList<Competitor> competitors() {                                 //Sorts for competitors
        ArrayList<Competitor> competitors = new ArrayList<Competitor>();
            for (Member member : Sorter.membersSort) {                                  //Runs through the member list
                if (member instanceof Competitor competitor){                    //If the member is competitor, creates an instance of competitor
                    competitors.add(competitor);                                 //Adds the competitor to the list
                }
            } return competitors;
    }

    static ArrayList<Competitor> disciplineSorter (String discipline){      //Sorts by discipline
        ArrayList<Competitor> disciplineSorted = new ArrayList<>();

        for (Competitor e : Sorter.competitors()){                            //Run through competitors
            if (e.discipline.equals(discipline)){
                disciplineSorted.add(e);
            }
        } return disciplineSorted;
    }
    static ArrayList<Competitor> sortResults () {
        ArrayList<Competitor> resultsSorted = competitors();
        resultsSorted.sort(null);
        return resultsSorted;
    }
}

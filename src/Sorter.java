import java.util.ArrayList;

public class Sorter {
    ArrayList<Member> membersSort = MemberRegister.members;

    public ArrayList<Competitor> competitors() {                                 //Sorts for competitors
        ArrayList<Competitor> competitors = new ArrayList<Competitor>();
            for (Member member : membersSort) {                                  //Runs through the member list
                if (member instanceof Competitor competitor){                    //If the member is competitor, creates an instance of competitor
                    competitors.add(competitor);                                 //Adds the competitor to the list
                }
            } return competitors;
    }

    public ArrayList<Competitor> disciplineSorter (String discipline){      //Sorts by discipline
        ArrayList<Competitor> disciplineSorted = new ArrayList<>();

        for (Competitor e : this.competitors()){                            //Run through competitors
            if (e.discipline.equals(discipline)){
                disciplineSorted.add(e);
            }
        } return disciplineSorted;
    }

    /*
    public void topFive (ArrayList<Competitor> competitors){

        Competitor reference = competitors.getFirst();
        for (int i=1; i < competitors.size(); i++){
            if (competitors.get(i) < reference) {
                reference = competitors.get(i);
            }
        }
    }
    */
}

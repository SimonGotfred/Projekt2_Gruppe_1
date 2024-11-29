import java.util.ArrayList;

public class Sorter {
    static ArrayList<Member> membersSort = MemberRegister.members;

    static ArrayList<Competitor> competitors() {                                 //Sorts for competitors
        ArrayList<Competitor> competitors = new ArrayList<Competitor>();
            for (Member member : Sorter.membersSort) {                           //Runs through the member list
                if (member instanceof Competitor competitor){                    //If the member is competitor, creates an instance of competitor
                    competitors.add(competitor);                                 //Adds the competitor to the list
                }
            } return competitors;
    }

    static ArrayList<Competitor> disciplineSorter (String discipline){          //Sorts by discipline
        ArrayList<Competitor> disciplineSorted = new ArrayList<>();

        for (Competitor e : Sorter.competitors()){                              //Run through competitors
            if (e.discipline.equals(discipline)){                               //If the competitor has the desired discipline
                disciplineSorted.add(e);                                        //Add the competitor to disciplineSorted
            }
        } return disciplineSorted;
    }

    static void topFive (ArrayList<Competitor> fullList) {                      //PRINT TOP FIVE
        fullList.sort(null);                                                    //Sort the list provided (should be a list made with the disciplineSorted method)

        int i;
        if (5>fullList.size())                                                  //If there are less than five competitors on the list, print out the whole list
        for (i=0; i<fullList.size(); i++) {
            System.out.println(fullList.get(i));
        }
        else {                                                                  //Else print the first 5 only
            for (i=0; i<5; i++){
                System.out.println(fullList.get(i));
            }
        }
    }
}

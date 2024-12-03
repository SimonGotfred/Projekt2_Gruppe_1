import java.util.ArrayList;
import java.util.Scanner;

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

    static ArrayList<Member> membersByName (ArrayList<Member> toSearch) {
        String name;
        Scanner sc = new Scanner(System.in);
        ArrayList<Member> equalsName = new ArrayList<>();

            System.out.println("Søg på navn:");                     //User searches a name
            name = sc.next();
            for (Member e : toSearch) {
                if (e.getName().equalsIgnoreCase(name)) {            //All members with that name are added to a list
                    equalsName.add(e);
                }
            }
            if (equalsName.isEmpty()) {
                System.out.println("Der er ingen medlemmer med det navn");

        } return equalsName;
    }

    static Member chooseMember(ArrayList<Member> chooseFrom){
        Scanner sc = new Scanner(System.in);
        int choice;
        String choiceSwitch;
        boolean choose = true;
        Member chosenMember = null;

        while (choose) {
            for (int i = 0; i < chooseFrom.size(); i++) {                           //Print the list of members
                System.out.println("Tryk " + i + 1 + ":\t" + chooseFrom.get(i));    //Assign a value to each
            }
            choice = sc.nextInt() - 1;                                              //User chooses
            if (-1 < choice && choice < chooseFrom.size()){                         //If the choice is on the list
                choose = false;                                                     //Continue from the loop
            } else {
                System.out.println("Vælg fra listen");                              //Else try again
            }

            System.out.println("Bekræft valg af medlem:\n" + chooseFrom.get(choice));   //Confirm the chosen member
            System.out.println("\n\nTryk 1: Bekræft\t\tTryk 2: Vælg andet medlem");

            choiceSwitch = sc.nextLine();
            switch (choiceSwitch) {
                case "1":
                    chosenMember = chooseFrom.get(choice);
                    return chosenMember;
                case "2":
                    break;
                case "q":
                    return null;

            }
        }
        return chosenMember;
    }
}

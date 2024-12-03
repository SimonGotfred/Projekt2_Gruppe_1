import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

    static ArrayList<Competitor> disciplineSorter (Discipline discipline){          //Sorts by discipline
        ArrayList<Competitor> disciplineSorted = new ArrayList<>();

        for (Competitor e : Sorter.competitors()){                              //Run through competitors
            for (Discipline d: e.discipline) {                                  //Run through their disciplines
            if (d.equals(discipline)){                                          //If the competitor has the desired discipline
                disciplineSorted.add(e);                                        //Add the competitor to disciplineSorted
                }
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
        ArrayList<Member> containsName = new ArrayList<>();
        LocalDate checkIfDate = null;
        int phoneNumber = 0;

        while (true) {
            try {
                name = UI.inquire("Søg på navn, fødselsdag (d M yyyy) eller telefonnummer:");                                       //User searches a name
            }
            catch(ExitMenuCommand e) {return null;}
            for (Member e : toSearch) {
                try {
                    checkIfDate = LocalDate.parse(name,MemberRegister.dateTimeFormatter);       //Check if it is a date
                } catch (DateTimeParseException d){}                                            //If it is not a date
                try {phoneNumber = Integer.parseInt(name);}                                     //Check if it is an integer
                catch (NumberFormatException n){}                                               //If it is not an integer
                if (checkIfDate!=null){
                    if (e.getBirthDate().equals(checkIfDate)) {
                        containsName.add(e);
                    }
                } else if (name.length()==8 && phoneNumber!=0) {
                    if (e.getPhoneNumber().equals(name)){
                        containsName.add(e);
                    }
                    else System.out.println("Der er ingen medlemmer med det telefonnummer");
                }
                else if (e.getName().toLowerCase().contains(name.toLowerCase())) {            //All members with that name are added to a list
                    containsName.add(e);
                }
            }
            if (!containsName.isEmpty()) {
                return containsName;
            }
            System.out.println("Der er ingen medlemmer med det navn");
        }
    }

    static Member chooseMember(ArrayList<Member> chooseFrom){
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        String choiceSwitch;
        boolean choose = true;
        Member chosenMember = null;

        while (choose) {
            for (int i = 0; i < chooseFrom.size(); i++) {                           //Print the list of members
                System.out.println("Tryk " + (i + 1) + ":\t" + chooseFrom.get(i));    //Assign a value to each
            }
            try {
            choice = sc.nextInt() - 1;                                              //User chooses
            if (-1 < choice && choice < chooseFrom.size()){                         //If the choice is on the list
                choose = false;                                                     //Continue from the loop
            } else {System.out.println("Vælg fra listen");}                         //Else try again
            }
            catch (InputMismatchException e){
                System.out.println("Vælg fra listen");
                sc.nextLine();
            }
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
        return chosenMember;
    }
}

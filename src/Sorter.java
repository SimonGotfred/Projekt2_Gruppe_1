import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Sorter {
    public static void main(String[] args) throws ExitMenuCommand {
        ArrayList<Member> testingList = membersSort;
        for (int i = 0; i < 10; i++){
            testingList.add(TestingSuite.getMember());
            System.out.println(testingList.get(i));
        }
        ArrayList<Member> testToPrint = searchMember(testingList);
        for (Member member : testToPrint) {
            System.out.println(member);
        }


    }
    static ArrayList<Member> membersSort = MemberRegister.getMembers();

    static ArrayList<Member> competitors() {                                 //Sorts for competitors
        ArrayList<Member> competitors = new ArrayList<>();
            for (Member member : Sorter.membersSort) {                           //Runs through the member list
                if (member.isCompetitor()){                    //If the member is competitor, creates an instance of competitor
                    competitors.add(member);                                 //Adds the competitor to the list
                }
            } return competitors;
    }

    static ArrayList<Member> disciplineSorter (Discipline discipline){          //Sorts by discipline
        ArrayList<Member> disciplineSorted = new ArrayList<>();

        for (Member e : Sorter.competitors()){                                  //Run through competitors
            for (Discipline d: e.disciplines()) {                               //Run through their disciplines
            if (d.equals(discipline)){                                          //If the competitor has the desired discipline
                disciplineSorted.add(e);                                        //Add the competitor to disciplineSorted
                }
            }
        } return disciplineSorted;
    } // TODO: Use doesDiscipline

    static void topFive (Discipline discipline) {                       //PRINT TOP FIVE

        ArrayList<Member> fullList = disciplineSorter(discipline);      //Make a list of members who competes in the discipline
        ArrayList<Performance> performances = new ArrayList<>();
        ArrayList<Member> topFive = new ArrayList<>();
        HashMap<Performance, Member> memberHashMap = new HashMap<>();

        for (Member e: fullList){
            performances.add(e.getBestPerformance(discipline));         //Adds all the best performances to list
        }
        performances.sort(Comparator.reverseOrder());                   //Sort the performances - reverse as we want lowest times first

        for (Member e : fullList){                                      //Add the members to the hashmap - bestPerformance as key
            memberHashMap.put(e.getBestPerformance(discipline), e);
        }

        for (int i = 0; i < 5; i++) {                                   //Add the 5 best performance to a list
            topFive.add(memberHashMap.get(performances.get(i)));
        }
        System.out.println(topFive);
    }

    static ArrayList<Member> searchMember(ArrayList<Member> toSearch) throws ExitMenuCommand {
        String inputSearchMember;
        ArrayList<Member> containsMember = new ArrayList<>();
        LocalDate checkIfDate = null;
        int outcome = 0;

        while (true) {
                inputSearchMember = UI.inquire("Søg på navn, fødselsdag (d M yyyy) eller telefonnummer:");       //User searches a name
            if (Member.isPhoneNumber(inputSearchMember)) {
                outcome = 1;
            }
            if (Member.isName(inputSearchMember)) {
                outcome = 2;
            }
            try {checkIfDate = LocalDate.parse(inputSearchMember,MemberRegister.dateTimeFormatter);       //Check if it is a date - set LocalDate to the date
            } catch (DateTimeParseException _){}                                                            //If it is not a date
            if (checkIfDate!=null){
                outcome = 3;
            }

            switch (outcome){
                case 1:
                    for (Member e : membersSort){
                        if (e.getPhoneNumber().equals(inputSearchMember)){
                            containsMember.add(e);
                        }
                    }
                    if (!containsMember.isEmpty()){
                        return containsMember;
                    }
                    else {
                        System.out.println("Ingen medlemmer med oplyste telefonnummer");
                        continue;
                    }
                case 2:
                    for (Member e : membersSort){
                        if (e.getName().toLowerCase().contains(inputSearchMember.toLowerCase())){
                            containsMember.add(e);
                        }
                    }
                    if (!containsMember.isEmpty()){
                        return containsMember;
                    }
                    else {
                        System.out.println("Ingen medlemmer med oplyste navn");
                        continue;
                    }
                case 3:
                    for (Member e : membersSort){
                        if (e.getBirthDate().equals(checkIfDate)){
                            containsMember.add(e);
                        }
                    }
                    if (!containsMember.isEmpty()){
                        return containsMember;
                    }
                    else {
                        System.out.println("Ingen medlemmer med oplyste fødselsdato");
                        continue;
                    }
                default:
                    System.out.println("Ugyldigt input");
            }
        }
    }

    static Member chooseMember(ArrayList<Member> chooseFrom) throws ExitMenuCommand {
        int choice = 0;
        String choiceSwitch;
        boolean choose = true;
        Member chosenMember = null;

        if (chooseFrom.size()==1){
            chosenMember = chooseFrom.getFirst();
            return chosenMember;
        } else {

        while (choose) {
            for (int i = 0; i < chooseFrom.size(); i++) {                           //Print the list of members
                System.out.println("Tryk " + (i + 1) + ":\t" + chooseFrom.get(i));    //Assign a value to each
            }
            try {
            choice = Integer.parseInt(UI.inquire()) - 1;                                              //User chooses
            if (-1 < choice && choice < chooseFrom.size()){                         //If the choice is on the list
                choose = false;                                                     //Continue from the loop
            } else {System.out.println("Vælg fra listen");}                         //Else try again
            }
            catch (InputMismatchException e){
                System.out.println("Vælg fra listen");
            }
        }

            System.out.println("Bekræft valg af medlem:\n" + chooseFrom.get(choice));   //Confirm the chosen member
            System.out.println("\n\nTryk 1: Bekræft\t\tTryk 2: Vælg andet medlem");

            choiceSwitch = UI.inquire();
            switch (choiceSwitch) {
                case "1":
                    chosenMember = chooseFrom.get(choice);
                    return chosenMember;
                case "2":
                    break;
            }
        return chosenMember;
    }}
}

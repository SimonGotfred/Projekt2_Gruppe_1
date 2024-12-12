import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Sorter {
    static ArrayList<Member> membersSort = MemberRegister.getMembers();

    static ArrayList<Member> competitors() {                    //Sorts for competitors
        ArrayList<Member> competitors = new ArrayList<>();
            for (Member member : Sorter.membersSort) {         //Runs through the member list
                if (member.isCompetitor()){                    //If the member is competitor
                    competitors.add(member);                   //Adds the competitor to the list
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
        performances.sort(null);                   //Sort the performances - reverse as we want lowest times first

        for (Member e : fullList){                                      //Add the members to the hashmap - bestPerformance as key
            memberHashMap.put(e.getBestPerformance(discipline), e);
        }

        int printNr = Math.min(5, performances.size());                      //Determine if there are less than 5 performances
        for (int i = 0; i < printNr; i++) {                                  //Add a max of the 5 best performance to a list
            topFive.add(memberHashMap.get(performances.get(i)));
        }
        ArrayList<String> toPrint = new ArrayList<>();

            for (Member member : topFive) {                                           //Print the list of members
                toPrint.add(member.toString("n\tb\t" + member.getBestPerformance(discipline)));                                 //Assign a value to each
                UI.println(" \t\t ", toPrint.toArray(new String[0]));                //Format to make equal space
            }
    }

    static ArrayList<Member> searchMember(ArrayList<Member> toSearch) throws AbortToMenuCommand
    {
        String inputSearchMember;
        ArrayList<Member> containsMember = new ArrayList<>();
        LocalDate checkIfDate = null;
        int outcome = 0;

        while (true) {
                inputSearchMember = UI.inquire("Søg på navn, fødselsdag (d M yyyy) eller telefonnummer:\n");       //User searches
            if (Member.isPhoneNumber(inputSearchMember)) {                                                       //Check if phonenumber
                outcome = 1;
            }
            if (Member.isName(inputSearchMember)) {                                                              //Check if name
                outcome = 2;
            }
            try {checkIfDate = LocalDate.parse(inputSearchMember,MemberRegister.dateTimeFormatter);              //Check if it is a date
            } catch (DateTimeParseException _){}                                                                 //If it is not a date
            if (checkIfDate!=null){
                outcome = 3;
            }

            switch (outcome){       //Make a search based on the int outcome
                case 0:
                    containsMember=toSearch;
                    return containsMember;
                case 1:
                    for (Member e : toSearch){
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
                    for (Member e : toSearch){
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
                    for (Member e : toSearch){
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

    static Member chooseMember(ArrayList<Member> chooseFrom) throws AbortToMenuCommand
    {
        int choice = 0;
        String choiceSwitch;
        boolean choose = true;
        Member chosenMember = null;

        switch (chooseFrom.size()){
            case 0:
                System.out.println("Ingen medlemmer opfylder kriteriet.\n");
                throw new AbortToMenuCommand();         //Case 0 is super rare - throwing AbortToMenuCommand will lead the user to a known place
            case 1:
                chosenMember = chooseFrom.getFirst();
                System.out.println(chosenMember);
                return chosenMember;
            default:
                ArrayList<String> toPrint = new ArrayList<>();
                for (int i = 0; i < chooseFrom.size(); i++) {                                           //Print the list of members
                    toPrint.add("Tryk " + (i + 1) + ":\t" + chooseFrom.get(i).toString("n\tb\tp"));    //Assign a value to each
                }

                while (choose) {
                    UI.println(" \t \t \t ",toPrint.toArray(new String[0]));                //Format to make equal space
                try {
                    choice = Integer.parseInt(UI.inquire()) - 1;                             //User chooses
                    if (-1 < choice && choice < chooseFrom.size()){                         //If the choice is on the list
                        choose = false;                                                     //Continue from the loop
                    } else {System.out.println("FEJL: Vælg fra listen\n");}                         //Else try again
                }
                catch (NumberFormatException e){
                    System.out.println("FEJL: Vælg fra listen\n");
                }
            }

                System.out.println("Bekræft valg af medlem:\n\n" + chooseFrom.get(choice));   //Confirm the chosen member
                System.out.println("\nTryk 1: Bekræft\t\tTryk 2: Vælg andet medlem");

                choiceSwitch = UI.inquire();
                switch (choiceSwitch) {
                    case "1":
                        chosenMember = chooseFrom.get(choice);
                        break;
                    case "2":
                        return chooseMember(chooseFrom);
                    default:
                        break;
                }
                return chosenMember;
        }
    }

    static Discipline chooseDiscipline() throws AbortToMenuCommand
    {
        Discipline discipline;
        String answer = UI.inquire("Vælg disciplin:" +
                "\n1:\tFrisvømning\t\t\t"+"4:\tButterfly\t\t"+"7:\tVandpolo" +
                "\n2:\tRygsvømning\t\t\t"+"5:\tMedley\t\t\t"+"8:\tUndervandsrugby" +
                "\n3:\tBrystsvømning\t\t"+"6:\tUdspring\t\t"+"9:\tSynkronsvømning\n\n"+"Tryk 'q' for at gå tilbage");

        while (true){
            switch (answer) {
                case "1":
                    discipline = Discipline.FRISVØMNING;
                    return discipline;
                case "2":
                    discipline = Discipline.RYGSVØMNING;
                    return discipline;
                case "3":
                    discipline = Discipline.BRYSTSVØMNING;
                    return discipline;
                case "4":
                    discipline = Discipline.BUTTERFLY;
                    return discipline;
                case "5":
                    discipline = Discipline.MEDLEY;
                    return discipline;
                case "6":
                    discipline = Discipline.UDSPRING;
                    return discipline;
                case "7":
                    discipline = Discipline.VANDPOLO;
                    return discipline;
                case "8":
                    discipline = Discipline.UNDERVANDSRUGBY;
                    return discipline;
                case "9":
                    discipline = Discipline.SYNKRONSVØMNING;
                    return discipline;
                default:
                    System.out.println("Vælg discipling eller tryk 'q' for at gå tilbage");
            }
        }
    }
}

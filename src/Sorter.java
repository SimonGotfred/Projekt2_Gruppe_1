import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Sorter {
    static ArrayList<Member> membersSort = MemberRegister.getMembers();

    //Returns a list containing competitors
    static ArrayList<Member> competitors() {
        ArrayList<Member> competitors = new ArrayList<>();

        //Runs through the member list - adds competitors to the list isCompetitor
        for (Member member : Sorter.membersSort) {
            if (member.isCompetitor()){
                competitors.add(member);
            }
        } return competitors;
    }

    //Returns a list of competitors of a desired discipline
    static ArrayList<Member> disciplineSorter (Discipline discipline){
        ArrayList<Member> disciplineSorted = new ArrayList<>();

        //Runs through competitors - runs through their disciplines
        for (Member e : Sorter.competitors()){
            for (Discipline d: e.disciplines()) {

                //If they have desired discipline, add to list disciplineSorted
                if (d.equals(discipline)){
                disciplineSorted.add(e);
                }
            }
        } return disciplineSorted;
    } // TODO: Use doesDiscipline

    //Prints top five of a desired disciplines
    static void topFive (Discipline discipline) {

        //Create list of competitors who competes in desired discipline
        ArrayList<Member> fullList = disciplineSorter(discipline);
        ArrayList<Performance> performances = new ArrayList<>();
        ArrayList<Member> topFive = new ArrayList<>();

        //HashMap with performance as key and member as value
        HashMap<Performance, Member> memberHashMap = new HashMap<>();

        //Best performance of each member is added to list performances and sorts them
        for (Member e: fullList){
            performances.add(e.getBestPerformance(discipline));
        }
        performances.sort(null);

        //Add all members to memberHashMap with their best performance as key
        for (Member e : fullList){
            memberHashMap.put(e.getBestPerformance(discipline), e);
        }

        //Add best performing members to top five - if less than five compete, only add them
        int printNr = Math.min(5, performances.size());
        for (int i = 0; i < printNr; i++) {
            topFive.add(memberHashMap.get(performances.get(i)));
        }

        //Utilize UI.println to format print with equal spaces
        ArrayList<String> toPrint = new ArrayList<>();
            for (Member member : topFive) {
                toPrint.add(member.toString("n\tb\t" + member.getBestPerformance(discipline)));
            }
            UI.println(" \t\t ", toPrint.toArray(new String[0]));
    }

    //Allows user to search for a member - returns a list of members who fulfill search criteria
    static ArrayList<Member> searchMember(ArrayList<Member> toSearch) throws AbortToMenuCommand
    {
        String inputSearchMember;
        ArrayList<Member> containsMember = new ArrayList<>();
        LocalDate checkIfDate = null;

        //Used for switch
        int outcome = -1;

        while (true) {
            //UI.inquire instead of scanner
                inputSearchMember = UI.inquire("Søg på navn, fødselsdag (d M yyyy) eller telefonnummer:\n");

                //Check if search is empty
            if (inputSearchMember.isEmpty()){
                outcome = 0;
            }//Check if input is a phone number
            else if (Member.isPhoneNumber(inputSearchMember)) {
                outcome = 1;
            }//Check if input is a name
            else  {
                outcome = 2;
            }

                //Check if input is a date
                try {checkIfDate = LocalDate.parse(inputSearchMember,MemberRegister.dateTimeFormatter);
            } catch (DateTimeParseException _){}
            if (checkIfDate!=null){
                outcome = 3;
            }

            //Switch returns based on the outcome int
            switch (outcome){
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
                    System.out.println("\n\n\nUgyldigt input\n\n");
            }
        }
    }

    //Allows user to choose a member from a given list - returns member
    static Member chooseMember(ArrayList<Member> chooseFrom) throws AbortToMenuCommand
    {
        int choice = 0;
        String choiceSwitch;
        boolean choose = true;
        Member chosenMember = null;

        //There are three relevant cases for a list - empty, one member, multiple members
        switch (chooseFrom.size()){
            case 0:
                //Case 0 (empty) is super rare - throwing AbortToMenuCommand will lead the user to a known place
                System.out.println("Ingen medlemmer opfylder kriteriet.\n");
                throw new AbortToMenuCommand();
            case 1:
                //Case 1 automaticcaly returns the member
                chosenMember = chooseFrom.getFirst();
                System.out.println(chosenMember);
                return chosenMember;
            default:
                //Print the list of members with an assigned value to each - allows easy choosing
                ArrayList<String> toPrint = new ArrayList<>();
                for (int i = 0; i < chooseFrom.size(); i++) {
                    toPrint.add("Tryk " + (i + 1) + ":\t" + chooseFrom.get(i).toString("n\tb\tp"));
                }

                while (choose) {
                    //Format to make equal space
                    UI.println(" \t \t \t ",toPrint.toArray(new String[0]));
                try {
                    //User chooses - check if it is a number
                    choice = Integer.parseInt(UI.inquire()) - 1;

                    //Check if user choice is a number on the list
                    if (-1 < choice && choice < chooseFrom.size()){
                        choose = false;
                    } else {System.out.println("FEJL: Vælg fra listen\n");}
                }

                //If input is not a number
                catch (NumberFormatException e){
                    System.out.println("FEJL: Vælg fra listen\n");
                }
            }

                System.out.println("Bekræft valg af medlem:\n\n" + chooseFrom.get(choice));
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
        String answer;

        while (true){
        answer = UI.inquire("Vælg disciplin:" +
                "\n1:\tFrisvømning\t\t\t"+"4:\tButterfly\t\t"+"7:\tVandpolo" +
                "\n2:\tRygsvømning\t\t\t"+"5:\tMedley\t\t\t"+"8:\tUndervandsrugby" +
                "\n3:\tBrystsvømning\t\t"+"6:\tUdspring\t\t"+"9:\tSynkronsvømning\n");

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
                    System.out.println("\n\nUgyldigt input\n");

            }
        }
    }
}

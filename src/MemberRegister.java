import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberRegister {
    static private ArrayList<Member> members = new ArrayList<>(){};
    static Scanner scanner = new Scanner(System.in);
    static String format = "d M yyyy";
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    static boolean pressedQ;
    //main just for testing
    public static void main(String[] args) throws AbortToMenuCommand
    {
        addMemberMenu();
    }
    //gets called from Menu, asks for name, tlfnr, birthdate, if it's active and competitive
    //makes a new member and gives it all the variables
    static void addMemberMenu() throws AbortToMenuCommand
    {

            //System.out.println("Du kan altid skrive 'q' for at gå tilbage til menuen");
            String name = "";
            String phoneNumber;
            LocalDate birthday;
            boolean isActive;
            boolean isCompeting = false;
            pressedQ = false;
            while (!Member.isName(name)) {
                name = UI.inquire("Skriv Medlemmets Navn og Efternavn: ");
                if (!Member.isName(name)){
                    System.out.println("\n\nUgyldigt input\n");
                }
            }
            phoneNumber = checkPhoneNr();
            birthday = checkIfDate();
            Member m ;
            isActive = checkYesOrNo("Er Medlemmet Aktivt? (ja/nej): ");
            if(isActive){
                isCompeting = checkYesOrNo("Er Medlemmet En Konkurrencesvømmer? (ja/nej): ");
                if (isCompeting)
                    m = Member.newCompetitor(name, birthday, phoneNumber);
                else
                    m = Member.newActive(name, birthday, phoneNumber);
            }
            else{
                m = Member.newPassive(name, birthday, phoneNumber);
            }

            System.out.println(name + ", " + birthday + ", er aktiv: " + isActive + ", " + ", er konkurrenceSvømmer: " + isCompeting);

            addMember(m);
    }
    //gets called from addMemberMenu, is used when you want either a yes or a no from the user
    //checks if the input is either "ja" or "nej" and returns a bool corresponding to the answer
    static boolean checkYesOrNo(String text) throws AbortToMenuCommand
    {
        while (true) {
            String activity = UI.inquire(text).toLowerCase();
            if (activity.equals("ja"))
                return true;
            else if (activity.equals("nej"))
                return  false;
            System.out.println("forkert input, prøv igen");
        }
    }
    //gets called from addMemberMenu, is used when you want a localDate from the user
    //tries to parse the string to a localDate, if it can't do that it says try again
    //it uses a specific format which is (d M yyyy)
    static LocalDate checkIfDate() throws AbortToMenuCommand
    {
        while (true){
            try {
                String birthD = UI.inquire("Skriv Medlemmets fødselsdag (d M yyyy): ");

                return LocalDate.parse(birthD, dateTimeFormatter);
            }
            catch (DateTimeParseException e){
                System.out.println("forkert input, prøv igen: ");
            }
        }
    }
    //gets called from addMemberMenu, is used when you want a phoneNumber from the user
    //tries to parse the string to an int, and checks if it is 8 characters long
    //also checks if the phoneNumber is already used by a member in the list
    static String checkPhoneNr() throws AbortToMenuCommand
    {
        while (true){
            try {
                String numberString = UI.inquire("Skriv Medlemmets Tlf Nr.: ");
                int number = Integer.parseInt(numberString);
                boolean nAlreadyExist = false;
                for (int i = 0; i < members.size(); i++){
                    if (numberString.equals(members.get(i).getPhoneNumber()))
                        nAlreadyExist = true;
                }
                if (numberString.length() == 8 && !nAlreadyExist){
                    return numberString;
                }
                if (nAlreadyExist)
                    System.out.println("der er allerede et medlem under dette telefon nummer");
                else
                    System.out.println("ikke et rigtigt telefon nummer, prøv igen: ");
            }
            catch (NumberFormatException e){
                    System.out.println("ikke et rigtigt telefon nummer, prøv igen: ");
            }
        }
    }
    //all of these are so we could make members list private
    //also makes it easier with for example addMember where it calls saveData itself
    static public ArrayList<Member> getMembers(){
        return members;
    }
    static public Member getMember(int i){
        return members.get(i);
    }
    static public void setMember(Member m, Member newM){
        members.set(members.indexOf(m), newM);
        SaveData.saveData();
    }
    static public void addMember(Member m){
        members.add(m);
        SaveData.saveData();
    }
    static public void removeMember(Member m){
        members.remove(m);
        SaveData.saveData();
    }
}

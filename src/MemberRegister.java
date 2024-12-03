import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MemberRegister {
    static ArrayList<Member> members = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static String format = "d M yyyy";
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    static boolean pressedQ;
    public static void main(String[] args) {
        addMemberMenu();
    }
    static void addMemberMenu(){
        try {
            System.out.println("Du kan altid skrive 'q' for at gå tilbage til menuen");
            String name;
            String tlfNr;
            LocalDate birthday;
            boolean isActive;
            String disciplin = "";
            boolean isCompeting = false;
            pressedQ = false;
             name = UI.inquire("Skriv Medlemmets Navn: ");
            //System.out.println("Skriv Medlemmets Navn: ");
            //name = scanner.nextLine();
            //System.out.println("Skriv Medlemmets Tlf Nr.: ");
            tlfNr = checkTlfNr();
           // System.out.println("Skriv Medlemmets fødselsdag: ");
            birthday = checkIfDate();
            //System.out.println("Er Medlemmet Aktivt? (ja/nej): ");
            isActive = checkYesOrNo("Er Medlemmet Aktivt? (ja/nej): ");
            if(isActive){
               // System.out.println("Hvad Er Medlemmets Disciplin?: ");
                disciplin = UI.inquire("Hvad Er Medlemmets Disciplin?: ");
                //System.out.println("Er Medlemmet En Konkurrencesvømmer? (ja/nej): ");
                isCompeting = checkYesOrNo("Er Medlemmet En Konkurrencesvømmer? (ja/nej): ");
            }
            System.out.println(name + ", " + birthday + ", er aktiv: " + isActive + ", " + disciplin + ", er konkurrenceSvømmer: " + isCompeting);
            members.add(new Member(name, birthday, tlfNr));
            SaveData.saveData();
        }
        catch (ExitMenuCommand e){

        }

    }
    static boolean checkYesOrNo(String text) throws ExitMenuCommand{
        while (true) {
            String activity = UI.inquire(text).toLowerCase();
            if (activity.equals("ja"))
                return true;
            else if (activity.equals("nej"))
                return  false;
            System.out.println("forkert input, prøv igen");
        }
    }
    static LocalDate checkIfDate() throws ExitMenuCommand{
        while (true){
            try {
                String birthD = UI.inquire("Skriv Medlemmets fødselsdag: ");

                return LocalDate.parse(birthD, dateTimeFormatter);
            }
            catch (DateTimeParseException e){
                System.out.println("forkert input, prøv igen: ");
            }
        }
    }
    static String checkTlfNr() throws ExitMenuCommand{
        while (true){
            try {
               // int number = scanner.nextInt();
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
                    scanner.nextLine();
            }
        }
    }
}

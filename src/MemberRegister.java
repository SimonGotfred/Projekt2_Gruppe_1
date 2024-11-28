import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MemberRegister {
    static ArrayList<Member> members = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static String format = "dd MM yyyy";
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

    public static void main(String[] args) {
        addMemberMenu();
    }
    static void addMemberMenu(){
        String name;
        String tlfNr;
        LocalDate birthday;
        boolean isActive;
        String disciplin = "";
        boolean isCompeting = false;
        System.out.println("Skriv Medlemmets Navn: ");
        name = scanner.nextLine();
        System.out.println("Skriv Medlemmets Tlf Nr.: ");
        tlfNr = checkTlfNr();
        System.out.println("Skriv Medlemmets fødselsdag: ");
        birthday = checkIfDate();
        System.out.println("Er Medlemmet Aktivt? (ja/nej): ");
        isActive = checkYesOrNo();
        if(isActive){
            System.out.println("Hvad Er Medlemmets Disciplin?: ");
            disciplin = scanner.nextLine();
            System.out.println("Er Medlemmet En Konkurrencesvømmer? (ja/nej): ");
            isCompeting = checkYesOrNo();
        }
        System.out.println(name + ", " + birthday + ", er aktiv: " + isActive + ", " + disciplin + ", er konkurrenceSvømmer: " + isCompeting);
        members.add(new Member(name, birthday, tlfNr));
        SaveData.saveData();
    }
    static boolean checkYesOrNo(){
        while (true) {
            String activity = scanner.nextLine().toLowerCase();
            if (activity.equals("ja"))
                return true;
            else if (activity.equals("nej"))
                return  false;
            System.out.println("forkert input, prøv igen");
        }
    }
    static LocalDate checkIfDate(){
        while (true){
            try {
                String birthD = scanner.nextLine();

                return LocalDate.parse(birthD, dateTimeFormatter);
            }
            catch (DateTimeParseException e){
                System.out.println("forkert input, prøv igen: ");
            }
        }
    }
    static String checkTlfNr(){
        while (true){
            try {
                int number = scanner.nextInt();
                String numberString = "" + number;
                scanner.nextLine();
                boolean nAlreadyExist = false;
                for (int i = 0; i < members.size(); i++){
                    if (numberString.equals(members.get(i).getPhoneNumber()))
                        nAlreadyExist = true;
                }
                if (("" + number).length() == 8 && !nAlreadyExist){
                    return numberString;
                }
                if (nAlreadyExist)
                    System.out.println("der er allerede et medlem under dette telefon nummer");
                else
                    System.out.println("ikke et rigtigt telefon nummer, prøv igen: ");
            }
            catch (InputMismatchException e){
                System.out.println("ikke et rigtigt telefon nummer, prøv igen: ");
                scanner.nextLine();
            }
        }
    }
}

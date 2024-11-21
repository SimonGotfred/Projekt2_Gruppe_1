import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MemberRegister {
    static ArrayList<Medlem> members = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        addMemberMenu();
    }
    static void addMemberMenu(){
        String name;
        int age;
        boolean isActive;
        String disciplin = "";
        boolean isCompeting = false;
        System.out.println("Skriv Medlemmets Navn: ");
        name = sc.nextLine();
        System.out.println("Skriv Medlemmets Alder: ");
        age = checkIfInt();
        System.out.println("Er Medlemmet Aktivt?: ");
        isActive = checkYesOrNo();
        if(isActive){
            System.out.println("Hvad Er Medlemmets Disciplin?: ");
            disciplin = sc.nextLine();
            System.out.println("Er Medlemmet En Konkurrencesvømmer?: ");
            isCompeting = checkYesOrNo();
        }
        System.out.println(name + ", " + age + ", er aktiv: " + isActive + ", " + disciplin + ", er konkurrenceSvømmer: " + isCompeting);
        members.add(new Medlem());
    }
    static boolean checkYesOrNo(){
        while (true) {
            String activity = sc.nextLine();
            if (activity.equals("ja"))
                return true;
            else if (activity.equals("nej"))
                return  false;
            System.out.println("forkert input, prøv igen");
        }
    }
    static int checkIfInt(){
        while (true){
            try {
                int i = sc.nextInt();
                sc.nextLine();
                return i;
            }
            catch (InputMismatchException e){
                System.out.println("forkert input, prøv igen int");
                sc.nextLine();
            }
        }
    }
}

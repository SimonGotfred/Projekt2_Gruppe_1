import java.time.LocalDate;
import java.util.Scanner;

public class PayMenu {
    public static void main(String[] args) { // testing
        MemberRegister.members.add(new Member("John Doe", LocalDate.of(2010,10,10),"12121212"));
        MemberRegister.members.add(new Member("Ida Doe", LocalDate.of(2010,10,10),"12121212"));
        MemberRegister.members.add(new Member("Carl Doe", LocalDate.of(2010,10,10),"12121212"));

        MemberRegister.members.getFirst().pay(600);

        paymentMenu();


    }

    public static void paymentMenu(){
        System.out.println("Velkommen til Payment menuen");
        for (Member member : MemberRegister.members){
            System.out.println(member.name + " " + paymentStatus(member));
        }
    }

    public static String paymentStatus(Member member){
        if (member.hasPaid()){
            return "Har betalt";
        }
        else{
            return "Har ikke betalt. " + "Personen skylder " + member.paymentOwed() + " Kr";
        }
    }

    public static boolean makePayment(){
        System.out.println("\nSkriv navnet på personen, der skal betale regningen");
        Member member = null;
        while (member == null ){
            String name = MemberRegister.scanner.nextLine().toLowerCase();

            for (Member m : MemberRegister.members){
                if (m.name.toLowerCase().equals(name)){
                    member=m;
                    break;
                }
            }

            if (member != null){
                member.pay(member.paymentOwed());
                System.out.println("Nu har du betalt");
                return true;
            }
            System.out.println("Du har tastet forkert, prøv igen");
        }
        return true;
    }
}
// sæt en while lykke der omfatter blokken
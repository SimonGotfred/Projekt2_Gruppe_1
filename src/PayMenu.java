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
            System.out.println(member.getName() + " " + paymentStatus(member));
        }
    }

    public static String paymentStatus(Member member){                          // Tjekker om medlemmet har betalt og returnerer status
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
        while (member == null ){                                                 // Fortsætter indtil et gyldigt medlem er fundet
            String name = UI.input.nextLine().toLowerCase();      // Læser brugerens input

            if (name.toLowerCase().equals("q")){                                // hvis brugen taster q, så vender man tilbage til hovedmenuen - toLowerCase betyder udanset om man taster stort Q eller lille q
                return false;
            }

            for (Member m : MemberRegister.members){                             // Søger i medlemslisten efter et matchende navn
                if (m.getName().toLowerCase().equals(name)){
                    member=m;                                                    // Finder medlemmet og stopper søgningen
                    break;
                }
            }

            if (member != null){
                member.pay(member.paymentOwed());                               // Når medlemmet er fundet, foretages betalingen
                System.out.println("Nu har du betalt");
                return true;                                                    // Betaling er gennemført
            }
            System.out.println("Du har tastet forkert, prøv igen");             // Hvis ingen match findes, beder systemet om at prøve igen
        }
        return true;
    }
}

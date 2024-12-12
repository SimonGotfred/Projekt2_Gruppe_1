import java.time.LocalDate;
import java.util.Scanner;

public class PayMenu {
    public static void main(String[] args) { // testing
        TestingSuite.populateMembers();

        MemberRegister.getMembers().getFirst().pay(600);

        paymentMenu();


    }

    public static void paymentMenu(){
        System.out.println("Velkommen til Payment menuen");
        for (Member member : MemberRegister.getMembers()){
            System.out.println(member.getName() + " " + paymentStatus(member));
        }
    }

    public static String paymentStatus (Member member){                          // Tjekker om medlemmet har betalt og returnerer status
        if (member.hasPaid()){
            return "Har betalt";
        }
        else{
            return "Har ikke betalt. " + "Personen skylder " + member.paymentOwed() + " Kr";
        }
    }

    public static void makePayment() throws AbortToMenuCommand {
        System.out.println("\nSkriv navnet på personen, der skal betale regningen");
        Member member = Sorter.chooseMember(Sorter.searchMember(MemberRegister.getMembers()));

            if (member != null){
                member.pay(member.paymentOwed());                               // Når medlemmet er fundet, foretages betalingen
                System.out.println("Nu har du betalt");                                                  // Betaling er gennemført
           }
    }
}

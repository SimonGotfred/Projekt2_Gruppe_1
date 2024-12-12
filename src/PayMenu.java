import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class PayMenu {
    public static void main(String[] args) { // testing
        TestingSuite.populateMembers();

        MemberRegister.getMembers().getFirst().pay(600);

        paymentMenu();
    }

    public static void paymentMenu(){
        System.out.println("Velkommen til Payment menuen");

        ArrayList<String> toPrint = new ArrayList<>();
        for (Member member : MemberRegister.getMembers()){
            if (!member.hasPaid()) {
                toPrint.add(member.toString("n\tp") + (paymentStatus(member)));    //Assign a value to each
            }
        }
        UI.println(" \t ",toPrint.toArray(new String[0]));                          //Format to make equal space
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
        ArrayList<Member> hasNotPaid = new ArrayList<>();
        for (Member member : MemberRegister.getMembers()){
            if (!member.hasPaid()) {
                hasNotPaid.add(member);    //Assign a value to each
            }
        }

        Member member = Sorter.chooseMember(Sorter.searchMember(hasNotPaid));

            if (member != null){
                member.pay(member.paymentOwed());                               // Når medlemmet er fundet, foretages betalingen
                System.out.println("Nu har du betalt");                                                  // Betaling er gennemført
           }
    }
}

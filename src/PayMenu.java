import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class PayMenu {
    public static void main(String[] args) {                                                    // testing method
        TestingSuite.populateMembers();                                                         // Populates the member register with test data

        MemberRegister.getMembers().getFirst().pay(600);                                        // Makes the first member in the register pay 600 currency units

        paymentMenu();                                                                          // Calls the payment menu to display unpaid members
    }

    public static void paymentMenu(){                                                           // Menu for displaying payment information
        ArrayList<String> toPrint = new ArrayList<>();                                          // List for storing payment info strings
        for (Member member : MemberRegister.getMembers()){                                      // Loop through all members in the register
            if (!member.hasPaid()) {                                                            // If the member hasn't paid
                toPrint.add(member.toString("n\tp") + (paymentStatus(member)));                 //Assign a value to each
            }
        }

        UI.println(" \t ",toPrint.toArray(new String[0]));                                      // Print the list of unpaid members in a formatted manner
        System.out.println("\nDer er "+toPrint.size()+" medlemmer i restance.\n");
    }

    public static String paymentStatus (Member member){                                         // Method to check payment status of a member and return their status as a string
        if (member.hasPaid()){                                                                  // If the member has already paid
            return "Har betalt";                                                                // Return status "Has paid"
        }
        else{
            return "Har ikke betalt. " + "Personen skylder " + member.paymentOwed() + " Kr";    // Return status including how much they owe
        }
    }

    public static void makePayment() throws AbortToMenuCommand {                                // Method to allow a member to make a payment

        Member member = Sorter.chooseMember(Sorter.searchMember(MemberRegister.getMembers()));                   // Allow the user to select a member from the unpaid members list

            if (member != null){                                                                // If a member was selected
                member.pay(member.paymentOwed());                                               // Pay the total amount owed by the member
                System.out.println("Betaling registreret");                                         // Confirm the payment was made
           }
    }
}

public class EditMember {

    public static void edit() throws AbortToMenuCommand                                                         // Method to edit a member from the member register
    {
        Member member = Sorter.chooseMember(Sorter.searchMember(MemberRegister.getMembers()));                  // Retrieve the member list and let the user select a member to edit
        System.out.println(member);                                                                             // Print information about the selected member
       while (true){
           System.out.println("Tryk 1: Ret navn");                                                                // Infinite loop to handle user choices until editing is finished
           System.out.println("Tryk 2: Ret telefon nummer");
           System.out.println("Tryk 3: Gør til motionist");
           System.out.println("Tryk 4: Gør til passivt medlem");
           System.out.println("Tryk 5: Gør til konkurrencemedlem");
           System.out.println("Tryk 6: Slet medlem");

           String input = UI.inquire();                                                                        // Get input from the user
           try {                                                                                               // Handle user choice
                 switch (input.toLowerCase()){                                                        // Change status to competitive member
                     case "1", "navn" , "n" : editName(member); break;                                                 // Check input, making it case-insensitive
                     case "2", "tlf", "telefon nummer": editPhoneNumber(member); break;                        // Call method to edit the phone number
                     case "3", "aktiv", "a": member.setActive(); break;                                        // Change status to active
                     case "4", "passiv", "p": member.setPassive(); break;                     // Remove the member from the list
                     case "5", "konkurrence", "k" : member.setCompetitor(); break;                                             // Change status to passive
                     case "6", "slet", "delete", "s" : MemberRegister.getMembers().remove(member); break;        // Call method to edit the name
                        default:
                   System.out.println("Ugyldigt valg, prøv igen.");                                            // If input is invalid, provide feedback to the user
                }
           } catch (AbortToMenuCommand e) {}                                                                   // Catch exception if the user wants to interrupt and return to the menu
       }
    }
    public static void editPhoneNumber(Member member) throws AbortToMenuCommand                                // Method to change a member's phone number
    {
        String input = UI.inquire("Hvad er det nye telefon nummer du gerne vil redigere?");                    // Ask the user for the new phone number
        if (Member.isPhoneNumber(input)){                                                                      // Check if the entered phone number has the correct format
            member.setPhoneNumber(input);                                                                      // Update the phone number if the format is valid
        }
        else {
            System.out.println("Forkert formateret, prøv igen");                                               // If the format is invalid, provide feedback and call the method again
            editPhoneNumber(member);
        }
    }
    public static void editName(Member member) throws AbortToMenuCommand                                        // Method to change a member's name
    {
        String input = UI.inquire("Hvad er navnet du vil ændre til?");                                          // Ask the user for the new name
        if (Member.isName(input)){                                                                              // Check if the entered name has the correct format
            member.setName(input);                                                                              // Update the name if the format is valid
        }
        else{
            System.out.println("Forkert formateret, prøv igen");                                                // If the format is invalid, provide feedback and call the method again
            editName(member);
        }
    }
}

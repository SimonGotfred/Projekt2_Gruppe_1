public class EditMember {

    public static void edit() throws AbortToMenuCommand
    {
        Member member = Sorter.chooseMember(Sorter.searchMember(MemberRegister.getMembers()));                  // Vælg et medlem fra medlemsregisteret vha. Sorter.chooseMember
        System.out.println(member);                                                                             // Udskriv det valgte medlems informationer
       while (true){
           System.out.println("Tryk 1: ændring af telefon nummer");
           System.out.println("Tryk 2: ændre til aktivstatus");
           System.out.println("Tryk 3: ændre til passivstatus");
           System.out.println("Tryk 4: slet medlemmet");
           System.out.println("Tryk 5: ændre til konkurrencemedlem");
           System.out.println("Tryk 6: ændring af navn");

           String input = UI.inquire();                                                                        // Spørg brugeren, hvad de vil redigere: telefonnummer eller status
           try {

                 switch (input.toLowerCase()){
                     case "1", "tlf", "telefon nummer": editPhoneNumber(member); break;                        // Kald metoden til at redigere telefonnummeret
                     case "2", "aktiv", "a": member.setActive(); break;
                     case "3", "passiv", "p": member.setPassive(); break;
                     case "4", "slet", "delete", "s" : MemberRegister.getMembers().remove(member); break;
                     case "5", "konkurrence", "k" : member.setCompetitor(); break;
                     case "6", "navn" , "n" : editName(member); break;
                        default:
                   System.out.println("Ugyldigt valg, prøv igen.");                                     // Håndterer ugyldige indtastninger
                }
           } catch (AbortToMenuCommand e) {}
       }
    }
    public static void editPhoneNumber(Member member) throws AbortToMenuCommand
    {                                            // Metode til at redigere et medlems telefonnummer
        String input = UI.inquire("Hvad er det nye telefon nummer du gerne vil redigere?");         // Spørg brugeren om det nye telefonnummer
        if (Member.isPhoneNumber(input)){                                                           // Tjek, om telefonnummeret er korrekt formateret
            member.setPhoneNumber(input);                                                           // Opdater medlemmets telefonnummer
        }
        else {
            System.out.println("Forkert formateret, prøv igen");                                    // Hvis formateringen er forkert, informer brugeren og prøv igen
            editPhoneNumber(member);
        }
    }
    public static void editName(Member member) throws AbortToMenuCommand
    {
        String input = UI.inquire("Hvad er navnet du vil ændre til?");
        if (Member.isName(input)){
            member.setName(input);
        }
        else{
            System.out.println("Forkert formateret, prøv igen");
            editName(member);
        }
    }
}

public class EditMember {

    public static void edit() throws AbortToMenuCommand
    {
        Member member = Sorter.chooseMember(Sorter.searchMember(MemberRegister.getMembers()));                                 // Vælg et medlem fra medlemsregisteret vha. Sorter.chooseMember
        System.out.println(member);                                                                  // Udskriv det valgte medlems informationer
       while (true){
           System.out.println("Tryk 1: ændring af telefon nummer");
           System.out.println("Tryk 2: aktivstatus");
           System.out.println("Tryk 3: passivstatus");
           System.out.println("Tryk 4: slette");
           System.out.println("Tryk 5: konkurrence");
           System.out.println("Tryk 6: ændring af navn");

           String input = UI.inquire("Vil du redigere telefonnummeret eller om de er aktiv/passiv");    // Spørg brugeren, hvad de vil redigere: telefonnummer eller status
           try {

                 switch (input.toLowerCase()){                                                                // Brug en switch-case til at håndtere brugerens valg
                 case "telefon nummer", "tlf", "1" : editTelefonnummer(member); break;                         // Kald metoden til at redigere telefonnummeret
                 case "aktiv", "a": member.setActive(); break;
                 case "passiv", "p": member.setPassive(); break;
                 case "slet", "delete", "s" : MemberRegister.getMembers().remove(member); break;
                 case "konkurrence", "k" : member.setCompetitor(); break;
                 case "navn" , "n" : editname(member); break;
                 default:
                   System.out.println("Ugyldigt valg, prøv igen.");                                     // Håndterer ugyldige indtastninger
                }
           } catch (AbortToMenuCommand e) {}
       }
    }
    public static void editTelefonnummer(Member member) throws AbortToMenuCommand
    {                                            // Metode til at redigere et medlems telefonnummer
        String input = UI.inquire("Hvad er det nye telefon nummer du gerne vil redigere?");         // Spørg brugeren om det nye telefonnummer
        if (Member.isPhoneNumber(input)){                                                           // Tjek, om telefonnummeret er korrekt formateret
            member.setPhoneNumber(input);                                                           // Opdater medlemmets telefonnummer
        }
        else {
            System.out.println("Forkert formateret, prøv igen");                                    // Hvis formateringen er forkert, informer brugeren og prøv igen
            editTelefonnummer(member);
        }
    }
    public static void editname(Member member) throws AbortToMenuCommand
    {
        String input = UI.inquire("Hvad er navnet du vil ændre til?");
        if (Member.isName(input)){
            member.setName(input);
        }
        else{
            System.out.println("Forkert formateret, prøv igen");
            editname(member);
        }
    }
}

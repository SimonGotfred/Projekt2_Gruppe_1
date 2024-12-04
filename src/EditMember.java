public class EditMember {

    public static void edit(){
        Member member = Sorter.chooseMember(MemberRegister.members);                                 // Vælg et medlem fra medlemsregisteret vha. Sorter.chooseMember
        System.out.println(member);                                                                  // Udskriv det valgte medlems informationer
       while (true){
           System.out.println("Her kan du ændre på telefonnummer, om medlem er aktiv eller passiv eller slette");
           String input = UI.inquire("Vil du redigere telefonnummeret eller om de er aktiv/passiv");    // Spørg brugeren, hvad de vil redigere: telefonnummer eller status
           switch (input.toLowerCase()){                                                                // Brug en switch-case til at håndtere brugerens valg
               case "telefon nummer", "tlf" : editTelefonnummer(member); break;                         // Kald metoden til at redigere telefonnummeret
               case "aktiv": member.setActive(); break;
               case "passiv": member.isPassive(); break;
               case "slet", "delete" : MemberRegister.members.remove(member); break;
               default:
                   System.out.println("Ugyldigt valg, prøv igen.");                                     // Håndterer ugyldige indtastninger
           }
       }
    }
    public static void editTelefonnummer(Member member){                                            // Metode til at redigere et medlems telefonnummer
        String input = UI.inquire("Hvad er det nye telefon nummer du gerne vil redigere?");         // Spørg brugeren om det nye telefonnummer
        if (Member.isPhoneNumber(input)){                                                           // Tjek, om telefonnummeret er korrekt formateret
            member.setPhoneNumber(input);                                                           // Opdater medlemmets telefonnummer
        }
        else {
            System.out.println("Forkert formateret, prøv igen");                                    // Hvis formateringen er forkert, informer brugeren og prøv igen
            editTelefonnummer(member);
        }
    }
}

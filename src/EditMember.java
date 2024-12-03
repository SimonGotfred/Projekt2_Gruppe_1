public class EditMember {

    public static void edit(){
        Member member = Sorter.chooseMember(MemberRegister.members);
        System.out.println(member);
        String input = UI.inquire("Vil du redigere telefonnummeret eller om de er aktiv/passiv");
        switch (input.toLowerCase()){
            case "telefon nummer", "tlf" : editTelefonnummer(member); break;
            case "aktiv": member.setActive(); break;
            case "passiv": member.isPassive(); break;
        }

    }
    public static void editTelefonnummer(Member member){
        String input = UI.inquire("Hvad er det nye telefon nummer du gerne vil redigere?");
        if (Member.isPhoneNumber(input)){
            member.setPhoneNumber(input);
        }
        else {
            System.out.println("Forkert formateret, prøv igen");
            editTelefonnummer(member);
        }
    }
}

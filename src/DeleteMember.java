import java.util.InputMismatchException;
import java.util.Locale;

public class DeleteMember {

    static void deleteMemberMenu(){
        System.out.println("Du kan altid skrive 'q' for at gå tilbage til menuen");
        System.out.println("Hvad er telefonNummeret af personen du vil afmelde?");
        checkTlfNr();
    }
    static void checkTlfNr(){
        while (true){
                String numberString = MemberRegister.scanner.nextLine();
                if (numberString.toLowerCase(Locale.ROOT).equals("q"))
                    return;
                for (int i = 0; i < MemberRegister.members.size(); i++){
                    if (numberString.equals(MemberRegister.members.get(i).getPhoneNumber())){
                        System.out.println("Medlemmet: "+ MemberRegister.members.get(i).getName() + "s abonnement er blevet annulleret");
                        MemberRegister.members.remove(MemberRegister.members.get(i));
                        return;
                    }
                }
                System.out.println("Telefon nummeret høre ikke til noget medlem, prøv igen:");
        }
    }
}

import java.util.InputMismatchException;
import java.util.Locale;

public class DeleteMember {

    static void deleteMemberMenu(){
        System.out.println("Du kan altid skrive 'q' for at gå tilbage til menuen");
        checkTlfNr();
        //delete member and save data
    }
    static void checkTlfNr(){
        try {
            while (true){
                String numberString = UI.inquire("Hvad er telefonNummeret af personen du vil afmelde?");
                for (int i = 0; i < MemberRegister.getMembers().size(); i++){
                    if (numberString.equals(MemberRegister.getMembers().get(i).getPhoneNumber())){
                        System.out.println("Medlemmet: "+ MemberRegister.getMembers().get(i).getName() + "s abonnement er blevet annulleret");
                        MemberRegister.getMembers().remove(MemberRegister.getMembers().get(i));
                        SaveData.saveData();
                        return;
                    }
                }
                System.out.println("Telefon nummeret høre ikke til noget medlem, prøv igen:");
            }
        }
        catch (ExitMenuCommand e){

        }

    }
}

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
                for (int i = 0; i < MemberRegister.members.size(); i++){
                    if (numberString.equals(MemberRegister.members.get(i).getPhoneNumber())){
                        System.out.println("Medlemmet: "+ MemberRegister.members.get(i).getName() + "s abonnement er blevet annulleret");
                        MemberRegister.members.remove(MemberRegister.members.get(i));
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

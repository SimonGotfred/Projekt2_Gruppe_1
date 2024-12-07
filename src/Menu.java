import java.util.Scanner;
public class Menu {
    static String welcome = "\n~Velkommen til Svømmeklubben Delfinen~" +
            "\n~~~~~~~~~ Hvor vandet er rent ~~~~~~~~~";

    static void start() throws ExitMenuCommand {
        SaveData.makeMembersFromData();
        System.out.println(welcome);
        boolean menu = true;
        while (menu) {
            menu = Menu.mainMenu();
        }
    }

    static boolean mainMenu() throws ExitMenuCommand {
        boolean subMenu = true;
            String options = "Tryk 1:\t\t\tTryk 2:\t\t\tTryk 3:" +
                    "\nForperson\t\tKasserer\t\tTræner";
            System.out.println("\n" + options);

            Scanner sc = new Scanner(System.in);
            String optionsAnswer = sc.next();

            switch (optionsAnswer) {
                case "1":
                    while (subMenu) {
                        subMenu = Menu.chairmanMenu();
                    }
                    break;
                case "2":
                    while (subMenu) {
                        subMenu = Menu.cashierMenu();
                    }
                    break;
                case "3":
                    while (subMenu) {
                        subMenu = Menu.coachMenu();
                    }
                    break;
                case "q":
                    return false;
            }
            return true;
        }

    static boolean chairmanMenu() throws ExitMenuCommand {
        String chairmanOptions =
                "Tryk 1: Opret ny medlem" +
                        "\nTryk 2: Rediger eksisterende medlem" +
                        "\nTryk 3: Slet medlem" +
                        "\n\nTryk q: Hovedmenu";
        System.out.println(chairmanOptions);
        Member referenceMember;

        Scanner sc = new Scanner(System.in);
        String chairmanAnswer = sc.next();

        switch (chairmanAnswer){
            case "1":
                MemberRegister.addMemberMenu();
                break;
            case "2":
                referenceMember = Sorter.chooseMember(Sorter.searchMember(MemberRegister.getMembers()));    //Search by name, choose member
                break;
            case "3":
                DeleteMember.deleteMemberMenu();
                break;
            case "q":
                return false;
        }   return true;
    }

    static boolean cashierMenu(){
        String cashierOptions =
                "Tryk 1: Se medlemmer i restance" +
                        "\nTryk 2: Registrer modtaget betaling" +
                        "\n\nTryk q: Hovedmenu";
        System.out.println(cashierOptions);

        Scanner sc = new Scanner(System.in);
        String cashierAnswer = sc.next();

        switch (cashierAnswer){
            case "1":
                PayMenu.paymentMenu();

                break;
            case "2":
                PayMenu.makePayment();

                break;
            case "q":
                return false;
        } return true;
    }

    static boolean coachMenu() throws ExitMenuCommand {
        String coachOptions =
                "Tryk 1: Se hold" +
                        "\nTryk 2: Se disciplin" +
                        "\nTryk 3: Se medlem" +
                        "\n\nTryk q: Hovedmenu";
        System.out.println(coachOptions);

        Scanner sc = new Scanner(System.in);
        String coachAnswer = sc.next();

        switch (coachAnswer){
            case "1":
                //Kør "se hold"
                System.out.println("HER SKAL TILFØJES NOGET: class Manu ->" +
                        " forpersonMenu -> switch(coachAnswer)-> case 1");
                break;
            case "2":
                //Kør "se disciplin"
                System.out.println("HER SKAL TILFØJES NOGET: class Manu ->" +
                        " forpersonMenu -> switch(coachAnswer)-> case 2");
                break;
            case "3":
                Sorter.chooseMember(Sorter.searchMember(MemberRegister.getMembers()));
                break;
            case "q":
                return false;
        } return true;
    }
}


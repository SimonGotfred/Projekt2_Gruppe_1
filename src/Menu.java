import java.util.Scanner;
public class Menu {
    static String welcome = "\n~Velkommen til Svømmeklubben Delfinen~" +
            "\n~~~~~~~~~ Hvor vandet er rent ~~~~~~~~~";

    static void start(){
        SaveData.makeMembersFromData();
        System.out.println(welcome);
        boolean menu = true;
        while (menu) {
            menu = Menu.mainMenu();
        }
    }

    static boolean mainMenu(){
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

    static boolean chairmanMenu(){
        String chairmanOptions =
                "Tryk 1: Opret ny medlem" +
                        "\nTryk 2: Rediger eksisterende medlem" +
                        "\nTryk 3: Slet medlem" +
                        "\n\nTryk q: Hovedmenu";
        System.out.println(chairmanOptions);

        Scanner sc = new Scanner(System.in);
        String chairmanAnswer = sc.next();

        switch (chairmanAnswer){
            case "1":
                MemberRegister.addMemberMenu();
                break;
            case "2":
                //Kør "rediger medlem"
                System.out.println("HER SKAL TILFØJES NOGET: class Menu ->" +
                        " forpersonMenu -> switch(chairmanAnswer)-> case 2");
                break;
            case "3":
                //Kør "slet medlem"
                System.out.println("HER SKAL TILFØJES NOGET: class Menu ->" +
                        " forpersonMenu -> switch(chairmanAnswer)-> case 3");
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

    static boolean coachMenu(){
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
                //Kør "se medlem"
                System.out.println("HER SKAL TILFØJES NOGET: class Manu ->" +
                        " forpersonMenu -> switch(coachAnswer)-> case 3");
                break;
            case "q":
                return false;
        } return true;
    }
}


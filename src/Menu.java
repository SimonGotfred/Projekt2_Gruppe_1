import java.util.ArrayList;
import java.util.Scanner;
public class Menu {
    static String welcome = "\n~Velkommen til Svømmeklubben Delfinen~" +
            "\n~~~~~~~~~ Hvor vandet er rent ~~~~~~~~~";

    static void start(){
        System.out.println(welcome);
        Menu.mainMenu();
    }

    static void mainMenu(){
        String options = "Tryk 1:\t\t\tTryk 2:\t\t\tTryk 3:" +
                "\nForperson\t\tKasserer\t\tTræner";
        System.out.println("\n" + options);

        Scanner sc = new Scanner(System.in);
        String optionsAnswer = sc.next();

        switch (optionsAnswer) {
            case "1":
                Menu.chairmanMenu();
                break;
            case "2":
                Menu.cashierMenu();
                break;
            case "3":
                Menu.coachMenu();
                break;
            default:
                Menu.mainMenu();
        }
    }

    static void chairmanMenu(){
        String chairmanOptions =
                "Tryk 1: Opret ny medlem" +
                        "\nTryk 2: Rediger eksisterende medlem" +
                        "\nTryk 3: Slet medlem" +
                        "\n\nTryk w: Hovedmenu";
        System.out.println(chairmanOptions);

        Scanner sc = new Scanner(System.in);
        String chairmanAnswer = sc.next();

        switch (chairmanAnswer){
            case "1":
                //Kør "opret nyt medlem"
                System.out.println("HER SKAL TILFØJES NOGET: class Menu ->" +
                        " forpersonMenu -> switch(chairmanAnswer)-> case 1");
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
            case "w":
                Menu.mainMenu();
            default:
                Menu.chairmanMenu();
        }
    }

    static void cashierMenu(){
        String cashierOptions =
                "Tryk 1: Se medlemmer i restance" +
                        "\nTryk 2: Registrer modtaget betaling" +
                        "\n\nTryk w: Hovedmenu";
        System.out.println(cashierOptions);

        Scanner sc = new Scanner(System.in);
        String cashierAnswer = sc.next();

        switch (cashierAnswer){
            case "1":
                //Kør "opret nyt medlem"
                System.out.println("HER SKAL TILFØJES NOGET: class Menu ->" +
                        " forpersonMenu -> switch(cashierAnswer)-> case 1");
                break;
            case "2":
                //Kør "rediger medlem"
                System.out.println("HER SKAL TILFØJES NOGET: class Menu ->" +
                        " forpersonMenu -> switch(cashierAnswer)-> case 2");
                break;
            case "w":
                Menu.mainMenu();
            default:
                Menu.cashierMenu();
        }
    }

    static void coachMenu(){
        String coachOptions =
                "Tryk 1: Se hold" +
                        "\nTryk 2: Se disciplin" +
                        "\nTryk 3: Se medlem" +
                        "\n\nTryk w: Hovedmenu";
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
            case "w":
                Menu.mainMenu();
            default:
                Menu.coachMenu();
        }
    }
}


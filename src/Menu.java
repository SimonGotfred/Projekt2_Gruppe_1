import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
public class Menu {
    static String welcome = "\n~Velkommen til Svømmeklubben Delfinen~" +
            "\n~~~~~~~~~ Hvor vandet er rent ~~~~~~~~~";

    static void start() throws ExitMenuCommand {
        SaveData.makeMembersFromData();
        System.out.println(welcome);

        while (true) {
            try
            {
                Menu.mainMenu();
            }
            catch (ExitMenuCommand _) {return;}
        }
    }

    static void mainMenu() throws ExitMenuCommand {
        String options = "Tryk 1:\t\t\tTryk 2:\t\t\tTryk 3:" +
                "\nForperson\t\tKasserer\t\tTræner";

        System.out.println("\n" + options);
        String optionsAnswer = UI.inquire(); // throws to exit program.

        try
        {
            while (true)
            {
                switch (optionsAnswer)
                {
                    case "1":
                        Menu.chairmanMenu();
                        break;
                    case "2":
                        Menu.cashierMenu();
                        break;
                    case "3":
                        Menu.coachMenu();
                        break;
                }
            }
        }
        catch (ExitMenuCommand _) {}
    }

    static void chairmanMenu() throws ExitMenuCommand {
        String chairmanOptions =
                "Tryk 1: Opret ny medlem" +
                        "\nTryk 2: Rediger eksisterende medlem" +
                        "\nTryk 3: Slet medlem" +
                        "\n\nTryk q: Hovedmenu";

        System.out.println(chairmanOptions);
        String chairmanAnswer = UI.inquire();

        try
        {
            switch (chairmanAnswer)
            {
                case "1":
                    MemberRegister.addMemberMenu();
                    break;
                case "2":
                    EditMember.edit();
                    break;
                case "3":
                    DeleteMember.deleteMemberMenu();
                    break;
            }
        }
        catch (ExitMenuCommand _) {}
    }

    static void cashierMenu() throws ExitMenuCommand {
        String cashierOptions =
                "Tryk 1: Se medlemmer i restance" +
                        "\nTryk 2: Registrer modtaget betaling" +
                        "\n\nTryk q: Hovedmenu";

        System.out.println(cashierOptions);
        String cashierAnswer = UI.inquire();

        // try
        // {
            switch (cashierAnswer)
            {
                case "1":
                    PayMenu.paymentMenu();
                    break;
                case "2":
                    PayMenu.makePayment();
                    break;
            }
        // }
        // catch (ExitMenuCommand _) {}
    }

    static void coachMenu() throws ExitMenuCommand {
        String coachOptions =
                "Tryk 1: Se hold" +
                        "\nTryk 2: Se disciplin" +
                        "\nTryk 3: Se medlem" +
                        "\n\nTryk q: Hovedmenu";

        System.out.println(coachOptions);
        String coachAnswer = UI.inquire();

        try
        {
            switch (coachAnswer)
            {
                case "1": // se hvilke svømmere i disciplin
                    System.out.println("HER SKAL TILFØJES NOGET: class Manu ->" +
                            " forpersonMenu -> switch(coachAnswer)-> case 1");
                    break;
                case "2":
                    coachViewDiscipline();
                    break;
                case "3": // se medlem og deres præstationer
                    coachViewMember();
                    break;
            }
        }
        catch (ExitMenuCommand _) {}
    }

    static void coachViewMember() throws ExitMenuCommand
    {
        Member member = Sorter.chooseMember(Sorter.searchMember(MemberRegister.getMembers()));
        Discipline[] disciplines = member.disciplines();
        String input;

        while (true)
        {
            System.out.println(member.toString("n a p"));

            if (disciplines.length == 0) {System.out.println("\tIngen præstationer noteret.");}
            for (Discipline discipline : disciplines)
            {
                System.out.println(member.getBestPerformance(discipline));
            }

            input = UI.inquire("\nTilføj præstation til " + member.getFirstName() + "? ja/nej");
            switch (input)
            {
                case "ja": try{member.addPerformance(new Performance());}catch(ExitMenuCommand _){} break;
                case "nej": return;
            }
        }
    }

    static void coachViewDiscipline() throws ExitMenuCommand {

        Discipline discipline;
        discipline = Sorter.chooseDiscipline();

        System.out.println(Sorter.disciplineSorter(discipline));
        String answer = UI.inquire("\n1: Se top 5");

        if (answer.equalsIgnoreCase("1")){
            Sorter.topFive(discipline);
        }
    }
}


import java.util.ArrayList;

public class Menu {
    static String welcome = "\n~Velkommen til Svømmeklubben Delfinen~" +
            "\n~~~~~~~~~ Hvor vandet er rent ~~~~~~~~~";

    static void start() throws AbortToMenuCommand {
        SaveData.makeMembersFromData();
        System.out.println(welcome);

        while (true) {
            try {
                Menu.mainMenu();
            } catch (AbortToMenuCommand _) {
                return;
            }
        }
    }

    static void mainMenu() throws AbortToMenuCommand {
        String options = "Tryk 1:\t\t\tTryk 2:\t\t\tTryk 3:" +
                "\nForperson\t\tKasserer\t\tTræner";

        System.out.println("\n" + options);
        String optionsAnswer = UI.inquire(); // throws to exit program.

        try {
            while (true) {
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
                }
            }
        } catch (AbortToMenuCommand _) {
        }
    }

    static void chairmanMenu() throws AbortToMenuCommand {
        String chairmanOptions =
                "Tryk 1: Opret ny medlem" +
                        "\nTryk 2: Rediger eksisterende medlem" +
                        "\n\nTryk q: Hovedmenu";

        System.out.println(chairmanOptions);
        String chairmanAnswer = UI.inquire();

        try {
            switch (chairmanAnswer) {
                case "1":
                    MemberRegister.addMemberMenu();
                    break;
                case "2":
                    EditMember.edit();
                    break;
            }
        } catch (AbortToMenuCommand _) {
        }
    }

    static void cashierMenu() throws AbortToMenuCommand {
        String cashierOptions =
                "Tryk 1: Se medlemmer i restance" +
                        "\nTryk 2: Registrer modtaget betaling" +
                        "\n\nTryk q: Hovedmenu";

        System.out.println(cashierOptions);
        String cashierAnswer = UI.inquire();

        // try
        // {
        switch (cashierAnswer) {
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

    static void coachMenu() throws AbortToMenuCommand {
        String coachOptions =
                "Tryk 1: Se disciplin" +
                        "\nTryk 2: Se medlem" +
                        "\n\nTryk q: Hovedmenu";

        System.out.println(coachOptions);
        String coachAnswer = UI.inquire();

        try {
            switch (coachAnswer) {
                case "1":
                    coachViewDiscipline();
                    break;
                case "2": // se medlem og deres præstationer
                    coachViewMember();
                    break;
            }
        } catch (AbortToMenuCommand _) {
        }
    }

    static void coachViewMember() throws AbortToMenuCommand {
        Member member = Sorter.chooseMember(Sorter.searchMember(Sorter.competitors()));
        Discipline[] disciplines = member.disciplines();
        String input;

        while (true) {
            //System.out.println(member.toString("n a p"));

            if (disciplines.length == 0) {
                System.out.println("\tIngen præstationer noteret.");
            }
            for (Discipline discipline : disciplines) {
                System.out.println(member.getBestPerformance(discipline));
            }

            input = UI.inquire("\nTilføj præstation til " + member.getFirstName() + "? ja/nej");
            switch (input) {
                case "ja":
                    try {
                        member.addPerformance(new Performance());
                    } catch (AbortToMenuCommand _) {
                    }
                    break;
                case "nej":
                    return;
            }
        }
    }

    static void coachViewDiscipline() throws AbortToMenuCommand {

        Discipline discipline;

        //Choose discipline
        discipline = Sorter.chooseDiscipline();

        //Make list of members of the disicpline
        ArrayList<Member> membersOfDiscipline = Sorter.disciplineSorter(discipline);

        //Format members toString
        ArrayList<String> toPrint = new ArrayList<>();
        for (Member member : membersOfDiscipline) {
            toPrint.add(member.toString("n\tb\tp"));
        }
        //Format the print so it is beautiful :)
        UI.println(" \t \t ", toPrint.toArray(new String[0]));

        String answer = UI.inquire("\n1: Se top 5");

        if (answer.equalsIgnoreCase("1")) {
            Sorter.topFive(discipline);
        }
    }
}
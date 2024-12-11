import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public interface UI
{
    Scanner input = new Scanner(System.in);
    String dateFormat     = "d M yyyy";
    String dateTimeFormat = "H:m d-MMM-yyyy";
    DateTimeFormatter dateFormatter     = DateTimeFormatter.ofPattern(dateFormat);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);

    static String inquire() throws AbortToMenuCommand {return inquire("");}
    static String inquire(String prompt) throws AbortToMenuCommand
    {
        System.out.println();
        if (!prompt.isEmpty()) System.out.print(prompt);
        System.out.print("\t");

        String string = input.nextLine().trim();

        if (string.equalsIgnoreCase("q")) throw new AbortToMenuCommand();

        return string;
    }

    static void printOptions(String option, String... options)
    {
        int i = 1;
        System.out.print("Tryk " + i + ": " + option);

        for (String string : options)
        {
            if (i%4 == 0)System.out.println("\n");
            i++;
            System.out.print("Tryk " + i + ": " + string);
        }

        System.out.println("\n\nTryk q: Tilbage til forrige menu\n");
    }

    static void println(String string, String... strings)
    {
        int tabs = string.length() - string.replace("\t", "").length();
        boolean equalTabs = strings.length > 0;

        for (String s : strings)
        {
            if (tabs != s.length() - s.replace("\t", "").length())
            {
                equalTabs = false; break;
            }
        }

        if (equalTabs)
        {
            String[][] container = new String[strings.length+1][];
            container[0] = string.split("\t");
            for (int i = 1; i < container.length; i++)
            {
                container[i] = strings[i-1].split("\t");
            }
            printTable(container);
            return;
        }

        System.out.println(string);
        for (String s : strings) System.out.println(s);
    }

    static void printTable(String[][] strings)
    {
        String[] out;
        for (int i = 0; i < strings[0].length; i++)
        {
            int width = 0;

            for (int j = 0; j < strings.length; j++)
            {
                if (strings[j][i].length() > width) width = strings[j][i].length();
            }
            width++;

            for (int j = 0; j < strings.length; j++)
            {
                //strings[j][i] = ' ' + strings[j][i];
                while (strings[j][i].length() <= width) strings[j][i] += ' ';
            }
        }
        out = new String[strings.length];
        for (int i = 0; i < out.length; i++)
        {
            out[i] = "";
            for (int j = 0; j < strings[0].length; j++) out[i] += strings[i][j]; // TODO: catch indexOutOfBounds
        }
        for (String s : out) System.out.println(s);
    }
}


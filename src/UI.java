import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public interface UI
{
    Scanner input = new Scanner(System.in);
    String dateFormat     = "d M yyyy";
    String dateTimeFormat = "H:m d-MMM-yyyy";
    DateTimeFormatter dateFormatter     = DateTimeFormatter.ofPattern(dateFormat);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);

    static String inquire() {return inquire("");}
    static String inquire(String prompt) throws ExitMenuCommand
    {
        System.out.println();
        if (!prompt.isEmpty()) System.out.print(prompt);
        System.out.print("\t");

        String string = input.nextLine().trim();

        if (string.equalsIgnoreCase("q")) throw new ExitMenuCommand();

        return string;
    }

    static void println(String... strings)
    {
        for (String string : strings) System.out.println(string);
    }

    static void println(String[][] strings)
    {
        String[] out;
        int length = 0;
        for (int i = 0; i < strings.length; i++)
        {
            if (strings[i].length > length) length = strings[i].length;
            int width = 0;
            for (String s : strings[i]) if (s.length() > width) width = s.length();
            width++;
            for (String s : strings[i])
            {
                s = ' ' + s;
                while (s.length() <= width) s += ' ';
            }
        }
        out = new String[length];
        for (int i = 0; i < length; i++)
        {
            for (String[] s : strings) out[i] += s[i]; // TODO: catch indexOutOfBounds
        }
    }
}


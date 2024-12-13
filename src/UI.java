import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// interface handling communication to/from the user.
// (it's an interface since everything here is public-static anyway)
public interface UI
{
    // user keyboard-input scanner
    Scanner input = new Scanner(System.in);

    // practical/common dateTime formatters
    String dateFormat     = "d M yyyy";
    String dateTimeFormat = "H:m d-MMM-yyyy";
    DateTimeFormatter dateFormatter     = DateTimeFormatter.ofPattern(dateFormat);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);

    // Methods to asks for common scenarios of user input.
    static String inquire() throws AbortToMenuCommand {return inquire("");}
    static String inquire(String prompt) throws AbortToMenuCommand
    {
        // display prompt, if any, to user, allows no prompt, as advanced prompts may have been formulated earlier.
        System.out.println();
        if (!prompt.isEmpty()) {System.out.print(prompt + "\nTryk q: Menu ↩\t");}
        System.out.print("\t"); // purely cosmetic tabulation

        // take user input.
        String string = input.nextLine().trim();

        // handle the case of user aborting to menu,
        // done here as this is allowed for ANY instance of the user being prompted for input.
        if (string.equalsIgnoreCase("q")) throw new AbortToMenuCommand();

        // return users input to, presumably, be evaluated in an appropriate switch or saved as data.
        return string;
    }
    static String inquire(String option, String... options) throws AbortToMenuCommand
    {
        printOptions(option, options);
        return inquire();
    }

    // method to print several options for the user to choose from.
    static void printOptions(String option, String... options)
    {
        int i = 1;
        System.out.print("Tryk " + i + ": " + option); // print the 'at least one option required'

        for (String string : options) // print all further options
        {
            // after each 4th option put the rest on a new line,
            if (i % 4 == 0) System.out.println("\n");
            i++;
            System.out.print("Tryk " + i + ": " + string);
        }

        // end with informing the user of the ever-present option to abort to previous menu.
        System.out.println("\n\nTryk q: Tilbage til forrige menu\n");
    }

    // method to print a table of strings, assuming tabulations to denote columns.
    // this method creates an empty header with correct amount of tabulations,
    // for when no header is required
    static void println(String... strings)
    {
        int tabs = strings[0].length()-strings[0].replace('\t',' ').length();
        String header = " ";
        for (int i = 0; i < tabs; i++)
        {
            header += "\t ";
        }
        println(header, strings);
    }

    // method to print a list of strings
    static void println(String string, String... strings)
    {
        // checks if list is formatted as a table (\t denotiong columns)
        int tabs = string.length() - string.replace("\t", "").length();
        boolean equalTabs = strings.length > 0;

        for (String s : strings)
        {
            if (tabs != s.length() - s.replace("\t", "").length())
            {
                equalTabs = false; break;
            }
        }

        // if list is formatted as a table, split the list into an actual 2-dimensional table
        if (equalTabs)
        {
            String[][] container = new String[strings.length+1][];
            container[0] = string.split("\t"); // handle the 'at least one string required'
            for (int i = 1; i < container.length; i++) // split each string into columns
            {
                container[i] = strings[i-1].split("\t");
            }
            printTable(container);
            return; // return after printing table
        }

        System.out.println(string);
        for (String s : strings) System.out.println(s);
    }

    // print a 2-dimensional table of strings, assumed as [row][column]
    static void printTable(String[][] strings) // TODO: catch indexOutOfBounds
    {
        // list of strings to print when formatted
        String[] out = new String[strings.length];

        for (int c = 0; c < strings[0].length; c++) // for each column
        {
            int width = 0;

            // for ease of reading:
            // 'c' = column
            // 'r' = row

            // find the widest string in the column
            for (int r = 0; r < strings.length; r++)
            {
                if (strings[r][c].length() > width) width = strings[r][c].length();
            }
            width++; // add some padding for readability

            // add padding to each string in column to ensure all are of equal length
            for (int r = 0; r < strings.length; r++)
            {
                while (strings[r][c].length() <= width) strings[r][c] += ' ';
            }
        }

        // make each row into single string for printing
        for (int r = 0; r < out.length; r++)
        {
            out[r] = "";
            for (int c = 0; c < strings[0].length; c++) out[r] += strings[r][c];
        }

        // print each row of the table
        for (String r : out) System.out.println(r);
    }
}


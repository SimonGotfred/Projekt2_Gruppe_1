import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SaveData {
    static String format = "yyyy-MM-dd";
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    public static void main(String[] args) {
       /* MemberRegister.members.add(new Member("alex", LocalDate.now().minusYears(18),"00110011"));
        MemberRegister.members.add(new Member("pedro", LocalDate.now().minusYears(18),"98899889"));
        MemberRegister.members.add(new Member("sarbjit", LocalDate.now().minusYears(18),"10011001"));
        saveData();*/
        makeMembersFromData();
        for (Member m : MemberRegister.members){
            System.out.println(m.getName() + ",  " + m.getPhoneNumber());
        }
    }
    static void makeMembersFromData()  {
        try {
            FileReader file = new FileReader("src/data.txt");
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();
            while (line != null){
                String[] var = line.split(",");
                MemberRegister.members.add(new Member(var[0], LocalDate.parse(var[1], dateTimeFormatter), var[2]));

                line = reader.readLine();
            }
        }
        catch (IOException e){
            System.out.println("couldn't find file");
        }
    }
    static void saveData() {
        try {
            PrintWriter ud = new PrintWriter(new FileWriter("src//data.txt"));
            for (Member m : MemberRegister.members){
                ud.println(m.getName() + ","+ m.getBirthDate()+","+m.getPhoneNumber());
            }
            ud.close();
        }
        catch (IOException e){
            System.out.println("couldn't save file");
        }

    }
}

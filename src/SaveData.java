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
        for (Member m : MemberRegister.getMembers()){
            System.out.println(m.getName() + ",  " + m.getPhoneNumber() + m.fee());
        }
    }
    static void makeMembersFromData(){
        try {
            FileReader file = new FileReader("src/data.txt");
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();
            while (line != null){
                String[] Performances = line.split("/");
                String[] var = Performances[0].split(",");
                boolean isActive = false;
                if (var[5].equals("True"))
                    isActive = true;
                Member member = new Member(var[0], LocalDate.parse(var[1], dateTimeFormatter), var[2], isActive);
                member.charge(Double.parseDouble(var[3]));

                if (Performances.length > 1){
                    for (int i = 1; i < Performances.length; i++){
                        String[] pVar = Performances[i].split(",");
                      //  member.addPerformance(new Performance(Discipline.valueOf(pVar[0].toUpperCase())), LocalDate.parse(pVar[1], dateTimeFormatter), );
                    }
                }

                MemberRegister.addMember(member);
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
            for (Member m : MemberRegister.getMembers()){
                ud.print(m.getName() + ","+ m.getBirthDate()+","+m.getPhoneNumber()+ "," + m.paymentOwed() + "," + m.getNextFeeDate() + "," + m.isActive());
                for (Performance p : m.getPerformances()){
                    ud.print("/" + p.discipline + "," + p.location + "," + p.note + "," + p.mark + "," + p.dateTime + "," + p.placement);
                }
                LocalDate d = LocalDate.parse(" ", MemberRegister.dateTimeFormatter);

            }
            ud.close();
        }
        catch (IOException e){
            System.out.println("couldn't save file");
        }

    }
}

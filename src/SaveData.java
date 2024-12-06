import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                String[] performances = line.split("/");
                System.out.println(performances.length);
                String[] var = performances[0].split(",");
                boolean isActive = false;
                if (var[5].equals("True"))
                    isActive = true;
                Member member = Member.newCompetitor(var[0], LocalDate.parse(var[1], dateTimeFormatter), var[2]);
                member.charge(Double.parseDouble(var[3]));

                if (performances.length > 1){
                    for (int i = 1; i < performances.length; i++){
                        String[] pVar = performances[i].split(",");
                        member.addPerformance(new Performance(Discipline.valueOf(pVar[0].toUpperCase()), LocalDateTime.parse(pVar[4], dateTimeFormatter), pVar[1], Integer.parseInt(pVar[3]), Integer.parseInt(pVar[5]),pVar[2]));
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
                //LocalDate d = LocalDate.parse(" ", MemberRegister.dateTimeFormatter);

            }
            ud.close();
        }
        catch (IOException e){
            System.out.println("couldn't save file");
        }

    }
}

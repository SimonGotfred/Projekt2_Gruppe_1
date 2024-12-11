import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveData {
    static String format = "yyyy-MM-dd";
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    static boolean doneLoadingMembers = false;
    public static void main(String[] args) {
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
                String[] var = performances[0].split(",");

                Member member = Member.newCompetitor(var[0], LocalDate.parse(var[1], dateTimeFormatter), var[2]);
                //member.payAll();
                member.setNextFeeDate(LocalDate.parse(var[4], dateTimeFormatter));
                member.charge(Double.parseDouble(var[3]));
                if (var[5].equals("C"))
                    member.setCompetitor();
                else if (var[5].equals("A"))
                    member.setActive();
                else
                    member.setPassive();

                if (performances.length > 1){
                    for (int i = 1; i < performances.length; i++){
                        String[] pVar = performances[i].split(",");
                        member.addPerformance(new Performance(Discipline.valueOf(pVar[0].toUpperCase()), LocalDateTime.parse(pVar[4].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-M-d H:m") ), pVar[1], Integer.parseInt(pVar[3]), Integer.parseInt(pVar[5]),pVar[2]));
                    }
                }

                MemberRegister.addMember(member);
                line = reader.readLine();
            }
            doneLoadingMembers = true;
        }
        catch (IOException e){
            System.out.println("couldn't find file");
        }
    }
    static void saveData() {
        if (!doneLoadingMembers)
            return;
        try {
            PrintWriter ud = new PrintWriter(new FileWriter("src//data.txt"));
            for (int i = 0; i < MemberRegister.getMembers().size(); i++){
                Member m = MemberRegister.getMembers().get(i);
                String pCOrA = "";
                if (m.isCompetitor())
                    pCOrA = "C";
                else if (m.isActive())
                    pCOrA = "A";
                else
                    pCOrA = "P";
                ud.print(m.getName() + ","+ m.getBirthDate()+","+m.getPhoneNumber()+ "," + m.paymentOwed() + "," + m.getNextFeeDate() + "," + pCOrA);
                for (Performance p : m.getPerformances()){
                    ud.print("/" + p.discipline + "," + p.location + "," + p.note + "," + p.mark + "," + p.dateTime + "," + p.placement);
                }
                if (i != MemberRegister.getMembers().size() - 1)
                    ud.println();
                //LocalDate d = LocalDate.parse(" ", MemberRegister.dateTimeFormatter);

            }
            ud.close();
        }
        catch (IOException e){
            System.out.println("couldn't save file");
        }

    }
}

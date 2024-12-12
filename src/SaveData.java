import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveData {
    static String format = "yyyy-MM-dd";
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    static boolean doneLoadingMembers = false;
    static DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
    //main just for testing
    public static void main(String[] args) {
        makeMembersFromData();
        for (Member m : MemberRegister.getMembers()){
            System.out.println(m.getName() + ",  " + m.getPhoneNumber() + m.fee());
        }
    }


    //makes a bunch of members from the data.txt file
    static void makeMembersFromData(){
        try {
            // reads the txt file called data.txt saved in the src folder
            FileReader file = new FileReader("src/data.txt");
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();
            // reads one line at a time and foreach line it splits it by each "/"
            while (line != null){
                String[] performances = line.split("/");
                // the first string in the split string[](performances) always being the
                // variables for the member, it then splits it by "," to get the individual var
                String[] var = performances[0].split(",");
                //it makes a new member and sets all the variables to the variables we just got
                Member member = Member.newCompetitor(var[0], LocalDate.parse(var[1], dateTimeFormatter), var[2]);
                member.setNextFeeDate(LocalDate.parse(var[4], dateTimeFormatter));
                member.charge(Double.parseDouble(var[3]));
                if (var[5].equals("C"))
                    member.setCompetitor();
                else if (var[5].equals("A"))
                    member.setActive();
                else
                    member.setPassive();
                // for the rest of the performances it also splits it by ","
                // and adds a new performance with the given variables
                if (performances.length > 1){
                    for (int i = 1; i < performances.length; i++){
                        String[] pVar = performances[i].split(",");
                        member.addPerformance(new Performance(Discipline.valueOf(pVar[0].toUpperCase()), LocalDateTime.parse(pVar[4].replace("T", " "), dTF ), pVar[1], Integer.parseInt(pVar[3]), Integer.parseInt(pVar[5]),pVar[2]));
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
    // gets called each time a change is made to a member or a new member is added
    // foreach member in members it saves all the variables with a "," inbetween
    // it also saves all the variable from each performance, but with a "/" between each performance
    // saves all the data to a txt file as a string
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
                    ud.print("/" + p.discipline + "," + p.location + "," + p.note + "," + p.mark + "," + p.dateTime.format(dTF) + "," + p.placement);
                }
                if (i != MemberRegister.getMembers().size() - 1)
                    ud.println();

            }
            ud.close();
        }
        catch (IOException e){
            System.out.println("couldn't save file");
        }

    }
}

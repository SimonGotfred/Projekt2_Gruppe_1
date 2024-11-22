import java.time.LocalDate;

public class PayMenu {
    public static void main(String[] args) {
        MemberRegister.members.add(new Member("John Doe", LocalDate.of(2010,10,10),"12121212"));
        MemberRegister.members.add(new Member("Ida Doe", LocalDate.of(2010,10,10),"12121212"));
        MemberRegister.members.add(new Member("Carl Doe", LocalDate.of(2010,10,10),"12121212"));

        MemberRegister.members.getFirst().pay(600);

        paymentMenu();
    }
    public static void paymentMenu(){
        System.out.println("Velkommen til Payment menuen");
        for (Member member : MemberRegister.members){
            System.out.println(member.name + " " + paymentStatus(member.hasPaid()));
        }
    }

    public static String paymentStatus(boolean hasPaid){
        if (hasPaid){
            return "Har betalt";
        }
        else{
            return "Har ikke betalt";
        }
    }
}

import java.time.LocalDate;
import java.util.Random;

public class Member implements Comparable<Member>
{
    public static void main(String[] args) // ONLY FOR TESTING
    {
        Random r = new Random();

        for (int i = 0; i < 10; i++)
        {
            MemberRegister.members.add(TestingSuite.getMember());
        }

        MemberRegister.members.add(TestingSuite.getMember(62));

        for (Member member : MemberRegister.members)
        {
            if (r.nextBoolean()) member.setActive();
            System.out.println(member);
        }

    }

    final  static double baseFee   = 600;
    final  static double juniorFee = 400;
    final  static double seniorFee = 600;
    final  static double discount  =  .7;

    public static boolean isPhoneNumber(String string)
    {
        if (string.length() != 8) return false;

        char[] chars = string.toCharArray();
        for (char c : chars) if (c < '0' || c > '9') return false;

        return true;
    }

    public static boolean isName(String string)
    {
        if (string.isEmpty()) return false;

        for (char c : string.toLowerCase().toCharArray())
        {
            if ((c < 'a' && c != ' ') || (c > 'z' && (c < 'ß' || c > 'ü')) || c == '÷') return false;
        }

        return true;
    }


              String    name; // TODO: make private
    final     LocalDate birthDate;
    private   String    phoneNumber;
    private   LocalDate dueUpdated;
    private   double    paymentDue;
    protected boolean   isActive; // TODO: make as methods, overridable by 'Competitor'

    public Member(String name, LocalDate birthDate, String phoneNumber)
    {
        LocalDate dateNow = LocalDate.now();

        if (birthDate.isAfter (dateNow.minusYears( 12))) throw new IllegalArgumentException("Members younger than 12 years are not supported.");
        if (birthDate.isBefore(dateNow.minusYears(100))) throw new IllegalArgumentException("Members older than 100 years are not supported.");

        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.birthDate = birthDate;
        this.isActive  = false;

        this.dueUpdated = dateNow.minusDays(1);
        checkPayment();
    }

    public Member(String name, String birthDate, String phoneNumber)
    {
        this(name, LocalDate.parse(birthDate, MemberRegister.dateTimeFormatter), phoneNumber);
    }

    public String getName()      {return name;                                  }
    public String getFirstName() {return name.split(" ")[0];              }
    public String getLastName()  {return name.split(" ")[name.length()-1];}
    public void   setName(String name)
    {
        String n = name.trim();

        if (!isName(n)) throw new IllegalArgumentException("'Member' name may not be empty, or contain special characters.");

        this.name = n;
    }

    public LocalDate getBirthDate() {return birthDate;}
    public int       getAge()       {return birthDate.until(LocalDate.now()).getYears();      }
    public boolean   isJunior()     {return birthDate.until(LocalDate.now()).getYears() <= 18;}
    public boolean   isPensioner()  {return birthDate.until(LocalDate.now()).getYears() >= 60;}

    public String getPhoneNumber()  {return phoneNumber;}
    public void   setPhoneNumber(String phoneNumber) throws IllegalArgumentException
    {
        if (!isPhoneNumber(phoneNumber)) throw new IllegalArgumentException("Attempt to instantiate type 'Member', with illegal format for phone number. Format must be be '########'");
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive()   {return true; }
    public boolean isPassive()  {return false;}
    public void    setActive()  {isActive = true; }
    public void    setPassive() {isActive = false;}

    public void charge(double amount) {paymentDue += amount;} // TODO: add discount?

    public void pay(double amount) {paymentDue -= amount;}
    public void pay()              {pay(fee());}

    public boolean hasPaid()     {return checkPayment() <= 0.;}
    public double  paymentOwed() {return checkPayment();      }

    public double fee()
    {
        double fee = baseFee;

        if (isActive) {fee += juniorFee; if (!isJunior()) fee += seniorFee;}
        if (isPensioner()) fee *= discount;

        return fee;
    }

    private double checkPayment()
    {
        if (LocalDate.now().isBefore(dueUpdated)) return paymentDue;
        dueUpdated = dueUpdated.plusYears(1);

        paymentDue += fee();

        return paymentDue;
    }

    public String format(String format) // TODO
    {
        format = " "+format+" ";
        format = format.replace("\t" , " \t ");
        format = format.replace(" n ", " "+name+" ");
        format = format.replace(" a ", " "+getAge()+" ");
        format = format.replace(" b ", " "+birthDate.format(MemberRegister.dateTimeFormatter)+" ");
        format = format.replace(" p ", " "+phoneNumber+" ");
        format = format.replace(" f ", " "+fee()+" ");
        format = format.replace(" o ", " "+checkPayment()+" ");

        return format.trim();
    }

    public String toString()
    {
        return format("n\tb tlf: p");
        // return name + "\t" + birthDate.format(MemberRegister.dateTimeFormatter) + "\t" + phoneNumber;
    }

    public int compareTo(Member other) {return this.name.compareTo(other.name);}
}

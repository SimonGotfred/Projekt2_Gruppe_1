import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Member
{
    public static void main(String[] args) // ONLY FOR TESTING
    {
        Member member = new Member("John Doe", LocalDate.of(1960, 1, 1), "12121212");
    }

    final   static double baseFee   =  600;
    final   static double seniorFee = 1600;
    final   static double juniorFee = 1000;
    final   static double discount  =   .7;
    private static LocalDate dateNow; // TODO: consider whether static or not

    public static boolean isPhoneNumber(String string)
    {
        if (string.length() != 8) return false;

        char[] chars = string.toCharArray();
        for (char c : chars) if (c < '0' || c > '9') return false;

        return true;
    }

    final     String    name;
    final     LocalDate birthDate;
    private   String    phoneNumber;
    private   LocalDate paidUntil;
    private   double    paymentOwed;
    protected boolean   isActive;

    public Member(String name, LocalDate birthDate, String phoneNumber)
    {
        dateNow = LocalDate.now();

        if (birthDate.isAfter (dateNow.minusYears( 12))) throw new IllegalArgumentException("Members younger than 12 years are not supported.");
        if (birthDate.isBefore(dateNow.minusYears(100))) throw new IllegalArgumentException("Members older than 100 years are not supported.");

        this.name      = name;
        this.birthDate = birthDate;
        this.isActive  = false;
        this.setPhoneNumber(phoneNumber);

        this.paidUntil = dateNow.minusDays(1);
        checkPayment();
    }

    public Member(String name, String birthDate, String phoneNumber)
    {
        this(name, LocalDate.parse(birthDate, MemberRegister.dateTimeFormatter), phoneNumber);
    }

    public int yearsOld() {return birthDate.until(LocalDate.now()).getYears();}

    public String getPhoneNumber() {return phoneNumber;}
    public void   setPhoneNumber(String phoneNumber) throws IllegalArgumentException
    {
        if (!isPhoneNumber(phoneNumber)) throw new IllegalArgumentException("Attempt to instantiate type 'Member', with illegal format for phone number. Format must be be '########'");
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive()   {return isActive; }
    public boolean isPassive()  {return !isActive;}
    public void    setActive()  {isActive = true; }
    public void    setPassive() {isActive = false;}

    public boolean pay(double amount) // TODO
    {
        if (paymentOwed <= 0) return false;

        paymentOwed -= amount;

        paidUntil = paidUntil.plusYears(1);


        return true;
    }

    public boolean hasPaid()     {return (checkPayment() <= 0);}
    public double  paymentOwed() {return  checkPayment();      }

    private double checkPayment()
    {
        dateNow = LocalDate.now();

        if (dateNow.isBefore(paidUntil)) return paymentOwed;
        paidUntil.plusYears(1);

        double fee = 600;

        if (isActive) fee += 400; if (birthDate.until(dateNow).getYears() >= 60) fee += 600;
        if (birthDate.until(dateNow).getYears() >= 60) fee *= discount;

        paymentOwed += fee;

        return paymentOwed;
    }

    public String toString()
    {
        return name + " " + birthDate.format(MemberRegister.dateTimeFormatter) + " " + phoneNumber;
    }
}

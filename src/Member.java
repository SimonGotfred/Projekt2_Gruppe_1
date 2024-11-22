import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Member
{
    public static void main(String[] args) // ONLY FOR TESTING
    {
        Member member = new Member("John Doe", LocalDate.of(1960, 1, 1), "12121212");
    }

    final static double baseFee    =  600;
    final static double seniorFee  = 1600;
    final static double juniorFee  = 1000;

    final static double discount   =   .7;
    private static LocalDate dateNow; // TODO: consider whether static or not

    final String    name;
    final LocalDate birthDate;

    String    phoneNumber;
    LocalDate paidUntil;
    boolean   isActive;
    double    paymentOwed;

    public Member(String name, LocalDate birthDate, String phoneNumber)
    {
        dateNow = LocalDate.now();

        if (!isPhoneNumber(phoneNumber)) throw new IllegalArgumentException("Attempt to instantiate type 'Member', with illegal format for phone number. Format must be be '########'");
        if (birthDate.isAfter(dateNow.minusYears(12))) throw new IllegalArgumentException();

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.isActive = false;

        this.paidUntil = dateNow.minusDays(1);
        checkPayment();
    }

    public boolean pay(double amount)
    {
        if (paymentOwed <= 0) return false; // TODO, error-message if paid too much.

        paymentOwed -= amount;
        return true;
    }

    public boolean hasPaid()
    {
        return (checkPayment() <= 0);
    }

    public double paymentOwed()
    {
        return checkPayment();
    }

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

    public boolean isPhoneNumber(String string)
    {
        if (string.length() != 8) return false;

        char[] chars = string.toCharArray();
        for (char c : chars) if (c < '0' || c > '9') return false;

        return true;
    }
}

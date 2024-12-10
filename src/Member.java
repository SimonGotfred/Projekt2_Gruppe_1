import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Member implements Comparable<Member>
{
          // ONLY FOR TESTING
    public static void main(String[] args)
    {
        TestingSuite.populateMembers();
    } // END OF TESTING


    final static int maxAge       =  150;
    final static int minAge       =    3;
    final static String currency  = "kr";
    final static double baseFee   =  600; // base fee that everyone must pay.
    final static double juniorFee =  400; // additional fee for being an active member.
    final static double seniorFee =  600; // additional fee for active members that are older than 18 years.
    final static double discount  =   .7; // modifier for the discounted price for members older than 60 years.

    public static Member newPassive(String name, LocalDate birthDate, String phoneNumber)
    {
        return new Member(name, birthDate, phoneNumber);
    }

    public static Member newActive(String name, LocalDate birthDate, String phoneNumber)
    {
        Member member = new Member(name, birthDate, phoneNumber);
        member.setActive();
        return member;
    }

    public static Member newCompetitor(String name, LocalDate birthDate, String phoneNumber)
    {
        Member member = new Member(name, birthDate, phoneNumber);
        member.setCompetitor();
        return member;
    }

    // TODO: 'isPhoneNumber' and 'isName' belongs in their own class
    // checks string if valid phone-number
    public static boolean isPhoneNumber(String string)
    {
        // phone-numbers must be 8 digits.
        if (string.length() != 8) return false;

        // each digit must be between 0 and 9.
        for (char c : string.toCharArray()) if (c < '0' || c > '9') return false;

        return true;
    }

    // checks string if POTENTIALLY valid name, i.e. contains only valid letters & spaces.
    public static boolean isName(String string)
    {
        // names cannot be empty.
        if (string.isEmpty()) return false;

        // 'toLowerCase' to halve which chars are checked for.
        for (char c : string.toLowerCase().toCharArray())
        {
            // read as, char must be a-z, 'space' or accented vowel.
            // But for SOME reason SOMEONE decided to put '÷' in the
            // span of accented vowels in ascii,so I have to check for that AS WELL!
            if ((c < 'a' && c != ' ') || (c > 'z' && (c < 'ß' || c > 'ü')) || c == '÷') return false;
        }

        return true;
    }


    private final ArrayList<LocalDate>   memberHistory;
    private final ArrayList<Performance> performances;
    private final HashSet<Discipline>    disciplines;

    private final LocalDate birthDate;
    private String          name;
    private String          phoneNumber;
    private LocalDate       nextFeeDate;
    private double          paymentOwed;
    private boolean         isActive;
    private boolean         isCompetitor;

    // Constructor for 'Member' demands arguments 'name' and 'birthDate' that are essential for functionality.
    // 'phoneNumber' is demanded to differentiate members with similar names,
    // and to allow contacting members.
    public Member(String name, LocalDate birthDate, String phoneNumber) throws IllegalArgumentException
    {
        LocalDate dateNow = LocalDate.now(); // avoid multiple calls to function.

        // do not allow registering members younger than 3 or older than 100 years old.
        if (birthDate.isAfter (dateNow.minusYears(minAge))) throw new IllegalArgumentException("Members younger than 3 years are not supported.");
        if (birthDate.isBefore(dateNow.minusYears(maxAge))) throw new IllegalArgumentException("Members older than 150 years are not supported.");
        this.birthDate = birthDate;

        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setPassive();

        this.disciplines   = new HashSet<>();
        this.performances  = new ArrayList<>();
        this.memberHistory = new ArrayList<>();

        this.register();
    }

    public Member(String name, LocalDate birthDate, String phoneNumber, LocalDate nexFeeDate)
    {
        this(name, birthDate, phoneNumber);
        payAll();
        this.nextFeeDate = nexFeeDate;
        paymentOwed();
    }

    // methods regarding membership type.
    public boolean isActive()      {return isActive;    }
    public boolean isPassive()     {return !isActive;   }
    public boolean isCompetitor()  {return isCompetitor;}
    public void    setActive()     {isActive = true;  isCompetitor = false; SaveData.saveData();}
    public void    setPassive()    {isActive = false; isCompetitor = false; SaveData.saveData();}
    public void    setCompetitor() {isActive = true;  isCompetitor = true;  SaveData.saveData();}
    public String  getType()       {if (isCompetitor) return "Konkurrent"; if (isActive) return "Motionist"; return "Passiv Medlem";}

    // TODO: functionality to de-list members when they leave club, while retaining their data
    public boolean isRegistered() {return memberHistory.size() % 2 != 0;}
    public void    unregister()
    {
        if(isRegistered())
        {
            nextFeeDate = LocalDate.MAX;
            memberHistory.add(LocalDate.now());
            SaveData.saveData();
        }
    }
    public void    register()
    {
        if (isRegistered()) return; // disallow registering when already registered.

        // set nextFeeDate to now if last registered more than half a year ago.
        if (memberHistory.isEmpty() || memberHistory.getLast().until(LocalDate.now(), ChronoUnit.MONTHS) > 6)
        {
            memberHistory.add(LocalDate.now());
            nextFeeDate = memberHistory.getLast().minusDays(1);
        }
        else // if registered less
        {
            nextFeeDate = memberHistory.getLast().plusMonths(6);
            memberHistory.add(LocalDate.now());
        }
        // ensure payment is due immediately for newly registered members
        paymentOwed();
    }

    // methods regarding name
    public String getName()      {return name;                                  }
    public String getFirstName() {return name.split(" ")[0];              }
    public String getLastName()  {return Arrays.asList(name.split(" ")).getLast();}
    public void   setName(String name) throws IllegalArgumentException
    {
        String n = name.trim(); // ensure no leading or trailing whitespaces

        // check if valid name.
        if (!isName(n)) throw new IllegalArgumentException("'Member' name may not be empty, or contain special characters.");
        this.name = n;
        SaveData.saveData();
    }

    // methods regarding age.
    public int       getAge()       {return birthDate.until(LocalDate.now()).getYears();      }
    public boolean   isJunior()     {return birthDate.until(LocalDate.now()).getYears() <= 18;}
    public boolean   isPensioner()  {return birthDate.until(LocalDate.now()).getYears() >= 60;}
    public LocalDate getBirthDate() {return birthDate;}

    // methods regarding contact information
    public String getPhoneNumber()  {return phoneNumber;}
    public void   setPhoneNumber(String phoneNumber) throws IllegalArgumentException
    {
        // check if valid phone-number
        if (!isPhoneNumber(phoneNumber)) throw new IllegalArgumentException("Attempt to instantiate type 'Member', with illegal format for phone number. Format must be be '########'");
        this.phoneNumber = phoneNumber;
        SaveData.saveData();
    }

    // methods regarding disciplines
    public boolean doesDiscipline(Discipline discipline) {return disciplines.contains(discipline);}
    public Discipline[] disciplines() {return disciplines.toArray(new Discipline[0]);}

    // methods regarding adding and getting performances
    public void addPerformance(Performance performance, Performance... performances)
    {
        this.performances.add(performance);
        this.disciplines.add(performance.discipline);
        for (Performance p : performances)
        {
            this.performances.add(p);
            this.disciplines.add(p.discipline);
        }
        this.performances.sort(null);
        SaveData.saveData();
    }
    public Performance[] getPerformances() {return performances.toArray(new Performance[0]);}
    public Performance   getBestPerformance(Discipline discipline)
    {
        ArrayList<Performance> dp = new ArrayList<>(performances);
        dp.removeIf(performance -> performance.discipline != discipline);
        if (dp.isEmpty()) return null;
        dp.sort(null);
        return dp.getFirst();
    }

    // query as to a members current fee, regardless of payment status.
    public double fee()
    {
        double fee = baseFee;

        // add further fee if active member, and again if the active member is older than 18 years
        if (isActive()) {fee += juniorFee; if (!isJunior()) fee += seniorFee;}
        if (isPensioner()) fee *= discount; // adjust for 60+ year-olds discount

        return fee;
    }

    // yearly application of membership fee to payment owed.
    private void applyFee()
    {
        // return early if before scheduled fee.
        if (LocalDate.now().isBefore(nextFeeDate)) return;

        paymentOwed += fee();

        // update next fee to be applied a year later.
        nextFeeDate = nextFeeDate.plusYears(1);
        SaveData.saveData();
    }

    // method to directly add a charge to payment owed. For e.g. when passive members use the facilities.
    public void charge(double amount) {paymentOwed += amount; SaveData.saveData();}

    // package-private getter and setter, as these are only intended for use, when saving/loading from file.
    public LocalDate getNextFeeDate() {return nextFeeDate;}
    public void setNextFeeDate(LocalDate nextFeeDate) {this.nextFeeDate = nextFeeDate;}

    // methods to adjust payment owed according to specific payment,
    // expected payment fee, or paying all that is owed.
    public void pay(double amount) {paymentOwed -= amount; SaveData.saveData();}
    public void pay()              {pay(fee());}
    public void payAll()           {if(paymentOwed > 0) pay(paymentOwed);}

    // queries regarding payment owed
    // uses 'paymentOwed()' method to ensure potential new fee is accounted for
    public boolean hasPaid() {return paymentOwed() <= 0.;}
    public double  paymentOwed()
    {
        if (isRegistered()) applyFee(); // apply necessary fee.
        return paymentOwed;
    }

    // members are compared alphabetically by name.
    public int compareTo(Member other) {return this.name.compareTo(other.name);}

    // rudimentary formatter of member to string format.
    public String toString(String format)
    {
        format = ' '+format.trim()+' ';
        format = format.replace("\t" ," \t ");
        format = format.replace(" n ",name+' ');
        format = format.replace(" a ",getAge()+"år ");
        format = format.replace(" b ",birthDate.format(MemberRegister.dateTimeFormatter)+' ');
        format = format.replace(" p ","tlf: "+phoneNumber+' ');
        format = format.replace(" t ",getType()+' ');
        format = format.replace(" f ",String.format("%.2f", fee())+currency+' ');
        format = format.replace(" o ",String.format("%.2f", paymentOwed())+currency+' ');

        return format;
    }

    // toString formatted as 'name birthday phone-number'.
    public String toString()
    {
        return toString("n\tb p");
        // return name + "\t" + birthDate.format(MemberRegister.dateTimeFormatter) + "\t" + phoneNumber;
    }
}

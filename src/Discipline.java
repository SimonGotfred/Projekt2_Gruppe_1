public enum Discipline
{
    FREESTYLE,
    BACKSTROKE,
    BREASTSTROKE,
    BUTTERFLY,
    MEDLEY,
    RELAY,

    DIVING,
    HIGH_DIVING,
    WATER_POLO;

    public String formatMark(int mark)
    {
        if (this.ordinal() < DIVING.ordinal())
        {
            return ""+mark; // TODO: format mark as time.
        }
        else
        {
            return mark + " points";
        }
    }

    public int markToInt(String mark)
    {
        return 0; // TODO: logic
    }
}

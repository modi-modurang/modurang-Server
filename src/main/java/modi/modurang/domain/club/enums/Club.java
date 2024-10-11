package modi.modurang.domain.club.enums;

public enum Club {

    BIND,
    CNS,
    MODI,
    DUCAMI,
    SAMD,
    ALT,
    COMMAND;

    public static boolean isInvalidClub(String club) {
        for (Club type : Club.values()) {
            if (type.name().equals(club)) {
                return false;
            }
        }
        return true;
    }
}

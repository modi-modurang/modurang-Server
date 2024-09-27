package modi.modurang.domain.club.enums;

public enum Club {

    BIND,
    CNS,
    MODI;

    public static boolean isValidClub(String club) {
        for (Club type : Club.values()) {
            if (type.name().equals(club)) {
                return false;
            }
        }
        return true;
    }
}

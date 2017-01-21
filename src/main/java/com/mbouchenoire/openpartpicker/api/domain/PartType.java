package com.mbouchenoire.openpartpicker.api.domain;

/**
 * @author mbouchenoire
 */
public enum PartType {
    AIRFRAME("airframe", "airframes"),
    BATTERY("battery", "batteries"),
    ESC("esc", "esc"),
    SERVO("servo", "servos"),
    MOTOR("motor", "motors");

    private final String singularCode;
    private final String pluralCode;

    PartType(String singularCode, String pluralCode) {
        this.singularCode = singularCode;
        this.pluralCode = pluralCode;
    }

    public String getSingularCode() {
        return singularCode;
    }

    public String getPluralCode() {
        return pluralCode;
    }

    public static PartType fromCode(String code) {
        for(PartType type: PartType.values()) {
            if (type.getSingularCode().equals(code) || type.getPluralCode().equals(code)) {
                return type;
            }
        }

        throw new IllegalArgumentException("code");
    }
}

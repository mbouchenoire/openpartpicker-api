package com.mbouchenoire.openpartpicker.api.domain;

/**
 * @author mbouchenoire
 */
public enum ProductTemplateType {
    UAV("uav");

    private final String code;

    ProductTemplateType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ProductTemplateType fromCode(String code) {
        for(ProductTemplateType type: ProductTemplateType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }

        throw new IllegalArgumentException("code");
    }
}

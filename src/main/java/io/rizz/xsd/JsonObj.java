package io.rizz.xsd;

import java.util.ArrayList;

public class JsonObj {
    String name="";
    String description="";
    String multipleOf="";
    String maximum="";
    String type="";
    String pattern="";
    String maxLength="";
    String minLength="";
    ArrayList<String> enums=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMultipleOf() {
        return multipleOf;
    }

    public void setMultipleOf(String multipleOf) {
        this.multipleOf = multipleOf;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public ArrayList<String> getEnums() {
        return enums;
    }

    public void setEnums(ArrayList<String> enums) {
        this.enums = enums;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    @Override
    public String toString() {
        return "JsonObj{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", multipleOf='" + multipleOf + '\'' +
                ", maximum='" + maximum + '\'' +
                ", type='" + type + '\'' +
                ", pattern='" + pattern + '\'' +
                ", maxLength='" + maxLength + '\'' +
                ", minLength='" + minLength + '\'' +
                ", enums=" + enums +
                '}';
    }
}

package io.rizz.xsd;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleType {
    String name;
    String restrictionType;
    public ArrayList<String> enumeration = new ArrayList<String>();
    public String   maxValue    = null;
    public String   minValue    = null;
    public String   length      = null;
    public String   maxLength   = null;
    public String   minLength   = null;
    public String pattern     = null;
    public String   totalDigits = null;
    public String   fractionDigits = null;
    public String   whiteSpace = null;
    public String minInclusive=null;

    public SimpleType() {
    }

    public String getName() {
        return name;
    }

    public String getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(String restrictionType) {
        this.restrictionType = restrictionType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(ArrayList<String> enumeration) {
        this.enumeration = enumeration;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
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

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTotalDigits() {
        return totalDigits;
    }

    public void setTotalDigits(String totalDigits) {
        this.totalDigits = totalDigits;
    }

    public String getFractionDigits() {
        return fractionDigits;
    }

    public void setFractionDigits(String fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public String getWhiteSpace() {
        return whiteSpace;
    }

    public void setWhiteSpace(String whiteSpace) {
        this.whiteSpace = whiteSpace;
    }

    @Override
    public String toString() {
        return "SimpleType{" +
                "name='" + name + '\'' +
                ", restrictionType='" + restrictionType + '\'' +
                ", enumeration=" + enumeration.toString() +
                ", maxValue='" + maxValue + '\'' +
                ", minValue='" + minValue + '\'' +
                ", length='" + length + '\'' +
                ", maxLength='" + maxLength + '\'' +
                ", minLength='" + minLength + '\'' +
                ", pattern=" + pattern +
                ", totalDigits='" + totalDigits + '\'' +
                ", fractionDigits='" + fractionDigits + '\'' +
                ", whiteSpace='" + whiteSpace + '\'' +
                ", minInclusive='" + minInclusive + '\'' +
                '}';
    }

    public String getMinInclusive() {
        return minInclusive;
    }

    public void setMinInclusive(String minInclusive) {
        this.minInclusive = minInclusive;
    }
}

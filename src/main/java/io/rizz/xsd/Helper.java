package io.rizz.xsd;

public class Helper {
    public static boolean isSimpleTypeStart(String line){
        return line.contains("<xs:simpleType");
    }
    public static boolean isSimpleTypeEnd(String line){
        return line.contains("</xs:simpleType");
    }
    public static String extractName(String line){
        return line.substring(line.indexOf("=")+2,line.length()-2);
    }

    public static boolean isRestrictionStart(String line){
        return line.contains("<xs:restriction");
    }
    public static boolean isRestrictionEnd(String line){
        return line.contains("</xs:restriction") || isSimpleTypeEnd(line);
    }
    public static String extractRestrictionName(String line){
        return line.substring(line.lastIndexOf(":")+1,line.length()-2);
    }

    public static boolean isEnumerationStart(String line){
        return line.contains("<xs:enumeration");
    }
    public static boolean isEnumerationEnd(String line){
        return line.contains("</xs:enumeration");
    }
    public static String extractEnumerationName(String line){
        return line.substring(line.indexOf("=")+2,line.length()-2);
    }

    public static boolean setRestriction(String line, SimpleType el){
        boolean ret=true;
        if(el.getRestrictionType().equalsIgnoreCase("decimal"))
            setRestrictionDecimal(line,el);
        else if(el.getRestrictionType().equalsIgnoreCase("string"))
            ret=setRestrictionString(line,el);
        else if(el.getRestrictionType().equalsIgnoreCase("date"))
            setRestrictionDate(line,el);
        else if(el.getRestrictionType().equalsIgnoreCase("dateTime"))
            setRestrictionString(line,el);
        return ret;
    }

    public static void setRestrictionDecimal(String line, SimpleType el){
        String value=line.substring(line.indexOf('=')+2,line.length()-3);
        if(line.contains("fractionDigits"))
            el.setFractionDigits(value);
        else if(line.contains("totalDigits"))
            el.setTotalDigits(value);
        else if(line.contains("minInclusive"))
            el.setMinInclusive(value);
    }

    private static void setRestrictionDate(String line, SimpleType el) {
    }


    private static boolean setRestrictionString(String line, SimpleType el) {
        boolean ret=true;
        String value=line.substring(line.indexOf('=')+2,line.length()-3);
        if(line.contains("minLength"))
            el.setMinLength(value);
        else if(line.contains("maxLength"))
            el.setMaxLength(value);
        else if(line.contains("pattern"))
            el.setPattern(value);
        else if(line.contains("enumeration"))
            ret=false;
        return ret;
    }
}

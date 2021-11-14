package io.rizz.xsd;

import org.json.simple.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Helper {
    public static boolean isSimpleTypeStart(String line) {
        return line.contains("<xs:simpleType");
    }

    public static boolean isSimpleTypeEnd(String line) {
        return line.contains("</xs:simpleType");
    }

    public static String extractName(String line) {
        return line.substring(line.indexOf("=") + 2, line.length() - 2).replace("_", "").toLowerCase(Locale.ROOT);
    }

    public static boolean isDefinitionStart(String line) {
        return line.contains("<xs:documentation source=\"Definition\"");
    }

    public static String extractDefinition(String line) {
        return line.substring(line.indexOf(">") + 1, line.length() - "</xs:documentation>".length());
    }

    public static boolean isRestrictionStart(String line) {
        return line.contains("<xs:restriction");
    }

    public static boolean isRestrictionEnd(String line) {
        return line.contains("</xs:restriction") || isSimpleTypeEnd(line);
    }

    public static String extractRestrictionName(String line) {
        return line.substring(line.lastIndexOf(":") + 1, line.length() - 2).toLowerCase(Locale.ROOT);
    }

    public static boolean isEnumerationStart(String line) {
        return line.contains("<xs:enumeration");
    }

    public static boolean isEnumerationEnd(String line) {
        return line.contains("</xs:enumeration");
    }

    public static String extractEnumerationName(String line) {
        return line.substring(line.indexOf("=") + 2, line.length() - 2).toLowerCase(Locale.ROOT);
    }

    public static boolean setRestriction(String line, SimpleType el) {
        boolean ret = true;
        if (el.getRestrictionType().equalsIgnoreCase("decimal"))
            setRestrictionDecimal(line, el);
        else if (el.getRestrictionType().equalsIgnoreCase("string"))
            ret = setRestrictionString(line, el);
        else if (el.getRestrictionType().equalsIgnoreCase("date"))
            setRestrictionDate(line, el);
        else if (el.getRestrictionType().equalsIgnoreCase("dateTime"))
            setRestrictionString(line, el);
        return ret;
    }

    public static void setRestrictionDecimal(String line, SimpleType el) {
        String value = line.substring(line.indexOf('=') + 2, line.length() - 3);
        if (line.contains("fractionDigits"))
            el.setFractionDigits(value);
        else if (line.contains("totalDigits"))
            el.setTotalDigits(value);
        else if (line.contains("minInclusive"))
            el.setMinInclusive(value);
    }

    private static void setRestrictionDate(String line, SimpleType el) {
    }


    private static boolean setRestrictionString(String line, SimpleType el) {
        boolean ret = true;
        String value = line.substring(line.indexOf('=') + 2, line.length() - 3);
        if (line.contains("minLength"))
            el.setMinLength(value);
        else if (line.contains("maxLength"))
            el.setMaxLength(value);
        else if (line.contains("pattern"))
            el.setPattern(value);
        else if (line.contains("enumeration"))
            ret = false;
        return ret;
    }

    public static ArrayList<SimpleType> parseFile(String filename) throws IOException {
        FileReader f = new FileReader(filename);
        BufferedReader in = new BufferedReader(f);
        int n = 0;
        String line = in.readLine();
        while (line != null) {
            n++;
            line = in.readLine();
        }
        f.close();

        ArrayList<SimpleType> v = new ArrayList<>();

        f = new FileReader(filename);
        in = new BufferedReader(f);
        int i = 0;
        line = in.readLine();
        while ((line != null) && (i < n)) {
            boolean flag = true;
            if (Helper.isSimpleTypeStart(line)) {
                SimpleType el = new SimpleType();
                el.setName(Helper.extractName(line));
                line = in.readLine();
                while (!Helper.isSimpleTypeEnd(line)) {
                    if (Helper.isSimpleTypeStart(line)) {
                        flag = false;
                        break;
                    }
                    if (Helper.isDefinitionStart(line))
                        el.setDefinition(Helper.extractDefinition(line));
                    if (Helper.isRestrictionStart(line)) {
                        el.setRestrictionType(Helper.extractRestrictionName(line));
                        line = in.readLine();
                        ArrayList<String> enums = new ArrayList<String>();
                        while (!Helper.isRestrictionEnd(line)) {
                            if (!Helper.setRestriction(line, el)) {
                                if (!Helper.isEnumerationEnd(line))
                                    enums.add(Helper.extractEnumerationName(line));
                            }
                            line = in.readLine();
                        }
                        el.setEnumeration(enums);
                    }
                    line = in.readLine();
                }
                v.add(el);
            }
            if (flag) {
                line = in.readLine();
            }
        }
        f.close();
        return v;
    }

    public static void parseJsonObjects(String name, JSONObject obj, Hashtable<String, JsonObj> list) {
        JsonObj o = new JsonObj();
        o.setName(name.replace("_", "").toLowerCase(Locale.ROOT));
        o.setDescription(fixNull((String) obj.get("description")));
        o.setType(fixNull((String) obj.get("type")));
        o.setPattern(fixNull((String) obj.get("pattern")));
        o.setMaximum(fixNull(String.valueOf(obj.get("maximum"))));
        o.setMaxLength(fixNull(String.valueOf(obj.get("maxLength"))));
        o.setMinLength(fixNull(String.valueOf(obj.get("minLength"))));
        o.setMultipleOf(fixNull(String.valueOf(obj.get("multipleOf"))));
        if(null!=obj.get("enum")) {
            o.setEnums((ArrayList<String>) obj.get("enum"));
            o.toLowerCaseEnum();
        }
        list.put(o.getName(), o);
    }

    public static void compareBoth(ArrayList<SimpleType> st, Hashtable<String, JsonObj> list) throws IOException {
        ArrayList<String> names=new ArrayList<>();
        for (SimpleType xsdObj : st) {
            JsonObj jsonObj = list.get(xsdObj.getName());
            if (jsonObj != null) {
                if(matchProperties(xsdObj, jsonObj))
                    names.add(xsdObj.getName());
            }
        }
        saveResult(names);
    }

    private static void saveResult(ArrayList<String > names) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet
                = workbook.createSheet(" Student Data ");
        XSSFRow row;
        Map<String, Object[]> studentData
                = new TreeMap<>();

        studentData.put(
                "1",
                new Object[] { "Name", "Status" });

        for (int i=0; i<names.size(); i++)
            studentData.put(i+2+"", new Object[] { names.get(i), "Passed" });

        Set<String> keyid = studentData.keySet();

        int rowid = 0;

        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = studentData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }

        FileOutputStream out = new FileOutputStream(
                new File(Constants.RESULTS_FILE_PATH));

        workbook.write(out);
        out.close();

    }

    private static boolean matchProperties(SimpleType xsdObj, JsonObj jsonObj) {
        return xsdObj.getName().equalsIgnoreCase(jsonObj.getName()) && xsdObj.getDefinition().equalsIgnoreCase(jsonObj.getDescription())
                && xsdObj.getRestrictionType().equalsIgnoreCase(jsonObj.getType()) && compareRestrictions(xsdObj,jsonObj);
    }

    public static String fixNull(String str){
        String fixedStr=str;
        if(null==fixedStr || fixedStr.contains("null"))
            fixedStr= "";
        return fixedStr;
    }

    public static String fixEmptyNumber(String num){
        String fixed=num;
        if(num.equalsIgnoreCase(""))
            fixed="0";
        return fixed;
    }

    public static boolean compareRestrictions(SimpleType xsdObj, JsonObj jsonObj){
        return xsdObj.getMaxLength().equalsIgnoreCase(jsonObj.getMaxLength()) &&
                xsdObj.getMinLength().equalsIgnoreCase(jsonObj.getMinLength()) && xsdObj.getEnumeration().equals(jsonObj.getEnums()) &&
        xsdObj.getFractionDigits().equalsIgnoreCase(jsonObj.getMultipleOf().substring(jsonObj.getMultipleOf().indexOf("-")+1)) &&
                Integer.parseInt(fixEmptyNumber(xsdObj.getTotalDigits()))==jsonObj.getMaximum().replace(".","").length();
    }
}

package io.rizz.xsd;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class main {
    public static void main(String[] args) throws IOException {
        String filePath = Constants.XSD_FILE_PATH;
        ArrayList<SimpleType> res = Helper.parseFile(filePath);

        Hashtable<String, JsonObj> lst = new Hashtable<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(Constants.JSON_FILE_PATH)) {
            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            HashMap jsonObjects = (HashMap) jsonObject.get("definitions");

            jsonObjects.forEach((key, val) -> Helper.parseJsonObjects((String) key, (JSONObject) val, lst));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        Helper.compareBoth(res, lst);
    }
}

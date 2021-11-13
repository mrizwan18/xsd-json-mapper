package io.rizz.xsd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class main {

    public static ArrayList<SimpleType> parseFile (String filename) throws IOException {
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
            boolean flag=true;
            if(Helper.isSimpleTypeStart(line))
            {
                SimpleType el=new SimpleType();
                el.setName(Helper.extractName(line));
                line = in.readLine();
                while (!Helper.isSimpleTypeEnd(line))
                {
                    if(Helper.isSimpleTypeStart(line)) {
                        flag=false;
                        break;
                    }
                    if(Helper.isRestrictionStart(line)) {
                        el.setRestrictionType(Helper.extractRestrictionName(line));
                        line = in.readLine();
                        ArrayList<String> enums=new ArrayList<String>();
                        while (!Helper.isRestrictionEnd(line))
                        {
                            if(!Helper.setRestriction(line,el))
                            {
                                if(!Helper.isEnumerationEnd(line))
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
            if(flag) {
                line = in.readLine();
            }
        }
        f.close();
        return v;
    }
    public static void main(String [] args) throws IOException {
        String filePath = Constants.XSD_FILE_PATH;
        ArrayList<SimpleType> res= parseFile(filePath);
        for (SimpleType st: res)
            System.out.println(st.toString());
    }
}

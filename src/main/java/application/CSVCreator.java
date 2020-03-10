package application;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVCreator {

    private static final char Separator = '|';

    public static void write(Writer writer, List<String> stringList) throws IOException{
        writeCsv(writer, stringList, Separator, ' ');
    }

    public static void write(Writer writer, List<String>stringList, char separator)throws IOException{
        writeCsv(writer,stringList,separator, ' ');
    }


    private static String CSVform(String s){

        String v = s;
        if(v.contains("\"")){
            v = v.replace("\"", "\"\"");
        }
        return v;
    }

    public static void writeCsv(Writer writer, List<String> stringList, char separator, char zmsep) throws IOException{

        boolean bol = true;

        if(separator == ' '){
            separator = Separator;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(String val : stringList){
            if(!bol){
                stringBuilder.append(separator);
            }
            if(zmsep == ' '){
                stringBuilder.append(CSVform(val));
            }else{
                stringBuilder.append(zmsep).append(CSVform(val)).append(zmsep);
            }
            bol = false;
        }

        stringBuilder.append("\n");
        writer.append(stringBuilder.toString());
    }

}




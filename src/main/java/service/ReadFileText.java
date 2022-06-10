package service;

import model.ModelLog;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReadFileText {
    private static List<ModelLog> listModelLog;

    public ReadFileText(List<String> pathFile, JavaSparkContext javaSparkContext) throws IOException, ParseException {
        listModelLog = new ArrayList<>();
        readDataFromPath(pathFile, javaSparkContext);
    }

    public List<ModelLog> getListModelLog() {
        return listModelLog;
    }

    public void setListModelLog(List<ModelLog> listModelLog) {
        this.listModelLog = listModelLog;
    }

    public void readDataFromPath(List<String> pathFile, JavaSparkContext javaSparkContext) throws IOException, ParseException {
        for (String filename : pathFile) {
            JavaRDD<String> lines = javaSparkContext.textFile(filename);

            for (String line : lines.collect()) {
                ModelLog model = splitLine(line);
                listModelLog.add(model);
            }
        }
    }

    public ModelLog splitLine(String line) throws ParseException {
        String[] tokenizer = line.split("\t");

        Timestamp timeCreate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(tokenizer[0]).getTime());
        Timestamp cookieCreate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(tokenizer[1]).getTime());
        int browserCode = Integer.parseInt(tokenizer[2]);
        String browserVer = tokenizer[3];
        int osCode = Integer.parseInt(tokenizer[4]);
        String osVer = tokenizer[5];
        long ip = Long.parseLong(tokenizer[6]);
        int locId = Integer.parseInt(tokenizer[7]);
        String domain = tokenizer[8];
        int siteId = Integer.parseInt(tokenizer[9]);
        int cId = Integer.parseInt(tokenizer[10]);
        String path = tokenizer[11];
        String referer = tokenizer[12];
        long guid = Long.parseLong(tokenizer[13]);
        String flashVersion = tokenizer[14];
        String jre = tokenizer[15];
        String sr = tokenizer[16];
        String sc = tokenizer[17];
        int geographic = Integer.parseInt(tokenizer[18]);
        String category = tokenizer[23];
        String osName = tokenizer[5];

        return new ModelLog(timeCreate, browserCode, browserVer, osName, osCode, osVer, ip, domain,
                path, cookieCreate, guid, siteId, cId, referer, geographic, locId, flashVersion, jre, sr, sc, category);
    }
}
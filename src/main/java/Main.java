import com.sun.org.apache.xpath.internal.operations.Mod;
import model.IP;
import model.ModelLog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import service.ReadFileText;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static List<ModelLog> listModelLog = new ArrayList<>();

    public static List<String> getFiles() throws IOException {
        return Files.walk(Paths.get("src/main/java/sampletext/pt-v-1533869954405.dat"))
                .filter(Files::isRegularFile)
                .map(Path::toString).collect(Collectors.toList());
    }

    public void addTable() throws IOException, ParseException {
        Configuration config = HBaseConfiguration.create();
        config.clear();
//        config.set("zookeeper.znode.parent", "/hbase");
//        config.set("hbase.zookeeper.quorum", "192.168.1.13");
//        config.set("hbase.zookeeper.property.clientPort","2181");
        //config.set("hbase.master", "localhost:60000");

        SparkConf sparkConf = new SparkConf().setAppName("SparkHBaseTest").setMaster("local[4]");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();


        ReadFileText readFileText = new ReadFileText(Collections.singletonList("hdfs://172.18.0.2:9000/user/root/Task4/sampletext/pt-v-1533870072206.dat"), javaSparkContext);
        listModelLog.addAll(readFileText.getListModelLog());
        Table table = connection.getTable(TableName.valueOf("huan:pageviewlog"));

//        Map<Long, List<ModelLog>> stringModelLogMap = new HashMap<>();
//
//        for (ModelLog modelLog : listModelLog) {
//            if (!stringModelLogMap.containsKey(modelLog.getGuid())) {
//                stringModelLogMap.put(modelLog.getGuid(), new ArrayList<>());
//            }
//            stringModelLogMap.get(modelLog.getGuid()).add(modelLog);
//        }

        // String read model log = new String model log

        for(ModelLog modelLog : listModelLog){
            String rowKey = modelLog.getGuid() + "-" + modelLog.getTimeCreate();
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("timeCreate"),
                    Bytes.toBytes(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(modelLog.getTimeCreate())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("cookieCreate"),
                    Bytes.toBytes(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(modelLog.getCookieCreate())));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("browserCode"),
                    Bytes.toBytes(modelLog.getBrowserCode()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("browserVer"),
                    Bytes.toBytes(modelLog.getBrowserVer()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("osCode "),
                    Bytes.toBytes(modelLog.getOsCode()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("osVer "),
                    Bytes.toBytes(modelLog.getOsVer()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("ip"),
                    Bytes.toBytes(modelLog.getIp()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("locId"),
                    Bytes.toBytes(modelLog.getLocId()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("domain"),
                    Bytes.toBytes(modelLog.getDomain()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("siteId"),
                    Bytes.toBytes(modelLog.getSiteId()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("cId"),
                    Bytes.toBytes(modelLog.getcId()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("path"),
                    Bytes.toBytes(modelLog.getPath()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("referer"),
                    Bytes.toBytes(modelLog.getReferer()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("flashVersion"),
                    Bytes.toBytes(modelLog.getFlashVersion()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("jre"),
                    Bytes.toBytes(modelLog.getJre()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("sr"),
                    Bytes.toBytes(modelLog.getSr()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("sc"),
                    Bytes.toBytes(modelLog.getSc()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("geographic"),
                    Bytes.toBytes(modelLog.getGeographic()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("category"),
                    Bytes.toBytes(modelLog.getCategory()));
            table.put(put);
    }

        System.out.println("insert data succesfull");

}

    public static ResultScanner execution(Table table, String guid) throws IOException {
        Filter filter = new PrefixFilter(Bytes.toBytes(guid));
        Scan scan = new Scan();
        scan.setFilter(filter);
        return table.getScanner(scan);
    }


    public static Set<String> execution1(Table table, String guid, String dateString) throws IOException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

        Set<String> domains = new HashSet<>();
        for (Result result : execution(table, guid)) {
            Date date2 = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                            Bytes.toBytes("timeCreate"))));
            if (date2.compareTo(date1) == 0) {
                domains.add(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                        Bytes.toBytes("referer"))));
            }
        }
        return domains;
    }

    public static List<IP> execution2(Table table, String guid) throws IOException {
        List<IP> ips = new ArrayList<>();
        IP ip = null;
        for (Result result : execution(table, guid)) {
            ip = new IP(Bytes.toLong(result.getValue(Bytes.toBytes("info"),
                    Bytes.toBytes("ip"))), 1);
            if (ips.contains(ip)) {
                ips.get(ips.indexOf(ip)).setCount(ips.get(ips.indexOf(ip)).getCount() + 1);
            } else {
                ips.add(ip);
            }
        }
        ips.sort((o1, o2) -> {
            return o2.getCount() - o1.getCount();
        });
        return ips;
    }

    public static Date execution3(Table table, String guid) throws IOException, ParseException {
        int count = 0;
        Date resultDate = null;
        for (Result result : execution(table, guid)) {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .parse(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                            Bytes.toBytes("timeCreate"))));

            if (count == 0) {
                resultDate = date;
                count++;
            } else {
                if (resultDate.compareTo(date) < 0) {
                    resultDate = date;
                }
            }
        }
        return resultDate;
    }

    public static List<Result> execution4(Table table) throws IOException, ParseException {
        List<Result> results = new ArrayList<>();
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            Date timeCreate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .parse(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                            Bytes.toBytes("timeCreate"))));
            Date cookieCreate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .parse(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                            Bytes.toBytes("cookieCreate"))));
            long difference = Math.abs(timeCreate.getTime() - cookieCreate.getTime());
            if (difference <= 1800000) {
                results.add(result);
            }
        }
        return results;
    }


    public static void main(String[] args) throws IOException, ParseException {
        Configuration config = HBaseConfiguration.create();
        SparkConf sparkConf = new SparkConf().setAppName("SparkHBaseTest").setMaster("local[4]");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();

        Main main = new Main();
//        main.addTable();
        Table table = connection.getTable(TableName.valueOf("huan:pageviewlog"));
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);

        for (String str : execution1(table, "8687753366245917174", "2018-08-10")) {
            System.out.println(str);
        }

        System.out.println("--------------------------------------------------");

        for (IP ip : execution2(table, "8687753366245917174")) {
            System.out.println("Most used IP of guid 8687753366245917174 :");
            System.out.println(ip);
        }

        System.out.println("--------------------------------------------------");

        System.out.print("Last access time of guid 8687753366245917174 : ");
        System.out.println(execution3(table, "8687753366245917174"));

        System.out.println("--------------------------------------------------");

        System.out.println("Guids timeCreate - cookieCreate <= 30 min :");
        for (Result result : execution4(table)) {
            System.out.print(Bytes.toString(result.getRow()));
            System.out.println(" = " + Bytes.toString(result.getValue(Bytes.toBytes("info"),
                    Bytes.toBytes("cookieCreate"))) + " " + Bytes.toString(result.getValue(Bytes.toBytes("info"),
                    Bytes.toBytes("timeCreate"))));
        }
    }

}

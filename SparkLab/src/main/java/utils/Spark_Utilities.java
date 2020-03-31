package utils;

import org.apache.spark.Partition;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.IOException;

public class Spark_Utilities {

    //THis method will set the Hadoop home dir
    public void setHadoopHomeDir(){
        try {
        File file = new File("G:\\Coding\\Java\\spark_winutils\\");
        System.getProperties().put("hadoop.home.dir", file.getAbsolutePath());
        new File("./bin").mkdirs();
        new File("./bin/winutils.exe").createNewFile();
        System.out.println("hadoop.home.dir set successfully...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method will build the spark session and return it.
    public SparkSession getSparkSession(String appName){

        //declaring the spark session variable
        SparkSession sparksession = null;

        //creating the spark session
        sparksession = SparkSession.builder().appName(appName).master("local").getOrCreate();

        // returning the spark session
        return sparksession;

    }

    // This method will read the data from CSV file in spark dataframe
    public void readFromCSV(String csvFileName){

        //setting the hadoop home dir
        this.setHadoopHomeDir();

        //getting spark session
        SparkSession session = this.getSparkSession("read_from_csv");

        //reading the data from csv file in to dataframe
        Dataset<Row> df = session.read().format("csv").option("header", "true").load(csvFileName);
        System.out.println("Total number of rows in CSV file " + df.count());
        df.show(100);
    }

    //THis method will create JSON file from AVRO
    public void createJSONFromAVRO(String avroFilePath, String jsonFilePath){
        //setting the hadoop home dir
        this.setHadoopHomeDir();

        //getting spark session
        SparkSession session = this.getSparkSession("createJSONFromAVRO");

        //reading the data from avro file in to dataframe
        Dataset<Row> df = session.read().format("com.databricks.spark.avro")
                .option("header", "true")
                .load(avroFilePath);

        df.toJSON().write().json(jsonFilePath);
        //df.toJSON().write().save(jsonFilePath);

        System.out.println("JSON file create successfully...");
    }

    // This method will read the data from json file in spark dataframe
    public void readFromJson(String jsonFileName){

        //setting the hadoop home dir
        this.setHadoopHomeDir();

        //getting spark session
        SparkSession session = this.getSparkSession("read_from_json");

        //reading the data from json file in to dataframe
        Dataset<Row> df = session.read().format("json").option("header", "true").load(jsonFileName);
        System.out.println("Total number of rows in Json file " + df.count());
        df.show();
    }

    //This method will combine the dataframe
    public void combineDataframes(){

        String file1 = "src/main/resources/input/HPI_master.csv";
        String file2 = "src/main/resources/input/HPI_master.json";

        //setting the hadoop home dir
        this.setHadoopHomeDir();

        //getting spark session
        SparkSession session = this.getSparkSession("combine_dataframes");

        //reading data from files in dataframe
        Dataset<Row> df1 = session.read().format("csv").option("header", "true").load(file1);
        Dataset<Row> df2 = session.read().format("csv").option("header", "true").load(file1);
        //Dataset<Row> df2 = session.read().format("json").option("header", "true").load(file2);

        //combining the dataframe
        Dataset<Row> combineDF = df1.unionByName(df2);
        Partition[] partitions = combineDF.rdd().partitions();
        int partitionCount = partitions.length;

        System.out.println("Total partition count " + partitionCount);
        combineDF.show();
    }


    //This method will write AVRO file from csv
    public void convertAVRO2CSV(String avroFilePath){

        //setting the hadoop home dir
        this.setHadoopHomeDir();

        //getting spark session
        SparkSession session = this.getSparkSession("avro_2_csv");

        //reading the avro file
        Dataset<Row> df = session.read().format("com.databricks.spark.avro")
                .option("header", "true")
                .load(avroFilePath);

        df.printSchema();

        //writing the csv file
        df.write().format("csv").option("headers", "true").mode(SaveMode.Overwrite).save("src/main/resources/output/");
        System.out.println("Successfully converted avro file to csv...");
    }

    public static void main(String[] args) {
        Spark_Utilities utils = new Spark_Utilities();
        //utils.createJSONFromAVRO("src/main/resources/input/userdata5.avro", "src/main/resources/output/toJSON");
        utils.readFromCSV("src/main/resources/input/HPI_master.csv");
        //utils.readFromJson("src/main/resources/input/HPI_master.json");
        //utils.combineDataframes();
        //utils.convertAVRO2CSV("src/main/resources/input/userdata5.avro");
    }

}

package WorkingWithAvro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadWriteAvro {


    public void writeAVROFile() throws Exception{
        JSONParser jsonParser = new JSONParser();
        BitBucketSchema schema = new BitBucketSchema();
        FileReader fr = new FileReader(new File("src/test/java/WorkingWithAvro/BitBucketResponse.json"));
        Object obj = jsonParser.parse(fr);
        JSONObject jobj = new JSONObject(obj.toString());
        JSONArray arr = jobj.getJSONArray("values");

        DatumWriter<BitBucketSchema> datumWriter = new SpecificDatumWriter<BitBucketSchema>(BitBucketSchema.class);
        DataFileWriter<BitBucketSchema> fileWriter = new DataFileWriter<BitBucketSchema>(datumWriter);
        fileWriter.create(schema.getSchema(),new File("src/test/java/WorkingWithAvro/bitbucket.avro"));

        for(int i = 0; i < arr.length(); i++){
//            System.out.println(arr.getJSONObject(i).get("id"));
//            System.out.println(arr.getJSONObject(i).getJSONObject("author").get("name"));
//            System.out.println(arr.getJSONObject(i).getJSONObject("author").get("emailAddress"));
//            System.out.println(arr.getJSONObject(i).get("authorTimestamp"));

            schema.setId(arr.getJSONObject(i).get("id").toString());
            schema.setName(arr.getJSONObject(i).getJSONObject("author").get("name").toString());
            schema.setEmailAddress(arr.getJSONObject(i).getJSONObject("author").get("emailAddress").toString());
            schema.setAuthorTimestamp(arr.getJSONObject(i).get("authorTimestamp").toString());

            fileWriter.append(schema);
        }

        fileWriter.close();
    }

    public void readAVROFile()throws Exception{

        //DeSerializing the objects
        DatumReader<BitBucketSchema> empDatumReader = new SpecificDatumReader<BitBucketSchema>(BitBucketSchema.class);

        //Instantiating DataFileReader
        DataFileReader<BitBucketSchema> dataFileReader = new DataFileReader<BitBucketSchema>(new File("src/test/java/WorkingWithAvro/bitbucket.avro"), empDatumReader);
        BitBucketSchema em = null;

        while(dataFileReader.hasNext()){
            em=dataFileReader.next(em);
            System.out.println(em);
            if(em.get("emailAddress").toString().equalsIgnoreCase("sruiz@atlassian.com")){
                System.out.println("yes");
            }
        }
    }

    public static void main(String[] args) throws Exception{
        ReadWriteAvro readWriteAvro = new ReadWriteAvro();
        readWriteAvro.writeAVROFile();
        readWriteAvro.readAVROFile();
    }
}
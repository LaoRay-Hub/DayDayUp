package com.zyl;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.expressions.RowNumber;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Domain {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf();
        conf.setAppName("WordCountDemon");
        //设置master属性
        conf.setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(sc);

        wordCount1(sc,sqlContext);
//        wordCount2(sc,sqlContext);

    }

    public static void wordCount1(JavaSparkContext sc,SQLContext sqlContext) {

        JavaRDD<String> rdd1 = sc.textFile("F:\\hello.txt");
        //压扁
        JavaRDD<Row> rdd2 = rdd1.map(new Function<String, Row>() {
            @Override
            public Row call(String s) throws Exception {
                String[] arr = s.split(" ");

                return RowFactory.create(arr[0]);

            }
        });
        for (Row s : rdd2.collect()) {
            System.out.println(s);
        }

        List<StructField> structFilelds = new ArrayList<StructField>();
        structFilelds.add(DataTypes.createStructField("name", DataTypes.StringType, true));
        StructType structType = DataTypes.createStructType(structFilelds);

        // 第三步，使用动态构造的元数据，将rdd转换为dataframe
        Dataset<Row> studentDF = sqlContext.createDataFrame(rdd2, structType);

        StructType schemaWithId = studentDF.schema().add(DataTypes.createStructField("id", DataTypes.IntegerType, true));

//        RDD<Tuple2<Row, Object>> tuple2RDD = studentDF.rdd().zipWithIndex();
//
//
//        tuple2RDD.toJavaRDD().map(new Function<Tuple2<Row, Object>,Tuple2<Row, Object>>() {
//            @Override
//            public Tuple2<Row, Object> call(Tuple2<Row, Object> rowObjectTuple2) throws Exception {
//
//
//                rowObjectTuple2.
//                return null;
//            }
//        });

        studentDF.registerTempTable("students");
        Dataset<Row> sql = sqlContext.sql("select row_number() over(order by name desc) as id,* from students");

        List<Row> collect = sql.javaRDD().collect();

        for (Row row : collect) {
            System.out.println(row);
        }

        studentDF.printSchema();


//
//
//        studentDF.printSchema();
//
//        studentDF.registerTempTable("students");
//
//        Dataset<Row> teenagerDF = sqlContext.sql("select * from students");
//
//        List<Row> rows = teenagerDF.javaRDD().collect();
//
//        for (Row row : rows){
//            System.out.println(row);
//        }

        sc.close();


    }

    public static void wordCount2(JavaSparkContext sc,SQLContext sqlContext) {

        JavaRDD<String> rdd1 = sc.textFile("F:\\hello.txt");
        //压扁
        JavaRDD<String> rdd2 = rdd1.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                List<String> list = new ArrayList<String>();
                String[] arr = s.split(" ");
                for(String ss : arr){
                    list.add(ss) ;
                }
                return list.iterator() ;
            }
        });
        for (String s : rdd2.collect()) {
            System.out.println(s);
        }

        //映射
        JavaPairRDD<String,Integer> rdd3 = rdd2.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s,1);
            }
        });
        //聚合
        JavaPairRDD<String,Integer> rdd4 = rdd3.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        //收集,打印输出
        for(Object o : rdd4.collect()){
            System.out.println(o);
        }
    }


}

package com.zyl

import com.zyl.util.ModelCallingClient
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{LongType, StructField}
object Domain1 {

 def main(args:Array[String]):Unit = {

   val conf = new SparkConf().setAppName("MyScalaWordCount").setMaster("local")
   //创建一个SparkContext对象
   val sc = new SparkContext(conf)

     val spark = SparkSession
       .builder()
       .appName(this.getClass.getSimpleName).master("local")
       .getOrCreate()

   perform(spark)

   }

  def perform(spark:SparkSession):Unit={

    val df = spark.createDataFrame(Seq(
      ("ming", 20, 15552211521L),
      ("hong", 19, 13287994007L),
      ("zhi", 21, 15552211523L)
    )) toDF("name", "age", "phone")


     df.show()

    val sourceSchema = df.schema.add(StructField("sourceColId", LongType))

    val dfRDD: RDD[(Row, Long)]= df.rdd.zipWithIndex()
    val rowRDD: RDD[Row] = dfRDD.map(tp => Row.merge(tp._1, Row(tp._2)))

    // 将添加了索引的RDD 转化为DataFrame
    val sourceDfWithId = spark.createDataFrame(rowRDD, sourceSchema)
    sourceDfWithId.show()

    val list = sourceDfWithId.select("name").rdd.map(r => r.getString(0)).collect.toList

    import scala.collection.JavaConverters._
    val client = new ModelCallingClient
    val listAfterDomain= client.getDomain(list.asJava).asScala.toList

    println("list0---->" + listAfterDomain)


    import spark.implicits._
    val dfAfterDomain = listAfterDomain.toDF("targetCol")
    val targetSchema = dfAfterDomain.schema.add(StructField("targetColId", LongType))

    val df2RDD: RDD[(Row, Long)] = dfAfterDomain.rdd.zipWithIndex()
    val row2RDD: RDD[Row] = df2RDD.map(tp => Row.merge(tp._1, Row(tp._2)))
    // 将添加了索引的RDD 转化为DataFrame
    val dfAfterDomainWithId = spark.createDataFrame(row2RDD, targetSchema)
//    dfAfterDomainWithId.show()

    val finalDf = dfAfterDomainWithId.join(sourceDfWithId).where(sourceDfWithId.col("sourceColId").equalTo(dfAfterDomainWithId.col("targetColId")))
//    dfAfterDomainWithId.show()
//    dfAfterDomainWithId.printSchema()

    val frame = finalDf.drop("sourceColId","targetColId")

    frame.show()
    frame.printSchema()

    println("end")
//    listAfterDomain
//    val spark = pec.get[SparkSession]()
//
//    val sourceDf = in.read()
//    sourceDf.show()
//    val sourceSchema = sourceDf.schema.add(StructField("sourceColId", LongType))
//
//    val dfRDD: RDD[(Row, Long)]= sourceDf.rdd.zipWithIndex()
//    val rowRDD: RDD[Row] = dfRDD.map(tp => Row.merge(tp._1, Row(tp._2)))
//
//    // 将添加了索引的RDD 转化为DataFrame
//    val sourceDfWithId = spark.createDataFrame(rowRDD, sourceSchema)
//    val list = sourceDfWithId.select(sourceCol).rdd.map(r => r.getString(0)).collect.toList
//
//    import scala.collection.JavaConverters._
//    val listAfterDomain= ModelCallingClient.getDomain(list.asJava).asScala.toList
//    import spark.implicits._
//    val dfAfterDomain = listAfterDomain.toDF()
//    val targetSchema = dfAfterDomain.schema.add(StructField("targetColId", LongType))
//
//    val df2RDD: RDD[(Row, Long)] = dfAfterDomain.rdd.zipWithIndex()
//    val row2RDD: RDD[Row] = df2RDD.map(tp => Row.merge(tp._1, Row(tp._2)))
//    // 将添加了索引的RDD 转化为DataFrame
//    val dfAfterDomainWithId = spark.createDataFrame(row2RDD, targetSchema)
//    val finalDf = dfAfterDomainWithId.join(sourceDfWithId).where(sourceDfWithId.col("sourceColId").equalTo(dfAfterDomainWithId.col("targetColId")))
//
//    finalDf.show()
//    out.write(finalDf)

  }



}

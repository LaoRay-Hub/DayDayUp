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
      ("上海医药集团股份有限公司(简称“上海医药”)发布2018年第一季度报告。公告显示，报告期内公司实现营业收入363.86", 19, 13287994007L),
      ("我国60岁以上老年人口占比18.7%，属于轻度老龄化5月17日，国家统计局新闻发言人付凌晖在回应记者关于最新人口数据时表示，我国目前处于轻度老龄化状态。付凌晖说，社会上目前有一些误解，不少人认为中国已经进入到深度老龄化社会。实际上，国际上一般有这样的认识：一个国家60岁以", 21, 15552211523L)
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
    val client: ModelCallingClient= new ModelCallingClient()

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

    val sourceSchema1 = frame.schema.add(StructField("sourceColId", LongType))

    val dfRDD1: RDD[(Row, Long)]= frame.rdd.zipWithIndex()
    val rowRDD1: RDD[Row] = dfRDD1.map(tp => Row.merge(tp._1, Row(tp._2)))

    // 将添加了索引的RDD 转化为DataFrame
    val sourceDfWithId1 = spark.createDataFrame(rowRDD1, sourceSchema1)
    val list1 = sourceDfWithId1.select("name").rdd.map(r => r.get(0).toString).collect.toList


    val client1= new ModelCallingClient()
    val listAfterEvent1= client1.getEvent(list1.asJava).asScala.toList
    println("listAfterEvent1"+listAfterEvent1)
    val dfAfterEvent1 = listAfterEvent1.toDF("targetCol1")

    val targetSchema1 = dfAfterEvent1.schema.add(StructField("targetColId", LongType))

    val df2RDD1: RDD[(Row, Long)] = dfAfterEvent1.rdd.zipWithIndex()
    val row2RDD1: RDD[Row] = df2RDD1.map(tp => Row.merge(tp._1, Row(tp._2)))
    // 将添加了索引的RDD 转化为DataFrame
    val dfAfterEventWithId = spark.createDataFrame(row2RDD1, targetSchema1)
    val finalDf1 = dfAfterEventWithId.join(sourceDfWithId1).where(sourceDfWithId1.col("sourceColId").equalTo(dfAfterEventWithId.col("targetColId")))
    val frame1 = finalDf1.drop("sourceColId", "targetColId")
    frame1.show()
    frame1.printSchema()



    val sourceSchema2 = frame1.schema.add(StructField("sourceColId", LongType))

    val dfRDD2: RDD[(Row, Long)]= frame1.rdd.zipWithIndex()
    val rowRDD2: RDD[Row] = dfRDD2.map(tp => Row.merge(tp._1, Row(tp._2)))

    // 将添加了索引的RDD 转化为DataFrame
    val sourceDfWithId2 = spark.createDataFrame(rowRDD2, sourceSchema2)
    val list2 = sourceDfWithId2.select("name").rdd.map(r => r.get(0).toString).collect.toList


    val client2= new ModelCallingClient()
    val listAfterEvent2= client2.getEntity1(list2.asJava).asScala.toList
    println("listAfterEvent1"+listAfterEvent2)
    val dfAfterEvent2 = listAfterEvent2.toDF("targetCol2")

    val targetSchema2 = dfAfterEvent2.schema.add(StructField("targetColId", LongType))

    val df2RDD2: RDD[(Row, Long)] = dfAfterEvent2.rdd.zipWithIndex()
    val row2RDD2: RDD[Row] = df2RDD2.map(tp => Row.merge(tp._1, Row(tp._2)))
    // 将添加了索引的RDD 转化为DataFrame
    val dfAfterEventWithId1 = spark.createDataFrame(row2RDD2, targetSchema2)
    val finalDf2 = dfAfterEventWithId1.join(sourceDfWithId2).where(sourceDfWithId2.col("sourceColId").equalTo(dfAfterEventWithId1.col("targetColId")))
    val frame2 = finalDf2.drop("sourceColId", "targetColId")
    frame2.show()
    frame2.printSchema()


    println("end1")
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

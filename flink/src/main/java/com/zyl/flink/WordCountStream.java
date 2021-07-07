package com.zyl.flink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class WordCountStream {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        String inputPath="E:\\WorkSpace\\flink-study\\src\\main\\resources\\hello.txt";
        DataStream<String> inputDataStream = env.readTextFile(inputPath);

        DataStream<Tuple2<String, Integer>> resultStream=inputDataStream.flatMap(new WordCountBatch.MyFlatMapper())
                .keyBy(0)
                .sum(1);

        resultStream.print();

        env.execute();
    }
}

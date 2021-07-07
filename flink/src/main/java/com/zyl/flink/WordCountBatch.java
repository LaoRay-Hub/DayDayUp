package com.zyl.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class WordCountBatch {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String inputPath="E:\\WorkSpace\\flink-study\\src\\main\\resources\\hello.txt";
        DataSource<String> inputDataSet = env.readTextFile(inputPath);

        DataSet<Tuple2<String, Integer>> resutlSet = inputDataSet.flatMap(new MyFlatMapper())
                .groupBy(0)
                .sum(1);

        resutlSet.print();

    }

    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String,Integer>>{

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) {
            String[] s1 = s.split(" ");

            for (String s2 : s1) {

                collector.collect(new Tuple2<>(s2,1));
            }
        }
    }
}

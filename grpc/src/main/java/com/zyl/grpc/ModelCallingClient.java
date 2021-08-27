package com.zyl.grpc;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.SettableFuture;
import com.google.errorprone.annotations.Var;
import com.google.protobuf.TextFormat;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.newsmodel.JSONString;
import io.grpc.examples.newsmodel.ModelCallingGrpc;
import io.grpc.examples.newsmodel.NewsModelProto;
import io.grpc.examples.newsmodel.SentenceList;

import io.grpc.stub.StreamObserver;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static java.awt.SystemColor.info;

public class ModelCallingClient {

//    领域模型算法
    public static void main(String[] args) throws Exception  {
        // STEP1 构造 Channel 和 BlockingStub
        System.out.println("领域domain-------------------");
        getDomain();

        System.out.println("标题事件event-------------------");
        getEvent();

        System.out.println("公司实体entity-------------------");
        getEntity();


    }

    public static void getEntity() {
        SettableFuture<Object> finishFuture  = SettableFuture.create();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.200.141", 50053)
                .usePlaintext()
                .build();

        ModelCallingGrpc.ModelCallingStub asyncStub = ModelCallingGrpc.newStub(channel);
        StreamObserver<JSONString> responseObserver = new StreamObserver<JSONString>() {


            @Override
            public void onNext(JSONString jsonString) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = JSONObject.parseObject(jsonString.getEntity());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("输出："+jsonObject);

//                TextFormat.printToUnicodeString

                System.out.println("输出："+jsonString.getEntity());

            }

            @Override
            public void onError(Throwable throwable) {
                finishFuture.setException(throwable);
            }

            @Override
            public void onCompleted() {
                finishFuture.set(null);
            }
        };

        StreamObserver<SentenceList> requestObserver  = asyncStub.getEntity(responseObserver);
        try {
            String data ="挖顶管公司自成立以来，利用定向钻技术穿越总长";
//            SentenceList build = SentenceList.newBuilder().setSentence(0, data).setSentence(1,"1").build();
            List<String> str = new ArrayList<>();
            str.add("挖顶管公司自成立以来，利用定向钻技术穿越总长");
            str.add("2");
            str.add("凤台县专业顶管队伍联系电话&nbsp;&nbsp;立新宏昌非开挖顶管公司自成立以来，利用定向钻技术穿越总长度50多万米");
            str.add("我国60岁以上老年人口占比18.7%，属于轻度老龄化5月17日，国家统计局新闻发言人付凌晖在回应记者关于最新人口数据时表示，我国目前处于轻度老龄化状态。付凌晖说，社会上目前有一些误解，不少人认为中国已经进入到深度老龄化社会。实际上，国际上一般有这样的认识：一个国家60岁以上人口占全部人口的比重超过10%，这个国家进入到老龄化社会，10%到20%之间属于轻度老龄化，20%到30%之间为中度老龄化，超过30%是重度老龄化。这次人口普查，我国60岁以上老年人口占全部人口的比重是18.7%，说明目前中国还是属于轻度老龄化的状态。");
            str.add( "上海医药集团股份有限公司(简称“上海医药”)发布2018年第一季度报告。公告显示，报告期内公司实现营业收入363.86亿元(币种为人民币，下同)，同比增长9.83%;实现归母净利润10.2亿元，同比增长2.07%;归属于上市公司股东的扣除非经常性损益的净利润9.96亿元，同比增长6.08%;经营性现金流净流入0.96亿元。同时，为应对行业趋势、把握产业机遇，上海医药制定了以“顺应产业变革、加快转型发展、力争行业领先”为主旨的工作方针，围绕集约化、创新化、国际化、融产结合四大战略发展举措，持续推进多项重点工作，进一步提升作为工商业龙头企业的核心竞争力。");
            str.add("黄铁路（黄骅港段）扩能改造工程开工建设:12月16日，邯黄铁路（黄骅港段）扩能改造工程开工仪式在沧州渤海新区举行。沧州市委常委、常务副市长和春军出席仪式并宣布邯黄铁路（黄骅港段）扩能改造工程项目开工。沧州渤海新区党工委委员、管委会副主任刘兆忠出席仪式并致辞。他说，邯黄铁路是拉动河北沿海经济的重要引擎，是黄骅港集疏运体系的重要组成部分。此次扩能改造可以系统提升邯黄铁路黄骅港运输能力，将为港产城高质量融合发展、助力沧州沿海经济带建设增添新动能。渤海新区全力支持邯黄铁路发展，区直有关部门要密切配合、精诚合作，大力支持邯黄铁路建设和运营发展，为渤海新区高质量发展做出更大贡献。河北建投集团公司党委副书记、副总经理林士炎致辞。他说，邯黄铁路自运营以来始终倾注了沧州市及渤海新区各级领导的心血，扩能改造工程要切实抓好统筹工期，优化施工方案，严把质量关，建成经得起历史检验的优质工程。邯黄铁路有限责任公司党委书记、董事长左站峰主持仪式。");


            SentenceList builder = SentenceList.newBuilder().addAllSentence(str).build();

            SentenceList[] requests ={builder};

            for (SentenceList request : requests) {
                requestObserver.onNext(request);
                System.out.println("输入："+request.getSentence(0));
                System.out.println("输入："+request.getSentence(1));
                System.out.println("输入："+request.getSentence(2));
                System.out.println("输入："+request.getSentence(3));
                System.out.println("输入："+request.getSentence(4));
                System.out.println("输入："+request.getSentence(5));
            }
            requestObserver.onCompleted();
            finishFuture.get();
        } catch (Exception e) {
            requestObserver.onError(e);
//            throw e;
        }

    }

    public static void getEvent() throws ExecutionException, InterruptedException {
        SettableFuture<Object> finishFuture  = SettableFuture.create();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.200.141", 50052)
                .usePlaintext()
                .build();

        ModelCallingGrpc.ModelCallingStub asyncStub = ModelCallingGrpc.newStub(channel);
        StreamObserver<SentenceList> responseObserver = asyncStub.getEvent(new StreamObserver<SentenceList>() {
            @Override
            public void onNext(SentenceList sentenceList) {
                System.out.println("输出："+sentenceList.getSentence(0));
                System.out.println("输出："+sentenceList.getSentence(1));
                System.out.println("输出："+sentenceList.getSentence(2));
                System.out.println("输出："+sentenceList.getSentence(3));
            }

            @Override
            public void onError(Throwable throwable) {
                finishFuture.setException(throwable);
            }

            @Override
            public void onCompleted() {
                finishFuture.set(null);
            }
        });

        try {
            String data ="挖顶管公司自成立以来，利用定向钻技术穿越总长";
//            SentenceList build = SentenceList.newBuilder().setSentence(0, data).setSentence(1,"1").build();
            List<String> str = new ArrayList<>();
            str.add("aaa");
            str.add("我国60岁以上老年人口占比18.7%，属于轻度老龄化5月17日，国家统计局新闻发言人付凌晖在回应记者关于最新人口数据时表示，我国目前处于轻度老龄化状态。付凌晖说，社会上目前有一些误解，不少人认为中国已经进入到深度老龄化社会。实际上，国际上一般有这样的认识：一个国家60岁以上人口占全部人口的比重超过10%，这个国家进入到老龄化社会，10%到20%之间属于轻度老龄化，20%到30%之间为中度老龄化，超过30%是重度老龄化。这次人口普查，我国60岁以上老年人口占全部人口的比重是18.7%，说明目前中国还是属于轻度老龄化的状态。");
            str.add( "上海医药集团股份有限公司(简称“上海医药”)发布2018年第一季度报告。公告显示，报告期内公司实现营业收入363.86亿元(币种为人民币，下同)，同比增长9.83%;实现归母净利润10.2亿元，同比增长2.07%;归属于上市公司股东的扣除非经常性损益的净利润9.96亿元，同比增长6.08%;经营性现金流净流入0.96亿元。同时，为应对行业趋势、把握产业机遇，上海医药制定了以“顺应产业变革、加快转型发展、力争行业领先”为主旨的工作方针，围绕集约化、创新化、国际化、融产结合四大战略发展举措，持续推进多项重点工作，进一步提升作为工商业龙头企业的核心竞争力。");
            str.add("黄铁路（黄骅港段）扩能改造工程开工建设:12月16日，邯黄铁路（黄骅港段）扩能改造工程开工仪式在沧州渤海新区举行。沧州市委常委、常务副市长和春军出席仪式并宣布邯黄铁路（黄骅港段）扩能改造工程项目开工。沧州渤海新区党工委委员、管委会副主任刘兆忠出席仪式并致辞。他说，邯黄铁路是拉动河北沿海经济的重要引擎，是黄骅港集疏运体系的重要组成部分。此次扩能改造可以系统提升邯黄铁路黄骅港运输能力，将为港产城高质量融合发展、助力沧州沿海经济带建设增添新动能。渤海新区全力支持邯黄铁路发展，区直有关部门要密切配合、精诚合作，大力支持邯黄铁路建设和运营发展，为渤海新区高质量发展做出更大贡献。河北建投集团公司党委副书记、副总经理林士炎致辞。他说，邯黄铁路自运营以来始终倾注了沧州市及渤海新区各级领导的心血，扩能改造工程要切实抓好统筹工期，优化施工方案，严把质量关，建成经得起历史检验的优质工程。邯黄铁路有限责任公司党委书记、董事长左站峰主持仪式。");



            SentenceList builder = SentenceList.newBuilder().addAllSentence(str).build();

            SentenceList[] requests ={builder};

            for (SentenceList request : requests) {
                responseObserver.onNext(request);
                System.out.println("输入："+request.getSentence(0));
                System.out.println("输入："+request.getSentence(1));
                System.out.println("输入："+request.getSentence(2));
                System.out.println("输入："+request.getSentence(3));
            }
            responseObserver.onCompleted();
            finishFuture.get();
        } catch (Exception e) {
            responseObserver.onError(e);
            e.printStackTrace();
//            throw e;
        }

    }


    public static void getDomain() throws ExecutionException, InterruptedException {
        SettableFuture<Object> finishFuture  = SettableFuture.create();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.200.141", 50051)
                .usePlaintext()
                .build();

        ModelCallingGrpc.ModelCallingStub asyncStub = ModelCallingGrpc.newStub(channel);
        StreamObserver<SentenceList> responseObserver = asyncStub.getDomain(new StreamObserver<SentenceList>() {
            @Override
            public void onNext(SentenceList sentenceList) {

                System.out.println("输出："+sentenceList.getSentence(0));
                System.out.println("输出："+sentenceList.getSentence(1));
                System.out.println("输出："+sentenceList.getSentence(2));
            }

            @Override
            public void onError(Throwable throwable) {
                finishFuture.setException(throwable);
            }

            @Override
            public void onCompleted() {
                finishFuture.set(null);
            }
        });

        try {
            String data ="挖顶管公司自成立以来，利用定向钻技术穿越总长";
//            SentenceList build = SentenceList.newBuilder().setSentence(0, data).setSentence(1,"1").build();
            List<String> str = new ArrayList<>();
            str.add("");
            str.add("凤台县专业顶管队伍联系电话&nbsp;&nbsp;立新宏昌非开挖顶管公司自成立以来，利用定向钻技术穿越总长度50多万米");
            str.add("挖顶管公司自成立以来，利用定向钻技术穿越总长");

            SentenceList builder = SentenceList.newBuilder().addAllSentence(str).build();

            SentenceList[] requests ={builder};

            for (SentenceList request : requests) {
                responseObserver.onNext(request);
                System.out.println("输入："+request.getSentence(0));
                System.out.println("输入："+request.getSentence(1));
                System.out.println("输入："+request.getSentence(2));
            }
            responseObserver.onCompleted();
            finishFuture.get();
        } catch (Exception e) {
            responseObserver.onError(e);
            throw e;
        }

    }

}

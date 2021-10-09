package com.zyl.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


//-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
public class JvmDemo5 {

    public static void main(String[] args) {
        long count=0;
        while (true){
            System.out.println("创建Car类的子类的个数："+(++count));
            Enhancer enhancer=new Enhancer();
            enhancer.setSuperclass(Car.class);
            //  关闭CGLib缓存，否则总是生成同一个类
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    if("run".equals(method.getName())){
                        System.out.println("汽车启动前，先进行安全检查...");
                    }
                    return methodProxy.invokeSuper(o,objects);

                }
            });

            Car car=(Car) enhancer.create();
            car.run();
        }
    }

    static class Car{

        public void run(){
            System.out.println("汽车启动，开始行驶.....");
        }
    }

}




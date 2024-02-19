package com.xxl.hello.service.proxy;

import com.xxl.kit.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用targetSdkVersion 25 源码看Proxy#newProxyInstance源码比较清晰，高版本API变动较大不容易看
 *
 * @author xxl.
 * @date 2024/2/19.
 */
public class TestProxy {

    public interface Hello {
        void sayHello();
    }

    public static class HelloImp implements Hello {

        @Override
        public void sayHello() {
            LogUtils.d("aaa" + " hello");
        }
    }

    public static class HelloInvocationHandler implements InvocationHandler {
        private Object mTarget;

        public HelloInvocationHandler(Object target) {
            mTarget = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            LogUtils.d("aaa" + " before");
            Object result = method.invoke(mTarget, args);
            LogUtils.d("aaa" + " after");
            return result;
        }
    }

    public static void test() {
        HelloImp helloImp = new HelloImp();
        HelloInvocationHandler handler = new HelloInvocationHandler(helloImp);
        Hello hello = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class[]{Hello.class}, handler);
        hello.sayHello();
    }
}
// ITestResponse.aidl
package com.xxl.hello.main;

// Declare any non-default types here with import statements

interface ITestResponse {

   // 开始
   void onStart(String message);

   // 完成
   void onComplete(String result);

}

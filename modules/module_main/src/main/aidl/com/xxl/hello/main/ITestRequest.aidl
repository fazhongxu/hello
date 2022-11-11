// ITestRequest.aidl
package com.xxl.hello.main;

import com.xxl.hello.main.ITestResponse;
// Declare any non-default types here with import statements

interface ITestRequest {

   // 注册回调
   void registerCallBack(ITestResponse response);
}

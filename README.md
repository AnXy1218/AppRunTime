### 统计Android前台应用保持时长
##### 目录结构
```
├── app
│   ├── build
│   ├── libs
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   ├── com
│   │   │   │   │   ├── test
│   │   │   │   │   │   ├── apprun
│   │   │   │   │   │   │   ├── model
│   │   │   │   │   │   │   │   ├── UserLevel.java
│   │   │   │   │   │   │   ├── ui
│   │   │   │   │   │   │   │   ├── activity
│   │   │   │   │   │   │   │   │   ├── IconActivity.java
│   │   │   │   │   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   │   │   │   ├── adapter
│   │   │   │   │   │   │   │   │   ├── IconAdapter.java
│   │   │   │   │   │   │   ├── utils
│   │   │   │   │   │   │   │   ├── ApiUtils.java                               //定义一些常量
│   │   │   │   │   │   │   │   ├── SystemTool.java                             //监听第三方app运行工具
│   │   │   │   │   │   │   │   ├── UsageComparator.java                        //5.0以上统计时间排序
│   │   │   │   │   │   ├── service
│   │   │   │   │   │   │   ├── MyAppService.java                                //监听第三方app运行情况的Service
│   │   │   ├── res
│   ├── .gitignore
│   ├── build.gradle
│   ├── proguard-rules.pro
├── gradle
├── img
├── .gitignore
├── build.gradle
├── gradle.properties
├── gradlew
├── settings.gradle
```
##### 运行效果
![](img/rusult.gif)

##### 相关介绍：[Android跳转到第三方app，运行时长监听](https://www.jianshu.com/p/211b48966b0f)
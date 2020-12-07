[TOC]

# Route
## 项目介绍
该项目只是简单的组件化开发的示例，搭建了组件化开发的雏形。 暂时没有实现内容的开发工作，目前实现功能有： 网络模块的封装、ARoute的使用方法、登陆功能的实现（UI未实现）。

## 组件划分
- App 模块
- module_basis 基础模块
- module_login  登陆模块
- module_hilt  hilt使用示例模块
- module_version  依赖版本管理模块

## App 模块
程序入口。
	跳转到登陆模块 （ARoute 阿里路由开源框架）
1. 在 App目录下的 gradle	每个模块都需导入该设置

```groovy

    apply plugin: 'kotlin-kapt'

     defaultConfig {
         kapt {
             arguments {
                 arg("AROUTER_MODULE_NAME", project.getName())
             }
         }
     }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }


    dependencies {
    
        // 路由框架
        implementation  'com.alibaba:arouter-api:1.5.1'
        kapt 'com.alibaba:arouter-compiler:1.5.1'
    
        //导入其他模块
        implementation project(path: ':module_basis')
        implementation project(path: ':module_login')
        implementation project(path: ':module_hilt')
    }
```

2. 在 application 中初始化	

```kotlin

     private fun initARouter() {
        if (BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
       ARouter.init(this)
    }

```

3. 定义 路由

```kotlin

    object RoutePath{
        // 最少两级目录
        const val pageMain = "/app/mainActivity"
        const val pageLogin = "/login/loginActivity"
        const val pageHilt = "/hilt/HiltActivity"
    }

```

4. Activity中使用

```kotlin

    // 注解当前  Activity ，用于跳转
    @Route(path = RoutePath.pageMain)
    class MainActivity : BaseActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

                jumpModuleLogin.setOnClickListener {
                    //跳转到目标页面
                    ARouter.getInstance().build(RoutePath.pageLogin).navigation()
                }
        }

    }

    // 注解当前  Activity ，用于跳转
    @Route(path = RoutePath.pageLogin)
    class LoginActivity : BaseActivity() {}

```

## 基础模块
- 封装网络请求功能 kotlin 扩展功能
- toast 常量定义类

## 登陆模块
实现登陆请求返回功能（UI 未实现）

## hilt使用示例模块
 参考文章： https://mp.weixin.qq.com/s/OEX1d2cU1zGG5BBM-nANBg
1. 在 根项目的gradle中

```groovy
    classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha'
```

2. 在app 项目的gradle中

```groovy
   apply plugin: 'kotlin-android'
   apply plugin: 'kotlin-android-extensions'
   apply plugin: 'kotlin-kapt'
   apply plugin: 'dagger.hilt.android.plugin'

    compileOptions {
           sourceCompatibility JavaVersion.VERSION_1_8
           targetCompatibility JavaVersion.VERSION_1_8
       }

   implementation :'com.google.dagger:hilt-android:2.30.1-alpha'
   kapt: 'com.google.dagger:hilt-android-compiler:2.30.1-alpha'
   implementation: 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02'
   kapt: 'androidx.hilt:hilt-compiler:1.0.0-alpha02'

```
  
3. 在Application中注解  

```kotlin
    @HiltAndroidApp
    class MyApplication : Application(){
    }
```

4. 在Activity 中注解 
   
```kotlin
    @AndroidEntryPoint
    class MainActivity : BaseActivity(){
       
        //创建viewModel  常规方式
        private val viewModel:HiltViewModel by lazy { ViewModelProvider(this).get(HiltViewModel::class.java) }
        
        // 注入其他一般类
        @Inject
        lateinit var adapter: MyAdapter

    }

    // 定义并使用注解  @Inject
    class MyAdapter @Inject constructor() : Adapter(){
    
    }   
```   

5. 创建 HiltViewModel 注解 @ViewModelInject

```kotlin
   class HiltViewModel @ViewModelInject constructor( val repository: HiltRepository) : ViewModel() {}
```

6. 创建 HiltRepository 注解 @Inject

```kotlin
   class HiltRepository @Inject constructor(){}
```

7. 注入Retrofit （ 在 HiltRepository 中使用 ，而在 HiltRetrofit 中告诉 hilt 怎么实例化）
   
```kotlin
    @Inject
    lateinit var apiService: ApiService
    //此时就可以使用 apiService 了
```
   
8. 创建 HiltRetrofit

```kotlin
    @Module
    @InstallIn(SingletonComponent::class) // 全局范围
    object HiltRetrofit {

        @Singleton // 单例
        @Provides
        fun provideApiService():ApiService {
            return Retrofit.Builder()
                .baseUrl("http://45.32.43.43")
                .build()
                .create(ApiService::class.java)
        }

        @Singleton
        @Provides
        fun provideRetrofit(): ApiService {
            // RetrofitClient 自己封装的 Retrofit ；与上面效果相同
            return RetrofitClient.instance.onCreateApiService(ApiService::class.java)
        }
    }
```

## module_version  依赖版本管理模块
1. 创建一个新 Module ， 名为：module_version
   可以只保留 "包名" 下的文件 和 build.gradle， 其他文件可删除（如 res，lib）
   
2. 修改 本模块下的build.gradle

```groovy

buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10"
    }
}

apply plugin: 'kotlin'
apply plugin: 'java-gradle-plugin'

repositories {
    jcenter()
    google()
}

dependencies {
    implementation gradleApi()
    implementation "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10"
}

compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
compileTestKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

gradlePlugin {
    plugins {
        version {
            // 包名
            id = 'com.component.module_version'
            //在根目录下创建类 DependencyVersionPlugin 继承 Plugin<Project> 
            implementationClass = 'com.component.module_version.DependencyVersionPlugin'
        }
    }
}

```
3. 创建依赖库文件 BuildsVersion

```kotlin

object BuildsVersion {
    val compileSdkVersion = 30
    val buildToolsVersion = "30.0.2"
    val minSdkVersion = 22
    val targetSdkVersion = 30
    val versionCode = 1
    val versionName = "1.0"
}

```

其他依赖项, 可根据自己意愿分类引入, 如定义 http 依赖文件

```kotlin

object DepHttp {
    val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    val gson = "com.google.code.gson:gson:2.8.6"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.0.0"
}

```

4. 在根目录的 settings.gradle文件中添加

```groovy

    // 依赖版本模块
 includeBuild("module_version")

```

5. 在需要的 module  build.gradle中 添加使用

```groovy

// 在文件 build.gradle 的第一行添加
plugins{
    // 包名
    id "com.component.module_version"
}

// 定义的依赖地址
import com.component.module_version.*

```

6. 添加依赖

```groovy

    // 版本依赖
    compileSdkVersion BuildsVersion.compileSdkVersion
    buildToolsVersion BuildsVersion.buildToolsVersion

    // Retrofit 等依赖
    implementation DepHttp.retrofit
    implementation DepHttp.gson

```

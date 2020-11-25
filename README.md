# Route
## 项目介绍
该项目只是简单的组件化开发的示例，搭建了组件化开发的雏形。 暂时没有实现内容的开发工作，目前实现功能有： 网络模块的封装、ARoute的使用方法、登陆功能的实现（UI未实现）。
## 组件划分
- App 模块
- moduel_basis 基础模块
- moduel_login  登陆模块
- moduel_hilt  hilt使用示例模块
## App 模块
程序入口。
	跳转到登陆模块
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

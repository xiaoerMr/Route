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
- 在 根项目的gradle中
     classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha'
- 在app 项目的gradle中
   apply plugin: 'kotlin-android'
   apply plugin: 'kotlin-android-extensions'
   apply plugin: 'kotlin-kapt'
   apply plugin: 'dagger.hilt.android.plugin'

    compileOptions {
           sourceCompatibility JavaVersion.VERSION_1_8
           targetCompatibility JavaVersion.VERSION_1_8
       }

   implementation :'com.google.dagger:hilt-android:2.30.1-alpha',
   kapt: 'com.google.dagger:hilt-android-compiler:2.30.1-alpha',
   implementation: 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02',
   kapt: 'androidx.hilt:hilt-compiler:1.0.0-alpha02',

- 在Application中注解  @HiltAndroidApp
- 在Activity 中注解 @AndroidEntryPoint
   创建viewModel  常规方式
   private val viewModel:HiltViewModel by lazy { ViewModelProvider(this).get(HiltViewModel::class.java) }
- 创建 HiltViewModel 注解 @ViewModelInject
   class HiltViewModel @ViewModelInject constructor( val repository: HiltRepository) : ViewModel() {}
- 创建 HiltRepository 注解 @Inject
   class HiltRepository @Inject constructor(){}

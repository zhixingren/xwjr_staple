# xwjr_staple
### 简介
本库具有开屏、活动、升级、推送功能集成

### 导包
    
    implementation 'com.github.zhuxiaohang2016:xwjr_staple:版本号'

### 配置
    
    StapleConfig.INSTANCE.setAppSource(StapleConfig.APPHUB);//哪个app
    StapleConfig.INSTANCE.setDebug(true);//是否是debug模式
    JPushInterface.setDebugMode(true);//jpush是否是debug模式
    JPushInterface.init(this);//jpush初始化
    
gradle配置
    
    android{
        defaultConfig{
            manifestPlaceholders = [
                JPUSH_PKGNAME: applicationId,
                JPUSH_APPKEY : "*****************", //JPush 上注册的包名对应的 Appkey.
                JPUSH_CHANNEL: "developer-default", //暂时填写默认值即可.
             ]
          }
    }
    
 ### 使用
 
 1.自定义SplashActivity 继承  StapleSplashActivity，重写customDealActivityData,customDealActivityData方法会返回需要弹出的活动数据，自行处理，详见下方代码
   
       public class SplashActivity extends StapleSplashActivity {
          @Override
          public void customDealActivityData(StapleActivityBean.DataBean dataBean) {
              if (dataBean==null){
                 //无活动数据
              }else {
                 //有活动数据
              }
          }
        }
        
 2.开屏图相关功能会自动处理，如需更改默认图，可以增加同名资源文件，或者修改此库，具体名称如下
    
    staple_apphub_window_bg.png   --  apphub开屏图
    staple_wwxhb_window_bg.png  --  望望先花b端开屏图
    staple_wwxhc_null_window_bg.png  --  望望先花c端未登录状态开屏图
    staple_wwxhc_wwxh_window_bg.png  --  望望先花c端借款人开屏图
    staple_wwxhc_xssq_window_bg.png  --  望望先花c端业务员角色开屏图
    staple_wwxjk_window_bg.png   --  望望小金卡开屏图
    staple_xwb_window_bg.png  --  希望宝开屏图
    staple_xwjr_window_bg.png  --  希望金融开屏图
 
 3.升级功能会自动处理，更改升级弹层UI只需增加同名资源文件，但有一定规则，详见下方代码
 
    资源文件xml  staple_update_hint.xml ,文件内组件id必须保持统一，样式可自行调整。
    
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">


        <TextView
            android:id="@+id/tv_version"
            android:layout_width="0dp"
            android:layout_height="40dp"
            android:gravity="center"
            android:text="V0.0.1"
            android:textColor="#339933"
            android:textSize="20sp"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent" />

        <TextView
            android:id="@+id/tv_content"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:paddingLeft="20dp"
            android:paddingTop="10dp"
            android:paddingRight="20dp"
            android:textSize="14sp"
            android:paddingBottom="10dp"
            android:text="升级内容升级内容升级内容升级内容升级内容升级内容升级内容升级内容升级内容升级内容升级内容升级内容"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tv_version" />

        <ProgressBar
            android:id="@+id/pb"
            style="@style/StapleUpdateProgressBar"
            android:layout_width="0dp"
            android:layout_height="5dp"
            android:layout_marginLeft="20dp"
            android:layout_marginRight="20dp"
            android:progress="0"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tv_content" />


        <TextView
            android:id="@+id/tv_updateNow"
            android:layout_width="150dp"
            android:layout_height="40dp"
            android:layout_marginTop="10dp"
            android:background="@drawable/staple_update_button"
            android:gravity="center"
            android:text="立即更新"
            android:textColor="#FFFFFF"
            android:textSize="16sp"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@id/pb" />

        <TextView
            android:id="@+id/tv_updateLater"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:textSize="14sp"
            android:textColor="#999999"
            android:text="稍后更新"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tv_updateNow" />

        <View
            app:layout_constraintTop_toBottomOf="@id/tv_updateLater"
            android:layout_width="match_parent"
            android:layout_height="5dp"/>

    </androidx.constraintlayout.widget.ConstraintLayout>

 
 4.推送功能目前只支持简单的打开app操作，最好给用户配置alias 和 tag， 详见下方代码
 
    alias以用户手机号为标准，
 
    tags根据不同app区分。 
 
    希望金融：以用户等级
    
    望望先花b：以用户权限
    
    望望先花c：以用户角色
    
    望望小金卡：暂无
    
    希望宝：暂无
    
 
     JPushInterface.setAlias(this, 5233, "a0000000");

     Set<String> tags = new HashSet<>();
     tags.add("A");
     tags.add("B");
     JPushInterface.setTags(this, 5233, tags);

     
      

# xwjr_staple
### 简介
本库具有开屏、活动、升级、推送功能集成

### 导包
    
    implementation 'com.github.zhuxiaohang2016:xwjr_staple:版本号'

### 配置

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
    
 ### 初始化
    StapleUtils.init(context);//初始化工具类
    StapleConfig.INSTANCE.setAppSource(StapleConfig.APPHUB);//哪个app
    StapleConfig.INSTANCE.setDebug(true);//是否是debug模式
    JPushInterface.setDebugMode(true);//jpush是否是debug模式
    JPushInterface.init(this);//jpush初始化
    
    //用户登录之后需要调用
    StapleUserTokenManager.saveUserToken(token) //access_token
    //用户退出登录需调用
    StapleUserTokenManager.clearUserToken()
    
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
 
 3.推送功能目前只支持简单的打开app操作，最好给用户配置alias 和 tag， 详见下方代码
 
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
     
 4.身份证识别/活体检测相关功能
 
        //尽量提前调用
        AuthManager.getIDCardLicense(this)
        AuthManager.getLivingLicense(this)
        
        //初始化authManagerHelper
         val authManagerHelper = AuthManagerHelper()
         authManagerHelper.setRiskShieldDataListener(object : AuthManagerHelper.RiskShieldData {
                                override fun liveData(isApproved: Boolean) {
                                    //处理活体数据
                                }

                                override fun idCardData(authIDCardBean: StapleAuthIDCardBean.ResultBean) {
                                     //处理身份证数据
                                }
                            })
        
        //开始扫描身份证 activity:当前activity  fragment:当前fragment（可为null）  side：0(正面)1(反面)
        AuthManager.openScanIdActivity(activity,fragment,side)
        
        //开始活体检测 activity:当前activity  fragment:当前fragment（可为null）
        AuthManager.startLivingDetect(activity,fragment)
        
        //onActivityResult固定处理
         try {
            if (resultCode == Activity.RESULT_OK)
                when (requestCode) {
                    AuthManager.PAGE_INTO_IDCARDSCAN -> {
                        AuthManager.dealIDCardScan(data!!) { filePath ->
                            authManagerHelper.upLoadIDCardInfo(filePath)
                        }
                    }

                    AuthManager.PAGE_INTO_LIVENESS -> {
                        AuthManager.dealLivingData(this, data!!) { imagesMap, bestImg, delta ->
                            authManagerHelper.upLoadLiveData(idName, idNo, delta, imagesMap)
                        }
                    }
                }

        } catch (e: Exception) {
            e.printStackTrace()
        }
     
      

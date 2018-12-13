package pdkj.zhaoshang.entity;

/**
 * Created by yy on 2017/8/25.
 */

public class NetContants {

   //服务器地址
   public static final String BASE_URL ="http://114.115.156.243:8888/";
   //public static final String BASE_URL ="http://192.168.1.190:80/";


   public static final String BASE_URL_RESOURCE ="http://dyxy.pengdikj.com/resource/";
   //public static final String BASE_URL_RESOURCE ="http://192.168.1.250:8080/resource/";
   //获取token
   public static final String URL_GET_USER_TOKEN ="api/token/get/";
   //登录
   public static final String URL_GET_USER_LOGIN ="api/user/login";
   //意见反馈
   public static final String URL_GET_USER_FEEDBACK ="api/feedback/submit/";
   //获取项目大类
   public static final String URL_GET_LIB_PROJECT ="RApi/categoryType/getAllType/";
   //获取节点
   public static final String URL_GET_LIB_NODE ="api/node/list/";
   //添加项目
   public static final String URL_CATEGORY_ADD ="api/category/add/";
   //获取首页banner
   public static final String URL_GET_BANNER_LIST ="api/banner/list/";
   //版本更新
   public static final String URL_GET_APPVERSION_CHECK ="api/appVersion/check/";
   //获取项目名称
   public static final String URL_GET_CATEGORY_LIST ="api/category/list/";
   //添加待办事项
   public static final String URL_BACKLOG_ADD ="api/backlog/add";
   //获取当日待办事项
   public static final String URL_GET_BACKLOG_LIST4PAGE ="api/backlog/list4Page";
   //获取待办时间
   public static final String URL_GET_BACKLOG_LIST4DATE ="api/backlog/list4Date";
   //获取项目列表
   public static final String URL_GET_CATEGORY_CATEGORYLIST ="api/category/categoryList";
   //获取工作记录
   public static final String URL_GET_LOG_LIST4PAGE ="/api/log/list4All";
   //获取事件数据
   public static final String URL_GET_EVENT_LIST ="api/event/list";
   //上传文件
   public static final String URL_GET_COMMON_UPLOAD ="api/common/upload";
   //获取单挑
   public static final String URL_GET_EVENT_FINDBYID ="api/event/findById";
   //添加事件
   public static final String URL_GET_EVENT_ADD ="api/event/add";
   //添加汇总
   public static final String URL_GET_CONCLUSION_ADD ="api/conclusion/add";
   //项目评估
   public static final String URL_GET_CONCLUSION_EVALUATE ="api/conclusion/evaluate";
   //获取评分标准
   public static final String URL_GET_CONCLUSION_STANDARD ="api/conclusion/standard";
   //项目添加阶段选择
   public static final String URL_GET_STAGE_LIST2SELECT ="api/stage/list2Select/";
   //获取项目总结列表
   public static final String URL_GET_STAGE_COMMENTLIST ="api/category/commentList";
   //添加项目总结
   public static final String URL_GET_STAGE_ADDCOMMENT ="api/category/addComment";
   //修改用户信息
   public static final String URL_GET_STAGE_UPDATE ="api/user/update";
   //查询单条项目
   public static final String URL_GET_CATEGORY_DETAIL ="api/category/detail";
   //查询单条项目
   public static final String URL_GET_CATEGORY_NODELIST2CATEGORY ="api/category/nodeList2Category";

}
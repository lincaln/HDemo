package com.emt.card.utils;

public class Constants {

    public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public final static String DATE_DATE_TIME_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";



    public static final String COMMA = ",";

    public static final String TRUE = "true";

    public static final String TOKENString = "token";

    public class REFUND_TYPE{
        public static final int OFF_LINE = 0;
        public static final int WEIXIN_REFUND = 1;
    }

    public class RESPONSE_CODE{
        public static final int SUCCESS = 0;
        public static final int LOGIC_ERROR = 100;
    }

    public class STATUS_INT{
        public static final int VALID = 1;
        public static final int INVALID = 0;
    }
    public class STATUS_STR{
        public static final String VALID = "valid";
        public static final String INVALID = "invalid";
    }

    public class XCX_LOGIN_TYPE{
        public static final String SMS = "sms";
        public static final String ENCRY = "encry";
        public static final String ONLYSMS = "onlysms";
    }


    public class IS_TEMPORARY{
        public static final int INVALID = 0;
        public static final int VALID = 1;
        public static final int IS_USED = 2;
    }
    public class UPDATE_STATUS{
        public static final int NONE_UPDATE = 0;
        public static final int WAIT_AUDIT = 1;
        public static final int UPDATE_SUCCESS = 2;
        public static final int UPDATE_DISAGREE = 3;
    }

    public class WEIXIN_XCX_TYPE{//微信小程序 1是领取保险的小程序   为2时是理赔以及购买保险小程序
        public static final int RECEIVE_mall = 1;
        public static final int BUY_mall = 2;
    }

    public class USER_INSURED_REL_TYPE{//用户和被保人关联类型，1，本人，2子女，3配偶，4父母

        public static final int USER_SELF = 1;
        public static final int REL_INSURED = 2;
    }


    public class MINUTES {
        public static final int ONE_DAY = 1440;//一天的分钟数
        public static final int TWO_HOUR = 120;//两小时
        public static final int ONE_MIN = 1;//两小时
    }

    public class USER_TYPE {
        public static final int MEMBER = 1;//会员
        public static final int CUSTOMER_SERVICE = 2;//客服
        public static final int DOCTOR = 9;//医生
    }

    public class OPERATION_TYPE {
        public static final String CREATE = "1";
        public static final String UPDATE = "2";
    }

    public class ORDER_STATUS_INT {

    }


    public class PLATFORM{
        public static final String WECHAT="0";
        public static final String PERSONAL_COMPUTER="1";
        public static final String ANDROID="2";
        public static final String IOS="3";
        public static final String MINI_APP="4";
    }

    public class ERROR_TYPE {
        public static final String ERROR_MSG = "msg";
        public static final String ERROR_CODE = "ret";
    }
    public class ERROR_CODE {
        public static final int WECHAT = 212;//微信用户暂未关联
        public static final int SUCCESS = 200;//没有错误时提示1
        public static final int HAD_ERROR = 210;
        public static final int DATA_ERROR = 211;
        public static final int TOKEN_ERROR = 212;
        public static final int PERMISSION_ERROR = 213;
    }

    public class IS_AUDIT_CODE {
        public static final int WAIT_APPROVAL = 0;
        public static final int HAD_PASS = 1;
        public static final int HAD_REFUSE = 2;
    }
    public class IS_AUDIT_STATUS {
        public static final String WAIT_APPROVAL = "apply";
        public static final String HAD_PASS = "pass";
        public static final String HAD_REFUSE = "refuse";
        public static final String ALL = "all";
    }

    public class ERROR_MSG {
        public static final String GET_mall_INFO_DEFAULT = "获取保险信息失败。";
        public static final String STATUS_DEFAULT = "状态不正确。";
        public static final String SAVE_PHOTO_DEFAULT = "保存图片失败，请核对资料。";
        public static final String HAD_TEMPORY_DATA = "有待审核更新，请等审核通过后再次提交修改申请。";
        public static final String MERCHANT_HAD_UPDATE = "商户已更新信息请重新审核！";
        public static final String QUERY_VALUE_DEFAULT = "获取信息失败，请确认信息是否正确！";
        public static final String GET_COMPANY_INFO_DEFAULT = "获取公司信息失败，请确认信息是否正确！";
        public static final String PARAMETERS_INCOMPLETE = "参数不完整！";
        public static final String PARAMETERS_TYPE_ERROR = "参数类型错误！";
        public static final String VERIFICATION_CODE_SEND_ERROR = "短信发送失败！";
        public static final String VERIFICATION_CODE_ERROR = "验证码错误或已过期！";
        public static final String ACCOUNT_HAD_RIGISTER = "该手机号已注册！";
        public static final String SEND_MSG_DEFAULT = "发送失败！";
        public static final String WECHAT_USER_NOT_REGISTER = "用户尚未注册或未绑定微信！";
        public static final String ACCOUNT_PASSWORD_ERROR = "账号或密码错误！";
        public static final String IS_USER_ADD_SALESMAN = "该账号为普通用户，若要注册为业务员，请联系上级前往人员管理页面添加！";
        public static final String ACCOUNT_INVALID = "账号无效！";
        public static final String CHECK_SIGN_DEFAULT = "签名验证失败！";
        public static final String GET_IDENTITY_INFO_DEFAULT = "获取身份证信息失败！";
        public static final String TOKEN_INVALID = "token无效！";
        public static final String mall_INVALID = "无效保险险种！";
        public static final String mall_IS_USED = "该保险已被领取！";
        public static final String mall_POLICY_DEFAULT = "无法创建参保人清单(个人保单)！";
        public static final String mall_ORDER_DEFAULT = "创建保险订单失败！";
        public static final String mall_PRICE_ERROR = "投保金额错误,或总价对应单价不一致！";
        public static final String NO_AUTHORITY_ERROR = "没权限查看这数据";
        public static final String COMPANY_INFO_NOT_COMPARE = "该业务员关联公司和团队关联公司不一致。";
        public static final String TEAMMEMBER_HAD_BIND = "该业务员已绑定此团队。";
        public static final String HAD_SAME_TEAM_NAME = "该公司已有同名团队。";
        public static final String TEAMMEMBER_NOT_BIND = "该业务员非此团队成员，不能设置为队长。";
        public static final String PARENT_REPEAT = "上下级关系有误，请检查数据是否正确。";
        public static final String HAD_SAME_APPLY = "已有同类申请。";
        public static final String TEAM_HAS_MEMBER_CANNOT_DELETE = "团队仍有成员，不能删除。";
        public static final String NOT_THAT_TEAM = "找不到该团队！";
    }
    public class SERVICE_OPTION {
        public static final int REFERRAL = 23;

    }
    public class SMS_TYPE{
    public static final int IDENTITY_CHECK = 0;
    public static final int USER_REGISTER = 1;
    public static final int FIND_BACK_PASSWORD = 2;
    public static final int MOBILE_IDENTITY_CHECK = 3;
    public static final int FRIEND_APPOINTMENT = 4;
    public static final int FORGOT_PASSWORD = 5;
    public static final int EXAMINE_PASS = 6;
    public static final int PLACE_ORDER_SUCCESS = 7;
    public static final int FRIEND_HELP_APPOINTMENT = 8;
    }

    public class POLICY_RELATION {
        public static final String SELF = "本人";

    }

    public class SCAN_TYPE {
        public static final String SCAN = "scan";
        public static final String INFO = "info";
    }
    public  class  mall_STATUS{
        public static final int VALID=1;//1生效
        public static final int INVALID=2;//2退保
        public static final int NOT_PAY=3;//3尚未支付
        public static final int NOT_PAY_BUT_SHOW=4;//4尚未支付但显示
    }
    public  class  mall_SURRENDER_STATUS{
        public static final int NONE=0;//退保申请0,无。1退保申请
        public static final int HAS_APPLY=1;
    }

    public  class  mall_SURRENDER_AUDIT_STATUS{
        public static final int NONE=0;//未审核
        public static final int AUDIT_PASS=1;//审核通过
        public static final int AUDIT_NOT_PASS=2;//审核未通过
    }
    public class STATIS_DATE_TYPE{
        public static final String DAY = "day";
        public static final String MONTH = "month";

    }

    public class DICTIONARY_ITEM_TYPE{
        public static final String SCHOOL_GRADE  = "SCHOOL_GRADE";
        public static final String SCHOOL_CLASS = "SCHOOL_CLASS";
    }

    public class STATIC_mall_ID {
        public static final int PEOPLE_LIFE_APP=10000000;//全民健康保险
        public static final int BEAUTIFUL_LIFE=10000001;//美好生活保险
        public static final int RECREATIONAL=10000002;//康乐尊享
        public static final int HEALTHY_ANGEL=10000003;//健康天使
        public static final int MILION_SAFE_TRAVEL=10000004;//百万安行
        public static final int EXEMPTIONS=10000005;//豁免保险
        public static final int TAX=10000007;//税优保险
        public static final int DIABETES=10000011;//糖尿病
    }

    public class  JOB_CLASS{
        public static final int RETIRE=6904;//退休
        public static final int BEFORE_STUDENT=6906;
        public static final int STUDENT=5803;
        public static final int HOME_WOMEN=6903;
        public static final int PEDDLER=4804;//摊贩
        public static final int FREED_OUT_SIDE=6902;
        public static final int GUIDE=5502;
        public static final int BUS=3704;
        public static final int BUS_OTHER=3803;
        public static final int BUS_OTHER_WITH=3802;
        public static final int NO_JOB=6905;

    }

    public class MARITAL_STATUS{
        public static final int NONE_MARRY=1;//未婚
        public static final int HAD_MARRY=2;//已婚
        public static final int DIVORCE = 3;//离婚
        public static final int WIDOWHOOD=4;//丧偶
        public static final int UNKNOWN=5;//未明
    }

    public class QUESTION_TYPE{
        public static final int NOTICE=1;//告知
        public static final int FINANCIAL=2;//财务问卷
    }

    public class APPLY_STATUS {
        public static final int NONE_DEAL=0;
        public static final int SUCCESS=1;
        public static final int REJECT=2;
    }

    public class COMPANY_APPLY_TYPE{
        public static final int ONE=1;// 1 申请成为目标公司的下级公司    2 申请目标公司成为自己下级公司
        public static final int TWO=2;
    }

    public class TAX_STEP {
        public static final int WAIT_CONFIRM = 1;       // 申请中
        public static final int CONFIRMED = 2;   // 待公司确认（盖章）
        public static final int ORDER_CREATED = 3;  // 生成保单
        public static final int DONE = 4;           // 确认投保
    }

    public class DATA_TYPE {//业绩统计用的数据范围类型
        public static final int REGION = 1;       // 区域
        public static final int CHANNEL = 2;      // 渠道
        public static final int PRODUCT = 3;      // 产品
        public static final int TEAM = 4;         // 团队
        public static final int COMPANY = 5;      // 公司
        public static final int SALES = 6;      // 业务员
    }

    public class AREA_TYPE {//公司的areatype字段，对应公司不同级别
        public static final int COUNTRY = 1;       // 全国
        public static final int PROVINCE = 2;      // 省
        public static final int CITY = 3;      // 市
    }


    public class INCOME_REL_TYPE {
        public static final int SALE = 1;       //直销
        public static final int CHANNEL = 2;      //渠道销售
    }

    public class QUERY_SOCIAL_mall_TYPE{
        public static final String DWSB="DWSB";
        public static final String GRSB ="GRSB ";
    }
    public class QSBP_SUCCESS_CODE{
        public static final String SUCCESS="000000";
    }

}

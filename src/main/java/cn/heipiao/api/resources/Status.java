/**
 * 
 */
package cn.heipiao.api.resources;

/**
 * @author wzw
 * @date 2016年6月3日
 * @version 1.0 0：表示业务流程正确，反之则不正确。 <br/>
 *          不正确的状态类型为一下说明： <br/>
 *          1xxx: 1开头的代表用户模块的状态 <br/>
 *          2xxx: 2开头代表钓场模块的状态 <br/>
 *          3xxx: 3开头代表财务模块的状态 <br/>
 *          4xxx: 4开头代表操作流程判断的错误,也是公共错误。 <br/>
 *          5xxx: 5开头代表分享或是渔获的状态； <br/>
 *          6xxx: 6开头代表员工管理模板的业务状态 <br/>
 *          7xxx: 7开头代表商品管理模板的业务状态 <br/>
 *          8xxx: 8开头代表订单管理模板的业务状态 <br/>
 *          9xxx: 9开头代表看板模板的业务状态 <br/>
 *          10xxx:10开头代表票模块 <br/>
 *          11xxx:11开头代表文章模块<br/>
 *          12xxx:12开头代表渔具店模块<br/>
 *          13xxx:13开头代表优惠券<br/>
 *          14xxx:14开头代表系统默认图片库相关<br/>
 *          15xxx:15开头代表内容相关<br/>
 *          16xxx:16开头代表网页资源调用相关<br/>
 */
public interface Status {

	/**
	 * 代表成功，其他则代表失败
	 */
	public static final int success = 0;

	/**
	 * http状态，403，不予响应
	 */
	public static final int FORBIDDEN = 403;

	// ==================================== 1
	// =======================================
	/**
	 * 
	 * 用户不存在
	 */
	public static final int no_such_user = 1005;
	/**
	 * 
	 * 合伙人已存在
	 */
	public static final int exist_partner = 1006;

	/**
	 * 合伙人不存在
	 */
	public static final int partner_not_exists = 1007;
	
	/**
	 * 合伙人认领数量已达上限
	 */
	public static final int partner_out_of_claims = 1008;

	/**
	 * 该用户的优惠券已达上限
	 */
	public static final int partner_giving_coupon_uid_limited = 1009;

	/**
	 * 用户名或密码错误
	 * 
	 */
	public static final int login_error = 1010;

	/**
	 * 已是合伙人
	 * 
	 */
	public static final int partner_true = 1011;
	/**
	 * 认领人不存在
	 */
	public static final int accept_partner_not_exists = 1012;
	/**
	 * 验证码过期
	 * 
	 */
	public static final int veriy_code = 1015;

	/**
	 * 合伙人审核中
	 * 
	 */
	public static final int partner_checking = 1016;
	/**
	 * 合伙人审核不通过
	 * 
	 */
	public static final int partner_false = 1017;
	/**
	 * 合伙人申请已存在
	 * 
	 */
	public static final int partner_apply_exists = 1018;
	/**
	 * 无法认领该钓场
	 * 
	 */
	public static final int cannot_sign = 1019;
	
	/**
	 * 验证码错误
	 * 
	 */
	public static final int veriy_code_error = 1020;
	/**
	 * 密码不一致
	 * 
	 */
	public static final int pwd_not_agreed = 1021;
	/**
	 * 密码格式错误
	 * 
	 */
	public static final int pwd_format_error = 1022;

	/**
	 * 用户收杆失败
	 */
	public static final int user_finish_fail = 1030;

	/**
	 * 用户未实名认证
	 */
	public static final int user_not_realname_auth = 1040;
	/**
	 * 该钓场未认领
	 */
	public static final int not_trading = 1041;
	/**
	 * 该钓场未设置规模
	 */
	public static final int NOT_CATEGORY = 1042;
	/**售票认领未通过**/
	public static final int NOT_PASS = 1043 ;
	/**
	 * 数字码无效
	 */
	public static final int INVALID_CODE = 1045;

	/**
	 * 发券数量不足
	 */
	public static final int partner_giving_out_not_enough = 1050;

	/**
	 * 本月提现次数完成，请次月提现
	 */
	public static final int partner_withdraw_limit = 1051;

	/**
	 * 用户状态不可用
	 */
	public static final int USER_STATUS_DISABLE = 1086;

	/**
	 * 用户已经注册
	 */
	public static final int USER_IS_EXIST = 1099;
	
	/**
	 * 合伙人已解约
	 */
	public static final int partner_dismissal = 1100;
	/**
	 * 登陆token失效，请重新登录
	 */
	public static final int login_token_status = 1110;

	/**
	 * 该手机号已绑定其他微信
	 */
	public static final int user_wx_bound = 1120;

	/**
	 * 该微信已被其它账户绑定
	 */
	public static final int user_wx_be_bound = 1121;
	
	// ================================= 2 ==================================

	/**
	 * 钓场不存在
	 */
	public static final int fish_site_not_exists = 2001;

	/**
	 * 用户已存在钓场
	 */
	public static final int user_exists_fish_site = 2002;

	/**
	 * 钓场不支持购票
	 */
	public static final int fish_site_not_support_buy = 2003;

	/**
	 * 未认证
	 */
	public static final int fish_site_not_auth = 2004;

	/**
	 * 钓场已存在用户
	 */
	public static final int fish_site_exists_user = 2005;
	/**
	 * 鱼塘不存在
	 */
	public static final int fish_pond_not_exists = 2010;

	/**
	 * 鱼塘被关联
	 */
	public static final int fish_pond_relevance = 2011;
	/**
	 * 该票已回鱼
	 */
	public static final int DepositFished = 2020;
	/**
	 * 合伙人售票认领过期
	 */
	public static final int partner_over_exists = 2030;
	/**
	 * 改类型钓场奖励配置不存在
	 */
	public static final int CONFIG_NOT_EXIST = 2040;
	/**此钓场不可认领*/
	public static final int FISH_NOT_SIGN = 2050;
	// ================================= 3 ==================================

	/**
	 * 账户信息不存在
	 */
	public static final int account_not_exist = 3000;

	/**
	 * 用户余额不足
	 */
	public static final int user_balance_insufficient = 3001;

	/**
	 * 优惠券不存在
	 */
	public static final int user_coupons = 3002;

	/**
	 * 用户存鱼不足
	 */
	public static final int desposit_fish_insufficient = 3003;

	/**
	 * 订单总额错误
	 */
	public static final int user_orders_totalPrice_error = 3004;

	/**
	 * 优惠券不满足使用条件
	 */
	public static final int coupons_not_use_rule = 3005;

	/**
	 * 退款失败
	 */
	public static final int refund_fail = 3010;

	/**
	 * 优惠券金额错误
	 */
	public static final int coupons_money_error = 3011;

	/**
	 * 单笔金额的范围2~2w
	 */
	public static final int withdraw_fee_range_error_p = 3012;
	
	/**
	 * 当天交易总笔数已达上限
	 */
	public static final int withdraw_trade_max = 3020;

	/**
	 * 单笔金额的范围200~2w
	 */
	public static final int withdraw_fee_range_error = 3021;

	/**
	 * 当天提现总金额超限
	 */
	public static final int withdraw_fee_top_limit = 3022;

	/**
	 * 该平台不支持提现
	 */
	public static final int account_not_support = 3023;

	/**
	 * 用户真实姓名校验错误
	 */
	public static final int account_auth_realname_error = 3024;

	/**
	 * 账户付款错误,请查看订单描述
	 */
	public static final int account_pay_error = 3025;

	/**
	 * 该账户已存在
	 */
	public static final int account_exists = 3026;

	/**
	 * 付款请求过于频繁,15秒后重试
	 */
	public static final int account_freq_limit = 3027;

	/**
	 * 每天提现一次
	 */
	public static final int withdraw_day_over = 3028;
	/**
	 * 每月提现一次
	 */
	public static final int WITHDRAW_MONTH_OVER = 3029;
	/**
	 * 漂币不足
	 */
	public static final int user_gold_coin_not_enough = 3030;

	// =============================== 4 ======================================
	/**
	 * 服务器error 程序异常
	 */
	public static final int error = 4000;
	/**
	 * 字段值为空或值错误
	 */
	public static final int value_is_null_or_error = 4001;

	/**
	 * 短信发送失败
	 */
	public static final int sms_fail = 4004;

	/**
	 * 手机号码格式错误
	 */
	public static final int phonenum_format_error = 4005;
	
	/**
	 * 该功能未开放
	 */
	public static final int function_not_open = 4006;


	// ============================ 5 =========================================
	/**
	 * 已点过赞
	 */
	public static final int LIKE_EXIST = 5000;
	/**
	 * 已关注
	 */
	public static final int Focus_EXIST = 5001;
	/**
	 * 已赞
	 */
	public static final int LIKE_ARTICLE_EXIST = 5002;
	/**
	 * 已评价
	 */
	public static final int COMMENT_EXIST = 5003;
	/**
	 * 印象不存在
	 */
	public static final int IMPRESSION_IS_NOT_EXIST = 5004;
	/**
	 * 超过上限
	 */
	public static final int IMPRESSION_BEYOND_LIMITS = 5005;

	// ============================ 6 =========================================
	/**
	 * 用户(电话号码)没有在钓友端注册
	 */
	public static final int USER_NO_REGISTER = 6000;

	/**
	 * 权限分配失败
	 */
	public static final int EMP_PERMISSION_ERROR = 6001;

	/**
	 * 员工删除失败
	 */
	public static final int DELETE_EMP_ERROR = 6002;

	/**
	 * 员工禁用/启用失败
	 */
	public static final int ALLOW_EMP_ERROR = 6003;

	/**
	 * 该员工已经在该钓场注册
	 */
	public static final int HAS_BEEN_REGISTER_THIS = 6004;

	/**
	 * 该员工已经在别的钓场注册
	 */
	public static final int HAS_BEEN_REGISTER_OTHER = 6005;

	/**
	 * 员工不存在
	 */
	public static final int EMP_NOT_EXISTS = 6006;

	/**
	 * 员工状态不可用
	 */
	int EMP_IN_VALID = 6007;

	/**
	 * 该操作没有权限
	 */
	public static final int emp_not_permission = 6008;

	// =========================== 7 ==================================
	/**
	 * 信息不全
	 */
	public static final int INFO_NOT_COMPLETE = 7005;

	/**
	 * 商品参数错误
	 */
	public static final int goods_param_error = 7006;

	// ========================== 8 ==================================

	/**
	 * 订单不存在
	 */
	public static final int orders_not_exists = 8001;

	/**
	 * 订单错误
	 */
	public static final int orders_error = 8002;

	/**
	 * 支付参数生成失败
	 */
	public static final int pay_param_generate_fail = 8010;

	/**
	 * 订单已支付
	 */
	public static final int orders_paid = 8011;

	/**
	 * 订单已取消
	 */
	public static final int orders_cancel = 8012;

	/**
	 * 支付错误
	 */
	public static final int pay_error = 8013;

	/**
	 * 支付平台错误
	 */
	public static final int pay_platform = 8014;

	// ========================== 9 ==================================
	/**
	 * 添加时间出错
	 */
	int ADD_TIME_ERROR = 9000;

	// ============================= 10 =============================
	/**
	 * 票无效
	 */
	public static final int userTicket_invalid = 10001;

	/**
	 * 退票失败
	 */
	public static final int refund_ticket_fail = 10005;

	/**
	 * 退票有效期已过,无法退票
	 */
	public static final int userTicket_not_refund = 10006;
	/**
	 * 催钟次数过多
	 */
	public static final int pc_out_of_times = 10010;
	/**
	 * 催钟间隔太短
	 */
	public static final int pc_time_short = 10011;
	/**
	 * 催钟失败
	 */
	public static final int pc_fail = 10012;
	/**
	 * 钓鱼时间未结束
	 */
	public static final int pole_fail = 10013;

	// ============================= 11 =============================
	/**
	 * 标题重复
	 */
	public static final int title_repeat = 11001;
	/**
	 * 请升级最新版本
	 */
	public static final int update_version = 11002;

	// ============================= 12 =============================

	/**
	 * 更新数据库不成功
	 */
	public static final int UPDATE_DATABASE_FAIL = 12000;

	/**
	 * 渔具店不存在
	 */
	public static final int FISH_SHOP_NOT_EXISTS = 12001;

	/**
	 * 渔具店未实名认证
	 */
	public static final int FISH_SHOP_NOT_NAME_PASS = 12002;

	/**
	 * 用户状态不存在
	 */
	public static final int FISH_SHOP_USER_STATUS_NOT_EXISTS = 12003;

	/**
	 * 渔具店类型不存在
	 */
	public static final int FISH_SHOP_TYPE_NOT_EXISTS = 12004;

	/**
	 * 渔具店规模不存在
	 */
	public static final int FISH_SHOP_SCALE_NOT_EXISTS = 12005;

	/**
	 * 星标类型不存在
	 */
	public static final int FISH_SHOP_FLAG_NOT_EXISTS = 12006;

	/**
	 * 上线状态不存在
	 */
	public static final int FISH_SHOP_STATUS_NOT_EXISTS = 12007;

	/**
	 * 此渔具店已签约认证
	 */
	public static final int FISH_SHOP_SIGN_ALREADY = 12008;
	/**
	 * 未签约
	 */
	public static final int NOT_SIGN = 12009;
	/**
	 * 认领渔具店超过上限
	 */
	public static final int FISH_SHOP_SIGN_BEYOND_LIMITS = 12020;

	
	// ============================= 13 =============================

	/**
	 * 优惠券已被抢完
	 */
	public static final int coupon_is_zore = 13000;

	/**
	 * 已经领取过优惠券
	 */
	public static final int coupon_existed = 13001;

	/**
	 * 优惠券功能没有授权，请联系客户
	 */
	public static final int coupon_not_auth = 13002;

	// ============================= 14 =============================

	/**
	 * 系统默认图片库类型不正确
	 */
	public static final int SYSTEM_IMG_TYPE_ERROR = 14000;
	/**
	 * 指定的系统默认图片库不存在
	 */
	public static final int SYSTEM_IMG_NOT_EXIST = 14001;

	// ============================= 15 =============================
	/**
	 * 指定的内容不存在
	 */
	public static final int FODDER_CONTENT_NOT_EXISTS = 15000;
	/**
	 * 合伙人开放城市暂无
	 */
	public static final int CITY_RECUIT_NOT_EXIST = 16000;
	
	// ============================= 16 =============================
	/**
	 * 恭喜您，领取成功！,领取的优惠券可以在黑漂APP-我的-优惠券里查看
	 */
	public static final int COUPONS_SHARE_GET_SUCCESS = 16001;
	/**
	 * 抱歉，您来迟了！,优惠券被领完了，您还可以打开黑漂APP，查看并领取更多其它优惠券
	 */
	public static final int COUPONS_SHARE_GET_FAIL = 16002;
	//================================17================
	/**
	* 钓场平台奖励配置不存在
	*/
	public static final int fish_site_reward_config_not_exist = 17000;
	//========================18================
	/**改奖励配置已存在*/
	public static final int CONFIG_ISEXCIT = 18000;
	/**
	 * 奖励配置不存在或已发放
	 */
	public static final int CONFIG_NOTEXCIT = 18100;
	
}

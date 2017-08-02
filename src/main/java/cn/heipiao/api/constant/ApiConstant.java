package cn.heipiao.api.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author z
 * @version 1.0
 * @description
 * @date 2016年6月3日
 */
public class ApiConstant {

	public static final class UserConstant {
		/** 1代表男 */
		public static final String USER_SEX_M = "1";

		/** 2代表女 */
		public static final String USER_SEX_F = "2";

		/** 0未知,保密 */
		public static final String USER_SEX_U = "0";

		/** 1代表可登陆 **/
		public static final String USER_STATUS_ABLE = "1";

		/** 0代表禁用 **/
		public static final String USER_STATUS_DISABLE = "0";

		/** 2代表黑名单状态 **/
		public static final String USER_STATUS_BLACKLIST = "2";

		/** 3代表隐式注册状态 **/
		public static final String USER_STATUS_IMPLICIT = "3";

		/** 普通标签 */
		public static final String NORMAL_LABEL = "label";

		/** 拉黑标签 */
		public static final String BLACK_LABEL = "black";

	}

	public static final class SysConstant {

		/** 段落分隔符 **/
		public static final String DIVIDE_STR_0 = "\31";

		/** 图片标示符 **/
		public static final String DIVIDE_STR_1 = "\30";
		
		/** 视频标示符 **/
		public static final String DIVIDE_STR_2 = "\26";

		/** UTF-8字符编码 **/
		public static final String ENCODING = "UTF-8";

	}

	public static final class ShareConstant {

		/** 1表示有效 */
		public static final String share_status_able = "1";

		/** 0代表无效 */
		public static final String share_status_unable = "0";
	}

	public static final class CommentConstant {

		/** 1表示有效 */
		public static final String comment_status_able = "1";

		/** 0代表无效 */
		public static final String comment_status_unable = "0";
	}

	public static final class LikeConstant {

		/** 1表示分享或是渔获 */
		public static final String SHARE_FLAG = "1";

		/** 0表示评论 */
		public static final String COMMENT_FLAG = "0";
	}

	/**
	 * 交易类型（交易类型0表示消费（购票）1表示充值，2表示提现,3表示获取优惠券，4表示核票收入,5表示退票,15表示全部）
	 *
	 */
	public static final class TradeType {
		/** 漂币消费 */
		public static final int CONSUMPTION = 0;
		/** 充值 */
		public static final int RECHARGE = 1;
		/** 提现 */
		public static final int WITHDRAWS = 2;
		/** 发券 */
		public static final int COUPON = 3;
		/** 核票收入 */
		public static final int TICKET_INCOME = 4;
		/** 表示漂币退票 */
		public static final int TICKET_REFUND = 5;
		/** 提现手续费 **/
		public static final int POUNDAGE = 6;
		/** 表示微信退票 */
		public static final int TICKET_REFUND_WX = 7;
		/** 表示支付宝退票 */
		public static final int TICKET_REFUND_ALI = 8;
		/** 微信消费 */
		public static final int CONSUMPTION_WX = 9;
		/** 支付宝消费 */
		public static final int CONSUMPTION_ALI = 10;
		/** 使用券 */
		public static final int COUPON_EMPLOY = 11;
	}

	/**
	 * 交易记录状态(0表示失败，1表示成功)
	 *
	 */
	public static final class TradeStatus {
		/** 交易成功 */
		public static final int SUCCESS = 1;
		/** 交易失败 */
		public static final int FAIL = 0;
	}

	/**
	 * 第三方（微信，支付宝等）
	 *
	 */
	public static final class OtherSide {
		/** 交易成功 */
		public static final String ALI = "支付宝";
		/** 交易失败 */
		public static final String WX = "微信";
	}

	/**
	 * 票状态 0:未使用,1:已使用,2:已退票,3:过期'
	 *
	 */
	public static final class TicketStatus {
		/** 未使用 */
		public static final int UNUSED = 0;
		/** 已使用 */
		public static final int USED = 1;
		/** 已退票 */
		public static final int CANCEL = 2;
		/** 过期 */
		public static final int OVERDUE = 3;
	}

	/**
	 * 券的禁用/启用标示
	 *
	 */
	public static final class CouponFlag {
		/** 0表示不能发放 */
		public static final int NO = 0;
		/** 1表示可以发放 */
		public static final int YES = 1;
	}

	/**
	 * 
	 * 券的状态0表示待发放，1表示发放中，2表示发放完成
	 */
	public static final class CouponStatus {
		/** 待发放 */
		public static final int WAIT = 0;
		/** 发放中 */
		public static final int GAVE = 1;
		/** 已发放完 */
		public static final int OVER = 2;
	}

	/**
	 * 
	 * 优惠券的使用状态0:未使用,1:已使用,2:已过期
	 */
	public static final class CouponUserStatus {
		/** 未使用 */
		public static final int UNUSED = 0;
		/** 已使用 */
		public static final int USED = 1;
		/** 已过期 */
		public static final int PAST = 2;
	}

	/**
	 * 
	 * 消息状态，0表示未读，1表示已读
	 *
	 */
	public static final class MsgFlag {
		/** 未读 */
		public static final int UNREAD = 0;
		/** 已读 */
		public static final int READ = 1;
	}

	/**
	 * 
	 * 签约状态，0表示可认领，1表示已认领，2表示已签约
	 *
	 */
	public static final class SignStatus {
		/** 可认领 */
		public static final int CLAIM = 0;
		/** 已认领 */
		public static final int CLAIMED = 1;
		/** 已签约 */
		public static final int SIGN = 2;
		/** 售票认领*/
		public static final int TICK_CLAIM = 3;
		/**已售票认领*/
		public static final int TICK_CLAIMED = 4;
		/**售票认领审核通过*/
		public static final int PASS_TICK = 5;
	}

	/**
	 * 
	 * 合伙人审核状态，1表示审核中，0表示审核不通过，2表示审核通过
	 *
	 */
	public static final class PartnerCheckStatus {
		/** 审核不通过 */
		public static final int NO_PASS = 0;
		/** 审核中 */
		public static final int CHECKING = 1;
		/** 审核通过 */
		public static final int PASS = 2;
	}

	/**
	 * 
	 * 渔具店员工状态 ： 1-正常 0-冻结 2-店主
	 *
	 */
	public static final class ShopEmpStatus {
		/** 冻结 */
		public static final int FREEZE = 0;
		/** 正常 */
		public static final int STAFF = 1;
		/** 店主 */
		public static final int SHOPKEEPER = 2;
	}

	/**
	 * 垂钓实体类型 0-钓场 1-渔具店
	 */
	public static final class fishEntityType {
		/**
		 * 0-钓场
		 */
		public static final int TYPE_MENU_REGIONS_SITE = 0;
		/**
		 * 1-渔具店
		 */
		public static final int TYPE_MENU_REGIONS_SHOP = 1;
	}

	/**
	 * 间隔符
	 */
	public static final class spaceCharacter {
		/**
		 * 参数间间隔符 |
		 */
		public static final String PARAMS_SPACE_EACH_OTHER = "\\|";
		/**
		 * 参数占位符
		 */
		public static final String PARAMS_SPACE_SEIZE = "-";
	}

	/**
	 * 1大师文章，2大师经验，3钓场动态，4放鱼信息，5鱼获
	 */
	public static final class ArticleType {
		/** 大师文章/经验 */
		public static final Integer ARTICLE = 1;
		/** 大师经验 */
		public static final Integer EXPERIENCE = 2;
		/** 钓场动态 */
		public static final Integer DYNAMIC = 3;
		/** 放鱼信息 */
		public static final Integer FISH_INFO = 4;
		/** 鱼获 */
		public static final Integer FISH_CATCH = 5;
	}
	/**
	 * 系统默认图片库-存储类型 1-钓场列表图 2-钓场顶部图 3-店铺列表图 4-店铺顶部图 5-大师顶部图
	 */
	public static final class SysPicturesType {
		private static Map<String,List<Integer>> maps = new HashMap<String, List<Integer>>();
		/** 系统默认图片库-存储类型 */
		public static final String SYSTEM_PICTURE_TYPE = "t_sys_pictures";
		/** 钓场列表图 */
		public static final Integer SITE_LIST = 1;
		/** 钓场顶部图 */
		public static final Integer SITE_TOP = 2;
		/** 店铺列表图 */
		public static final Integer SHOP_LIST = 3;
		/** 店铺顶部图 */
		public static final Integer SHOP_TOP = 4;
		/** 大师顶部图 */
		public static final Integer MASTER_TOP = 5;
		static {
			List<Integer> spy = new ArrayList<Integer>();
			spy.add(SITE_LIST);
			spy.add(SITE_TOP);
			spy.add(SHOP_LIST);
			spy.add(SHOP_TOP);
			spy.add(MASTER_TOP);
			maps.put(SYSTEM_PICTURE_TYPE, spy);
		}
		public static  List<Integer> getMaps(String key){
			return maps.get(key);
		}
	}
}

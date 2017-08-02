package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.ArticleCommentMapper;
import cn.heipiao.api.mapper.ArticleMapper;
import cn.heipiao.api.mapper.ArticleNewMapper;
import cn.heipiao.api.mapper.CommentUserMapper;
import cn.heipiao.api.mapper.LikeFocusMapper;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.Comment;
import cn.heipiao.api.pojo.CommentUser;
import cn.heipiao.api.pojo.LikeFocus;
import cn.heipiao.api.pojo.SystemMsg;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.ArticleService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.HpArticleService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * 
 * @ClassName: ArticleServiceImpl
 * @Description: TODO
 * @author zf
 * @date 2016年10月12日
 */
@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private ArticleMapper<Article> articleMapper;
	@Resource
	private LikeFocusMapper<LikeFocus> likeFocusMapper;
	@Resource
	private ArticleCommentMapper<Comment> articleCommentMapper;
	@Resource
	private FishShopService fishShopService;
	@Resource
	private CommentUserMapper commentUserMapper;
	@Resource
	private ArticleNewMapper articleNewMapper;
	@Resource
	private SystemMsgService systemMsgService;
	
	@Resource
	private HpArticleService hpArticleService;
	
	//分享文章的url
	@Value("${article.url}")
	private String url;

	@Override
	public List<Article> getList(Integer uid,Integer start, Integer size,Integer regionId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 2);
		params.put("start", start);
		params.put("size", size);
		if(regionId!=null){
			params.put("regionId", regionId);	
		}
		params.put("uid", uid);
		params.put("type", ApiConstant.ArticleType.ARTICLE);
		List<Article> as = this.articleMapper.selecShoptList(params);
		for (Article a : as) {
			a.setUrl(getShareUri(a.getId().intValue()));
			List<String> list = this.likeFocusMapper.selectNamesforArticle(a
					.getId().intValue());
			a.setLikeUserNames(StringUtils.join(list, "、"));
			
			if (this.likeFocusMapper.selectOneLikeArticle(uid, a.getId()) == null) {
				a.setIsLike(0);//未赞
			} else {
				a.setIsLike(1);//已赞
			}
			Integer likeNum = this.likeFocusMapper.countLikeArticle(a.getId());
			a.setLikeNum(likeNum);//点赞数
			//清除标红标识
//			this.articleNewMapper.updateNewClear(a.getShopId(), ApiConstant.ArticleType.ARTICLE, uid);
//			this.articleNewMapper.updateNewUser(a.getShopId(), ApiConstant.ArticleType.ARTICLE, uid, 1);
		}
		return as;
	}

	@Override
	public List<Article> getListByShop(Long shopId, Integer start, Integer size)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 2);
		params.put("start", start);
		params.put("size", size);
		// params.put("uid", uid);
		params.put("shopId", shopId);
		params.put("type", ApiConstant.ArticleType.ARTICLE);
		List<Article> as = this.articleMapper.selectList(params);
		for (Article a : as) {
			a.setUrl(getShareUri(a.getId().intValue()));
			List<String> list = this.likeFocusMapper.selectNamesforArticle(a
					.getId().intValue());
			//点赞人的昵称
			a.setLikeUserNames(StringUtils.join(list, "、"));
		}
		return as;
	}
	@Override
	public List<Article> getList2ByType(Long shopId, Integer type, Integer start, Integer size)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 2);
		params.put("start", start);
		params.put("size", size);
		params.put("shopId", shopId);
		params.put("type", type);
		List<Article> as = this.articleMapper.selectList(params);
		for (Article a : as) {
			a.setUrl(getShareUri(a.getId().intValue()));
			List<String> list = this.likeFocusMapper.selectNamesforArticle(a
					.getId().intValue());
			//点赞人的昵称
			a.setLikeUserNames(StringUtils.join(list, "、"));
		}
		return as;
	}

	@Override
	@Transactional
	public Integer editArticle(JSONObject json) throws Exception {
		Article art = JSONObject.toJavaObject(json, Article.class);
		boolean isCreate = false;
		if (art.getId() == null) {// 新增
		// Article arti = this.articleMapper.selectOneByTitle(art.getTitle());
		// if (arti != null) {
		// return Status.title_repeat;
		// }
//			Long maxId = this.articleMapper.selectMaxId();
//			art.setId(maxId + 1);
			art.setContentId(0L);
			art.setCreateTime(ExDateUtils.getDate());
			art.setUpdateTime(ExDateUtils.getDate());
			art.setDescription(subDes(art.getContent()));
			art.setType(ApiConstant.ArticleType.ARTICLE);
			this.articleMapper.insert(art);
			
			art.setContentId(art.getId());
			articleMapper.updateArticleContentId(art);
			this.articleMapper.insertContent(art);
//			this.articleNewMapper.update(ApiConstant.ArticleType.ARTICLE, 0);
//			this.articleNewMapper.updateNewSite(art.getShopId(), art.getType(), 0);
//			this.articleNewMapper.updateNewAdd(art.getShopId(), art.getType());
			//大师发布文章，发系统消息给关注的用户
			List<Integer> uids = likeFocusMapper.selectFocusByUser(art.getUid());
			SystemMsg s = new SystemMsg("大师贡献发布啦", getShareUri(art.getId().intValue())
					, "您关注的大师发布文章了，请及时查看!", null, art.getUid(),"C");
			systemMsgService.saveMsgBatch(s, uids.toArray());
			isCreate = true;
//			return art.getId().intValue();
		} else {// 编辑
			// Article arti =
			// this.articleMapper.selectOneByTitle(art.getTitle());
			// if (arti != null) {
			// return Status.title_repeat;
			// }
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("description", subDes(art.getContent()));
//			params.put("status", art.getStatus());
//			params.put("title", art.getTitle());
//			params.put("subTitle", art.getSubTitle());
//			params.put("updateTime", ExDateUtils.getDate());
//			params.put("id", art.getId());
//			params.put("type", ApiConstant.ArticleType.ARTICLE);
//			params.put("location", art.getLocation());
//			params.put("lng", art.getLng());
//			params.put("lat", art.getLat());
//			params.put("site", art.getSite());
			
			art.setUpdateTime(ExDateUtils.getDate());
			art.setDescription(subDes(art.getContent()));
			art.setType(ApiConstant.ArticleType.ARTICLE);
			this.articleMapper.update(art);
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("content", art.getContent());
			params2.put("contentId", art.getId());
			this.articleMapper.updateContent(params2);
			//大师发布文章，发系统消息给关注的用户
			List<Integer> uids = likeFocusMapper.selectFocusByUser(art.getUid());
			SystemMsg s = new SystemMsg("大师贡献发布啦", getShareUri(art.getId().intValue())
					, "您关注的大师发布文章了，请及时查看!", null, art.getUid(),"C");
			systemMsgService.saveMsgBatch(s, uids.toArray());
		}
		//判断如果是发布状态，插入到身边列表
		if(art.getStatus().intValue() == 2){
			hpArticleService.addShopArticle(art);
		}
		
		if(isCreate){
			return art.getId().intValue();
		}
		return Status.success;
	}
	@Override
	@Transactional
	public Integer edits(JSONObject json) throws Exception {
		Article art = JSONObject.toJavaObject(json, Article.class);
		boolean isCreate = false;
		if (art.getId() == null) {// 新增
//			Long maxId = this.articleMapper.selectMaxId();
//			art.setId(maxId + 1);
			art.setContentId(0L);
			art.setCreateTime(ExDateUtils.getDate());
			art.setUpdateTime(ExDateUtils.getDate());
			art.setDescription(subDes(art.getContent()));
			this.articleMapper.insert(art);
			//保存内容
			art.setContentId(art.getId());
			articleMapper.updateArticleContentId(art);
			this.articleMapper.insertContent(art);
//			if(art.getType()==ApiConstant.ArticleType.ARTICLE || art.getType()==ApiConstant.ArticleType.FISH_INFO) {
//				this.articleNewMapper.update(art.getType(), 0);
				//更新标红标识
//				this.articleNewMapper.updateNewSite(art.getShopId(), art.getType(), 0);
				//数量+1
//				this.articleNewMapper.updateNewAdd(art.getShopId(), art.getType());
//			}
//			return art.getId().intValue();
			isCreate = true;
		} else {// 编辑
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("description", subDes(art.getContent()));
//			params.put("status", art.getStatus());
//			params.put("title", art.getTitle());
//			params.put("subTitle", art.getSubTitle());
//			params.put("updateTime", ExDateUtils.getDate());
//			params.put("id", art.getId());
//			params.put("type", art.getType());
//			params.put("location", art.getLocation());
//			params.put("lng", art.getLng());
//			params.put("lat", art.getLat());
//			params.put("site", art.getSite());
			
			art.setUpdateTime(ExDateUtils.getDate());
			art.setDescription(subDes(art.getContent()));
			this.articleMapper.update(art);
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("content", art.getContent());
			params2.put("contentId", art.getId());
			this.articleMapper.updateContent(params2);
		}
		//判断如果是发布状态，插入到身边列表
		if(art.getStatus().intValue() == 2){
			hpArticleService.addSiteArticle(art);
		}
		
		if(isCreate){
			return art.getId().intValue();
		}
		return Status.success;
	}

	@Override
	@Transactional
	public Integer rmArticle(Long id) throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("id", id);
//		params.put("status", 0);
		Article a = new Article();
		a.setId(id);
		a.setStatus(0);
		this.articleMapper.update(a);
		return null;
	}

	@Override
	@Transactional
	public Article getOneContent(Long id) throws Exception {
		Article art = this.articleMapper.selectOneContent(id);
		if (art != null) {
//			this.articleMapper.updateCount(id);
			List<String> list = this.likeFocusMapper.selectNamesforArticle(art
					.getId().intValue());
			//点赞人昵称
			art.setLikeUserNames(StringUtils.join(list, "、"));
			//点赞数
			Integer likeNum = this.likeFocusMapper.countLikeArticle(art.getId());
			art.setLikeNum(likeNum);
		}
//		Map<String,Object> params = new HashMap<String, Object>();
//		params.put("id", id);
//		params.put("start", 0);
//		params.put("size", 1000);
//		List<Comment> coms = this.articleCommentMapper.selectList(params);
//		if(coms.size()>0) {
//			art.setComs(coms);
//			for(Comment c : coms) {
//				Map<String,Object> map = new HashMap<String, Object>();
//				map.put("uid", c.getUserId());
//				List<CommentUser> cus = this.commentUserMapper.selectListForShare(map);
//				c.setCus(cus);
//			}
//		}
		
		return art;
	}
	@Override
	@Transactional
	public Article getOneContentForNew(Long id) throws Exception {
		Article art = this.articleMapper.selectOneContent(id);
		if (art != null) {
			this.articleMapper.updateCount(id);
			List<String> list = this.likeFocusMapper.selectNamesforArticle(art
					.getId().intValue());
			//点赞人的昵称
			art.setLikeUserNames(StringUtils.join(list, "、"));
			//点赞数
			Integer likeNum = this.likeFocusMapper.countLikeArticle(art.getId());
			art.setLikeNum(likeNum);
		}
		return art;
	}

	@Override
	public List<Article> getListByShop2(Long shopId, Integer start, Integer size)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 1);
		params.put("start", start);
		params.put("size", size);
		// params.put("uid", uid);
		params.put("shopId", shopId);
		params.put("type", ApiConstant.ArticleType.ARTICLE);
		List<Article> as = this.articleMapper.selectList(params);
		for (Article a : as) {
			a.setUrl(getShareUri(a.getId().intValue()));
			List<String> list = this.likeFocusMapper.selectNamesforArticle(a
					.getId().intValue());
			//点赞人的昵称
			a.setLikeUserNames(StringUtils.join(list, "、"));
		}
		return as;
	}
	@Override
	public List<Article> getListByType(Long shopId,Integer type, Integer start, Integer size)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 1);
		params.put("start", start);
		params.put("size", size);
		params.put("shopId", shopId);
		params.put("type", type);
		List<Article> as = this.articleMapper.selectList(params);
		for (Article a : as) {
			a.setUrl(getShareUri(a.getId().intValue()));
			List<String> list = this.likeFocusMapper.selectNamesforArticle(a
					.getId().intValue());
			//点赞人的昵称
			a.setLikeUserNames(StringUtils.join(list, "、"));
		}
		return as;
	}

	@Override
	public List<Comment> getComments(Long id, Integer start, Integer size)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("start", start);
		params.put("size", size);
		return this.articleCommentMapper.selectList(params);
	}

	@Override
	@Transactional
	public Integer adComment(JSONObject json) {
		Comment com = JSONObject.toJavaObject(json, Comment.class);
		com.setCreateTime(ExDateUtils.getDate());
		this.articleCommentMapper.insert(com);
		return Status.success;
	}

	@Override
	@Transactional
	public Integer rmComment(Long id) {
		this.articleCommentMapper.update(id);
		return Status.success;
	}

	@Override
	public List<Article> getListForC(Integer start, Integer size, Long shopId,
			Integer visitor) throws Exception {
		// FishShop fs = this.fishShopService.getFishShopById(shopId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 2);
		params.put("start", start);
		params.put("size", size);
		// params.put("uid", fs.getUid());
		params.put("shopId", shopId);
		params.put("type", ApiConstant.ArticleType.ARTICLE);
		//文章列表
		List<Article> as = this.articleMapper.selectList(params);
		for (Article a : as) {
			a.setUrl(getShareUri(a.getId().intValue()));
			List<String> list = this.likeFocusMapper.selectNamesforArticle(a
					.getId().intValue());
			//点赞人昵称
			a.setLikeUserNames(StringUtils.join(list, "、"));
			if (this.likeFocusMapper.selectOneLikeArticle(visitor, a.getId()) == null) {
				a.setIsLike(0);//未赞
			} else {
				a.setIsLike(1);//已赞
			}
			//点赞数量
			Integer likeNum = this.likeFocusMapper.countLikeArticle(a.getId());
			a.setLikeNum(likeNum);
		}
		return as;
	}
	@Override
	public List<Article> getListForCByType(Integer start, Integer size, Long shopId,Integer type,
			Integer visitor) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 2);
		params.put("start", start);
		params.put("size", size);
		params.put("shopId", shopId);
		params.put("type", type);
		List<Article> as = this.articleMapper.selectList(params);
		for (Article a : as) {
			a.setUrl(getShareUri(a.getId().intValue()));
			List<String> list = this.likeFocusMapper.selectNamesforArticle(a
					.getId().intValue());
			//点赞人的昵称
			a.setLikeUserNames(StringUtils.join(list, "、"));
			if (this.likeFocusMapper.selectOneLikeArticle(visitor, a.getId()) == null) {
				a.setIsLike(0);//未赞
			} else {
				a.setIsLike(1);//已赞
			}
			Integer likeNum = this.likeFocusMapper.countLikeArticle(a.getId());
			a.setLikeNum(likeNum);//点赞数量
			if(type==ApiConstant.ArticleType.FISH_CATCH) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", a.getId());
				map.put("start", 0);
				map.put("size", 1000);
				//查询评论
				List<Comment> coms = this.articleCommentMapper.selectList(map);
				if(coms.size()>0) {
					a.setComs(coms);
					for(Comment c : coms) {
						Map<String,Object> map2 = new HashMap<String, Object>();
						map2.put("uid", c.getUserId());
						map2.put("id", c.getId());
						//查询子评论
						List<CommentUser> cus = this.commentUserMapper.selectListForShare(map2);
						c.setCus(cus);
					}
				}
				
			}
		}
		
		return as;
	}

	public String subDes(String content) {
		String[] arrays = content.split(ApiConstant.SysConstant.DIVIDE_STR_0);
		for (String s : arrays) {
			if (!s.contains(ApiConstant.SysConstant.DIVIDE_STR_1) 
					&& !s.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
				return s.substring(0, s.length() > 30 ? 30 : s.length());
			}
		}
		return "暂无描述";
	}

	@Override
	public String getShareUri(Integer id) throws Exception {
		// 测试环境
		// String url = "http://192.168.1.220:8082/m/articlehtml?id="+id;
		// 外网测试环境
		// String url = "http://114.55.111.165:8083/articlehtml?id="+id;
		// 生产环境
//		String url = "http://m.heipiaola.com/articlehtml?id=" + id;
		String suburl = url;
		suburl = suburl + "articlehtml?id=" + id;
		return suburl;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String,Object> getArticleRed(Integer uid) throws Exception {
////		Integer news = this.articleMapper.countArtNew(ApiConstant.ArticleType.ARTICLE);
////		Integer news = this.articleNewMapper.selectOne(uid, ApiConstant.ArticleType.ARTICLE);
////		Integer counts = this.articleMapper.countArts(ApiConstant.ArticleType.ARTICLE);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("status", 2);
//		params.put("start", 0);
//		params.put("size", 100000);
//		params.put("type", ApiConstant.ArticleType.ARTICLE);
//		Map<String,Object> result = new HashMap<String, Object>();
//		List<Article> as = this.articleMapper.selectList(params);
//		List<Long> shopIDs = new ArrayList<Long>();
//		boolean flag = false;
//		Integer newNum = 0;
//		for(Article a : as) {
//			//查询是否有新发布标识
//			Integer fg = this.articleNewMapper.selectNew(a.getShopId(), ApiConstant.ArticleType.ARTICLE, uid);
//			if(fg!=null && fg==0) {
//				flag = true;
//				if(!shopIDs.contains(a.getShopId())) {
//					shopIDs.add(a.getShopId());
//					//新发布的数量
//					Integer counts = this.articleNewMapper.selectNewNum(a.getShopId(), ApiConstant.ArticleType.ARTICLE, uid);
//					newNum = newNum + counts;
//				}
//			}
//		}
		
		
//		result.put("num", newNum);
//		result.put("flag", flag);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("num", 0);
		result.put("flag", false);
		return result;
	}
	@Override
	public Map<String,Object> getPutFishRed(Integer uid,Double lng,Double lat,Integer regionId) throws Exception {
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("lng", lng);
//		map.put("lat", lat);
//		map.put("regionId", regionId);
//		map.put("uid", uid);
//		map.put("start", 0);
//		map.put("size", 100000);
//		List<Article> arts = this.articleMapper.selectListExtForPutFish(map);
//		//=====================
//		boolean isRed = false;
//		Integer newNum = 0;
//		List<Long> shopIDs = new ArrayList<Long>();
//		for(Article a : arts) {
//			//新发布标识
//			Integer flag = this.articleNewMapper.selectNew(a.getShopId(), ApiConstant.ArticleType.FISH_INFO, uid.intValue());
//			if(flag!=null && flag==0) {
//				isRed = true;
//				if(!shopIDs.contains(a.getShopId())) {
//					shopIDs.add(a.getShopId());
//					//新发布数量
//					Integer counts = this.articleNewMapper.selectNewNum(a.getShopId(), ApiConstant.ArticleType.FISH_INFO, uid);
//					newNum = newNum + counts;
//				}
//			}
//		}
//		Map<String,Object> result = new HashMap<String, Object>();
////		result.put("list", arts);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("flag", false);
		result.put("num", 0);
		//=====================
		return result;
	}

	@Override
	public List<Article> getPutFishList(Map<String, Object> map)
			throws Exception {
		List<Article> arts = this.articleMapper.selectListExtForPutFish(map);
		Long uid = (Long) map.get("userId");
//		this.articleNewMapper.updateOne(uid.intValue(), ApiConstant.ArticleType.FISH_INFO, 1);
		for(Article a : arts) {
			a.setUrl(getShareUri(a.getId().intValue()));
			//查询文章点赞人的昵称
			List<String> list = this.likeFocusMapper.selectNamesforArticle(a
					.getId().intValue());
			a.setLikeUserNames(StringUtils.join(list, "、"));
			if (this.likeFocusMapper.selectOneLikeArticle(uid.intValue(), a.getId()) == null) {
				a.setIsLike(0);//未赞
			} else {
				a.setIsLike(1);//已赞
			}
			Integer likeNum = this.likeFocusMapper.countLikeArticle(a.getId());
			a.setLikeNum(likeNum);//点赞数量
//			this.articleNewMapper.updateNewUser(a.getShopId(), ApiConstant.ArticleType.FISH_INFO, uid.intValue(), 1);
//			this.articleNewMapper.updateNewClear(a.getShopId(), ApiConstant.ArticleType.FISH_INFO, uid.intValue());
		}
		return arts;
	}
	
}

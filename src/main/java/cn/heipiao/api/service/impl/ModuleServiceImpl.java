package cn.heipiao.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.ArticleCommentMapper;
import cn.heipiao.api.mapper.ArticleMapper;
import cn.heipiao.api.mapper.ArticleNewMapper;
import cn.heipiao.api.mapper.CommentMapper;
import cn.heipiao.api.mapper.EmpMapper;
import cn.heipiao.api.mapper.FishPostNewsMapper;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.LikeFocusMapper;
import cn.heipiao.api.mapper.LikeMapper;
import cn.heipiao.api.mapper.ModuleMapper;
import cn.heipiao.api.mapper.ShareMapper;
import cn.heipiao.api.mapper.ShopEmpMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.Comment;
import cn.heipiao.api.pojo.FishPostNews;
import cn.heipiao.api.pojo.LikeFocus;
import cn.heipiao.api.pojo.Likes;
import cn.heipiao.api.pojo.Module;
import cn.heipiao.api.pojo.Share;
import cn.heipiao.api.pojo.ShopEmp;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.ModuleService;

/**
 * 说明 : 菜单资源service
 * 
 * @author chenwenye
 * @since 2016-6-28 heipiao1.0
 */
@Service
public class ModuleServiceImpl implements ModuleService {

	private Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

	/** 一级菜单列表的ID **/
	private static final char[] MODULES = { '1', '2', '3', '4' };

	@Resource
	private ModuleMapper moduleMapper;

	@Resource
	private EmpMapper empMapper;

	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private FishShopMapper fishShopMapper;
	
	@Resource
	private ShopEmpMapper<ShopEmp> shopEmpMapper;
	@Resource
	private ArticleNewMapper articleNewMapper;
	@Resource
	private ShareMapper shareMapper;
	@Resource
	private ArticleMapper<Article> articleMapper;
	@Resource
	private CommentMapper commentMapper;
	@Resource
	private ArticleCommentMapper<Comment> articleCommentMapper;
	@Resource
	private FishPostNewsMapper fishPostNewsMapper;
	@Resource
	private LikeMapper likeMapper;
	@Resource
	private LikeFocusMapper<LikeFocus> likeFocusMapper;
	@Resource
	private UserMapper userMapper;
	
	private static final String s_31 = "\31";
	private static final String s_30 = "\30";
	private static final String s_26 = "\26";

	@Override
	public List<Module> findModules(String pId, String empId) {
		Map<String, String> params = new LinkedMap<String, String>();
		params.put("modulePid", pId);
		params.put("empId", empId);
		return this.moduleMapper.selectList(params);
	}

	@Transactional
	@Override
	public boolean deletePermissions(String moduleId, String empId) {
		Map<String, String> id = new LinkedMap<String, String>();
		id.put("moduleId", moduleId);
		id.put("empId", empId);
		return moduleMapper.deleteEmpModuleRelevance(id) > 0;
	}

	@Transactional
	@Override
	public boolean addPermission(String moduleId, String empId) {
		try {
			// 若添加的权限没有
			boolean b1 = this.hasPermission(moduleId, empId);
			if (!b1) {
				return b1;
			}
			Map<String, String> params = new LinkedMap<String, String>();
			params.put("empId", empId);
			params.put("moduleId", moduleId);
			return moduleMapper.insertEmpModuleRelevance(params) > 0;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteAllPermissions(String empId) throws Exception {
		List<Module> modules = this.findModules("0", empId);
		for (Module module : ListUtils.emptyIfNull(modules)) {
			this.deletePermissions(String.valueOf(module.getId()), empId);
		}
		return true;
	}

	/**
	 * 作用: 业务:如果是属于一级菜单则返回true 否则判断员工是否拥有该权限的父权限模板
	 * 
	 * @param modyleId
	 * @param empId
	 * @return
	 */
	private boolean hasPermission(String moduleId, String empId) {
		if (moduleId.length() == 1
				&& Arrays.binarySearch(MODULES, moduleId.charAt(0)) > -1) { // 该菜单属于一级菜单不需要添加上级菜单关联
			return true;
		}
		Map<String, Object> id = new LinkedMap<String, Object>();
		id.put("empId", empId);
		id.put("moduleId", moduleId);
		return this.moduleMapper.selectCountByEmpAndModule(id) != null;
	}

	@Transactional
	@Override
	public boolean refreshAllPermissions(String empId, List<String> _moduleIds) {
		try {
			List<String> moduleIds = new ArrayList<String>();
			moduleIds.addAll(_moduleIds);
			moduleIds.remove("");
			moduleIds.remove(null);
			// FIXME 2016-12-18，发生错误 For input string: "(null)"
			if (this.fishSiteMapper.selectByUid(Long.valueOf(empId)) != null
					&& !moduleIds.contains(EMP_MANAGER)
					&& moduleIds.add(EMP_MANAGER)
					&& !moduleIds.contains(MANAGER) && moduleIds.add(MANAGER)) {
			}
			this.deleteAllPermissions(empId);
			if (moduleIds.size() != 0)
				this.addPermissions(empId, moduleIds);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean addPermissions(String empId, List<String> moduleIds) {
		Map<String, Object> params = new LinkedMap<String, Object>();
		params.put("empId", empId);
		params.put("moduleIds", moduleIds);
		return this.moduleMapper.insertEmpModuleRelevances(params) > 0;
	}

	@Override
	public List<String> findAllModileIds(String empId) {
		return this.moduleMapper.selectAllModuleIds(empId);
	}

	@Override
	@Transactional
	public Integer addPermissionsForShop(JSONObject json) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("uid", json.getInteger("empId"));
		param.put("shopId", json.getLong("shopId"));
		ShopEmp se = this.shopEmpMapper.selectOne(param);
		if(se.getStatus()==2) {
			return Status.emp_not_permission;
		}
		this.moduleMapper.deleteShopEmpModuleRelevance(
				json.getInteger("empId"), json.getLong("shopId"));
		Map<String, Object> params = new LinkedMap<String, Object>();
		List<String> moduleIds = Arrays.asList(json.getString("moduleIds")
				.split(","));
		params.put("moduleIds", moduleIds);
		params.put("empId", json.getInteger("empId"));
		params.put("shopId", json.getLong("shopId"));
		this.moduleMapper.insertShopEmpModuleRelevances(params);
		return Status.success;
	}

	@Override
	public List<String> getShopModileIds(Integer uid, Long shopId) {
		return this.moduleMapper.selectShopModuleIds(uid, shopId);
	}

	@Override
	@Transactional
	public Integer initShopEmpLimits(Integer uid,Long shopId) throws Exception {
		Map<String,Object> params = new HashMap<String, Object>();
		List<String> moduleIds = moduleMapper.selectShopEmpModules();
		params.put("moduleIds", moduleIds);
		params.put("empId", uid);
		params.put("shopId", shopId);
		this.moduleMapper.insertShopEmpModuleRelevances(params);
		return null;
	}

	@Override
	@Transactional
	public Integer initLimits() throws Exception {
		/*******************初始化权限********************/
//		Map<String,Object> param = new HashMap<String, Object>();
//		List<ShopEmp> list = shopEmpMapper.selectListAll(param);
//		for(ShopEmp s : list) {
//			List<String> moduleIds = new ArrayList<String>();
//			List<String> empModus = moduleMapper.selectShopModuleIds(s.getUid(), s.getShopId());
//			if(!empModus.contains("406")) {
//				moduleIds.add("406");
//			}
//			if(!empModus.contains("407")) {
//				moduleIds.add("407");
//			}
//			if(!empModus.contains("408")) {
//				moduleIds.add("408");
//			}
//			if(moduleIds.size()==0) {
//				continue;
//			}
//			Map<String,Object> params2 = new HashMap<String, Object>();
//			params2.put("moduleIds", moduleIds);
//			params2.put("empId", s.getUid());
//			params2.put("shopId", s.getShopId());
//			this.moduleMapper.insertShopEmpModuleRelevances(params2);
//		}
		/*******************初始化鱼获********************/
		Map<String,Object> params = new HashMap<String, Object>();
		List<Share> shares = this.shareMapper.selectAll(params);
		if(shares.size()>0) {
			for(Share s : shares) {
				Article pojo = new Article();
//				pojo.setId(this.articleMapper.selectMaxId()+1);
				pojo.setTitle(s.getContent().length()>7?s.getContent().substring(0, 7)+"...":s.getContent());
				pojo.setSubTitle("鱼获");
				pojo.setMainPicture(s.getMainImg());
				pojo.setDescription(s.getContent().length()>15?s.getContent().substring(0, 15)+"...":s.getContent());
				pojo.setCreateTime(s.getCreateTime());
				pojo.setUpdateTime(s.getCreateTime());
				pojo.setStatus(2);
				pojo.setContentId(pojo.getId());
				pojo.setUid(s.getUserId().intValue());
				pojo.setShopId(s.getSiteId().longValue());
				pojo.setViewCount(s.getClickNum());
				pojo.setType(ApiConstant.ArticleType.FISH_CATCH);
				this.articleMapper.insert(pojo);
				pojo.setContent(s.getContent()+s_31+s.getImg()+s_26+s_31);
				this.articleMapper.insertContent(pojo);
				/*******************初始化评论********************/
				List<Comment> comments = this.commentMapper.selectAll(s.getId().intValue());
				if(comments.size()>0) {
					for(Comment c: comments) {
						Comment com = new Comment();
						com.setContent(c.getContent());
						com.setCreateTime(c.getCreateTime());
						com.setUserId(c.getUserId());
						com.setSid(pojo.getId());
						this.articleCommentMapper.insert(com);
					}
				}
				/*******************初始化鱼获点赞***************************/
				List<Likes> likes = this.likeMapper.selectAll(s.getId().intValue());
				if(likes.size()>0) {
					for(Likes l : likes) {
						LikeFocus lf = new LikeFocus();
						lf.setUid(l.getUserId().intValue());
						lf.setTargetId(pojo.getId().intValue());
						lf.setCreateTime(l.getCreateTime());
						User user = this.userMapper.selectById(l.getUserId());
						lf.setNickname(user.getNickname());
						this.likeFocusMapper.insertLikeArticle(lf);
					}
				}
				
				
			}
			
		}
		/****************初始化动态****************/
		Map<String,Object> params2 = new HashMap<String, Object>();
		List<FishPostNews> news = fishPostNewsMapper.selectAll(params2);
		if(news.size()>0) {
			for(FishPostNews f: news) {
				if(f.getType().equals("1")) {
					Article pojo = new Article();
//					pojo.setId(this.articleMapper.selectMaxId()+1);
					pojo.setTitle(f.getContent().length()>7?f.getContent().substring(0, 7):f.getContent());
					pojo.setSubTitle("放鱼");
					pojo.setMainPicture(f.getMainPicture());
					pojo.setDescription(f.getContent().length()>7?f.getContent().substring(0, 7)+"...":f.getContent());
					pojo.setCreateTime(f.getCreateTime());
					pojo.setUpdateTime(f.getCreateTime());
					pojo.setStatus(2);
					pojo.setContentId(pojo.getId());
					pojo.setUid(0);
					pojo.setShopId(f.getFishSiteId().longValue());
					pojo.setViewCount(0);
					pojo.setType(ApiConstant.ArticleType.FISH_INFO);
					this.articleMapper.insert(pojo);
					if(f.getPicture().endsWith(".mp4")) {
						pojo.setContent(f.getContent()+s_31+f.getPicture()+s_26+s_31);
					}else {
						String[] strs = f.getPicture().split(",");
						if(strs!=null && strs.length>0) {
							String pic = null;
							for(String s: strs) {
								pic = pic + s + s_30+s_31;
							}
							pojo.setContent(f.getContent()+s_31+pic);
						}
					}
					this.articleMapper.insertContent(pojo);
				}else if(f.getType().equals("2")) {
					Article pojo = new Article();
//					pojo.setId(this.articleMapper.selectMaxId()+1);
					pojo.setTitle(f.getContent().length()>7?f.getContent().substring(0, 7):f.getContent());
					pojo.setSubTitle("开塘");
					pojo.setMainPicture(f.getMainPicture());
					pojo.setDescription(f.getContent().length()>7?f.getContent().substring(0, 7)+"...":f.getContent());
					pojo.setCreateTime(f.getCreateTime());
					pojo.setUpdateTime(f.getCreateTime());
					pojo.setStatus(2);
					pojo.setContentId(pojo.getId());
					pojo.setUid(0);
					pojo.setShopId(f.getFishSiteId().longValue());
					pojo.setViewCount(0);
					pojo.setType(ApiConstant.ArticleType.DYNAMIC);
					this.articleMapper.insert(pojo);
					if(f.getPicture().endsWith(".mp4")) {
						pojo.setContent(f.getContent()+s_31+f.getPicture()+s_26+s_31);
					}else {
						String[] strs = f.getPicture().split(",");
						if(strs!=null && strs.length>0) {
							String pic = null;
							for(String s: strs) {
								pic = pic + s + s_30+s_31;
							}
							pojo.setContent(f.getContent()+s_31+pic);
						}
					}
					this.articleMapper.insertContent(pojo);
				}else if(f.getType().equals("3")) {
					Article pojo = new Article();
//					pojo.setId(this.articleMapper.selectMaxId()+1);
					pojo.setTitle(f.getContent().length()>7?f.getContent().substring(0, 7):f.getContent());
					pojo.setSubTitle("闭塘");
					pojo.setMainPicture(f.getMainPicture());
					pojo.setDescription(f.getContent().length()>7?f.getContent().substring(0, 7)+"...":f.getContent());
					pojo.setCreateTime(f.getCreateTime());
					pojo.setUpdateTime(f.getCreateTime());
					pojo.setStatus(2);
					pojo.setContentId(pojo.getId());
					pojo.setUid(0);
					pojo.setShopId(f.getFishSiteId().longValue());
					pojo.setViewCount(0);
					pojo.setType(ApiConstant.ArticleType.DYNAMIC);
					this.articleMapper.insert(pojo);
					if(f.getPicture().endsWith(".mp4")) {
						pojo.setContent(f.getContent()+s_31+f.getPicture()+s_26+s_31);
					}else {
						String[] strs = f.getPicture().split(",");
						if(strs!=null && strs.length>0) {
							String pic = null;
							for(String s: strs) {
								pic = pic + s + s_30+s_31;
							}
							pojo.setContent(f.getContent()+s_31+pic);
						}
					}
					this.articleMapper.insertContent(pojo);
				}else if(f.getType().equals("4")) {
					Article pojo = new Article();
//					pojo.setId(this.articleMapper.selectMaxId()+1);
					pojo.setTitle(f.getContent().length()>7?f.getContent().substring(0, 7):f.getContent());
					pojo.setSubTitle("活动");
					pojo.setMainPicture(f.getMainPicture());
					pojo.setDescription(f.getContent().length()>7?f.getContent().substring(0, 7)+"...":f.getContent());
					pojo.setCreateTime(f.getCreateTime());
					pojo.setUpdateTime(f.getCreateTime());
					pojo.setStatus(2);
					pojo.setContentId(pojo.getId());
					pojo.setUid(0);
					pojo.setShopId(f.getFishSiteId().longValue());
					pojo.setViewCount(0);
					pojo.setType(ApiConstant.ArticleType.DYNAMIC);
					this.articleMapper.insert(pojo);
					if(f.getPicture().endsWith(".mp4")) {
						pojo.setContent(f.getContent()+s_31+f.getPicture()+s_26+s_31);
					}else {
						String[] strs = f.getPicture().split(",");
						if(strs!=null && strs.length>0) {
							String pic = null;
							for(String s: strs) {
								pic = pic + s + s_30+s_31;
							}
							pojo.setContent(f.getContent()+s_31+pic);
						}
					}
					this.articleMapper.insertContent(pojo);
				}
			}
		}
		
		return Status.success;
	}

	@Override
	@Transactional
	public Integer initData() throws Exception {
		/****************初始化数据****************/
//		List<Long> sites = this.fishSiteMapper.selectAllWhereUidNotNull();
//		List<Long> shops = this.fishShopMapper.selectAllWhereUIDNotNull();
//		List<Integer> IDs = this.userMapper.getUserIDs();
//		for(Long f: sites) {
//			Map<String,Object> params = new HashMap<String, Object>();
//			params.put("uids", IDs);
//			params.put("type", ApiConstant.ArticleType.FISH_INFO);
//			params.put("sid", f);
//			this.articleNewMapper.insertNewBatchUser(params);
//		}
//		
//		for(Long s: shops) {
//			Map<String,Object> params = new HashMap<String, Object>();
//			params.put("uids", IDs);
//			params.put("type", ApiConstant.ArticleType.ARTICLE);
//			params.put("sid", s);
//			this.articleNewMapper.insertNewBatchUser(params);
//		}
		return Status.success;
	}

	@Override
	@Transactional
	public Integer initData(Integer uid) throws Exception {
//		List<Long> sites = this.fishSiteMapper.selectAllWhereUidNotNull();
//		List<Long> shops = this.fishShopMapper.selectAllWhereUIDNotNull();
//		Map<String,Object> params1 = new HashMap<String, Object>();
//		params1.put("uid", uid);
//		params1.put("type", ApiConstant.ArticleType.FISH_INFO);
//		params1.put("sids", sites);
//		this.articleNewMapper.insertNewBatchSite(params1);
//		
//		Map<String,Object> params2 = new HashMap<String, Object>();
//		params2.put("uid", uid);
//		params2.put("type", ApiConstant.ArticleType.ARTICLE);
//		params2.put("sids", shops);
//		this.articleNewMapper.insertNewBatchSite(params2);
		return null;
	}

	@Override
	@Transactional
	public Integer initData(Long shopId,Integer type) throws Exception {
//		List<Integer> IDs = this.userMapper.getUserIDs();
//		Map<String,Object> params = new HashMap<String, Object>();
//		params.put("uids", IDs);
//		params.put("type", type);
//		params.put("sid", shopId);
//		this.articleNewMapper.insertNewBatchUser(params);
		return null;
	}
	

}

/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.ArticleMapper;
import cn.heipiao.api.mapper.ArticleNewMapper;
import cn.heipiao.api.mapper.FishPostNewsMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.FishPostNews;
import cn.heipiao.api.service.FishPostNewsService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ExTransferHtmlUtils;

/**
 * @author wzw
 * @date 2016年6月14日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class FishPostNewsServiceImpl implements FishPostNewsService {

	@Resource
	private FishPostNewsMapper fishPostNewsMapper;

	@Resource
	private FishSiteMapper fishSiteMapper;
	@Resource
	private ArticleMapper<Article> articleMapper;
	@Resource
	private ArticleNewMapper articleNewMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPostNewsService#selectBySiteId(java.util.Map)
	 */
	@Override
	public List<FishPostNews> selectBySiteId(Map<String, Object> map) {
//		List<FishPostNews> list = fishPostNewsMapper.selectByMap(map);
//		if (list != null && list.size() > 0) {
//			for (FishPostNews news : list) {
//				news.setContentExt(convertContentToHtml(news.getContent()));
//				news.setPictureExt(ExTransferHtmlUtils
//						.convertPictureToHtml(news.getPicture()));
//			}
//		}
//		return list;
		
		map.put("stauts", 2);
		map.put("type", ApiConstant.ArticleType.DYNAMIC);
		List<Article> arts = this.articleMapper.selectList(map);
		List<FishPostNews> list = new ArrayList<FishPostNews>();
		if(arts.size()>0) {
			for(Article a : arts) {
				FishPostNews news = new FishPostNews();
				Article con = this.articleMapper.selectOneContent(a.getContentId());
				if(!con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_1) && 
						!con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
					continue;
				}
				String pic = "";
				String[] cons = con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0);
				if(con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
					for(String d : cons) {
						if(d.contains(".jpg") && d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
							news.setPicture(d.substring(d.indexOf(".jpg")+4, d.indexOf(".mp4")+4));
							news.setMainPicture(d.substring(0, d.indexOf(".jpg")+4));
							break;
						}else if(!d.contains(".jpg") && d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)){
							news.setPicture(d.substring(0, d.indexOf(".mp4")+4));
							break;
						}
					}
					
				}else if(con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_1)){
					for(String d : cons) {
						if(d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)) {
							pic = pic + d + ApiConstant.SysConstant.DIVIDE_STR_0;
						}
					}
				}
				if(org.apache.commons.lang3.StringUtils.isNotBlank(pic)) {
					news.setPicture(pic);
				}
				String nr = "";
				for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
					if(!d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)&& !d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
//						nr = nr + d + ApiConstant.SysConstant.DIVIDE_STR_0;
						nr = nr + d;
					}
				}
				news.setContent(nr);
				news.setNid(a.getId());
				news.setMainPicture(news.getMainPicture()==null?a.getMainPicture():news.getMainPicture());
				news.setFishSiteId(a.getShopId().intValue());
				news.setFishSiteName(a.getFishSiteName());
				news.setDuration(a.getDuration());
				news.setFocus(a.getFocus());
				news.setCreateTime(new Timestamp(a.getCreateTime().getTime()));
				list.add(news);
			}
		}
		if (list != null && list.size() > 0) {
			for (FishPostNews news : list) {
//				news.setContentExt(convertContentToHtml(news.getContent()));
				news.setPictureExt(ExTransferHtmlUtils
						.convertPictureToHtml(news.getPicture()));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPostNewsService#insertPojo(cn.heipiao.api.
	 * pojo.FishPostNews)
	 */
	@Override
	@Transactional
	public void insertPojo(FishPostNews pojo) {
		Article art = new Article();
//		art.setId(this.articleMapper.selectMaxId()+1);
		
		if (pojo.getType().equals("1")) {
			art.setSubTitle("放鱼");
			art.setType(ApiConstant.ArticleType.FISH_INFO);
//			this.articleNewMapper.updateNewSite(pojo.getFishSiteId().longValue(), ApiConstant.ArticleType.FISH_INFO, 0);
//			this.articleNewMapper.updateNewAdd(pojo.getFishSiteId().longValue(), ApiConstant.ArticleType.FISH_INFO);
		}else if(pojo.getType().equals("2")) {
			art.setSubTitle("开塘");
			art.setType(ApiConstant.ArticleType.DYNAMIC);
			String str = pojo.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)[0];
			pojo.setContent(str.substring(0, str.lastIndexOf(",")));
		}else if(pojo.getType().equals("3")) {
			art.setSubTitle("闭塘");
			art.setType(ApiConstant.ArticleType.DYNAMIC);
			String str = pojo.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)[0];
			pojo.setContent(str.substring(0, str.lastIndexOf(",")));
		}else if(pojo.getType().equals("4")) {
			art.setSubTitle("活动");
			art.setType(ApiConstant.ArticleType.DYNAMIC);
		}
		art.setTitle(pojo.getContent().length()>7?pojo.getContent().substring(0, 7):pojo.getContent());
		art.setMainPicture(pojo.getMainPicture());
		art.setDescription(pojo.getContent().length()>7?pojo.getContent().substring(0, 7)+"...":pojo.getContent());
		art.setCreateTime(pojo.getCreateTime());
		art.setUpdateTime(ExDateUtils.getDate());
		art.setStatus(2);
		art.setContentId(art.getId());
		art.setUid(0);
		art.setShopId(pojo.getFishSiteId().longValue());
		art.setViewCount(0);
		this.articleMapper.insert(art);
		if(pojo.getPicture().endsWith(".mp4")) {
			art.setContent(pojo.getContent()==null?"":pojo.getContent()+ApiConstant.SysConstant.DIVIDE_STR_0
					+pojo.getPicture()==null?"":pojo.getPicture()+ApiConstant.SysConstant.DIVIDE_STR_2
					+ApiConstant.SysConstant.DIVIDE_STR_0);
		}else {
			String[] strs = pojo.getPicture().split(",");
			if(strs!=null && strs.length>0) {
				String pic = "";
				for(String s: strs) {
					pic = pic + s==null?"":s + ApiConstant.SysConstant.DIVIDE_STR_1+ApiConstant.SysConstant.DIVIDE_STR_0;
				}
				art.setContent(pojo.getContent()==null?"":pojo.getContent()+ApiConstant.SysConstant.DIVIDE_STR_0+pic);
			}
		}
		this.articleMapper.insertContent(art);
		
//		if (pojo.getType().equals("1")) {
//			fishSiteMapper.updatePutFishTime(pojo.getFishSiteId(),
//					ExDateUtils.getDate());
//		}
//		fishPostNewsMapper.insertPojo(pojo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPostNewsService#deletePojo(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void deletePojo(Long nid) {
//		fishPostNewsMapper.deletePojo(nid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPostNewsService#selectBySiteIdAnd(java.util
	 * .Map)
	 */
	@Override
	public List<FishPostNews> selectBySiteIdAnd(Map<String, Object> map) {
		map.put("stauts", 2);
		map.put("type", ApiConstant.ArticleType.DYNAMIC);
		List<Article> arts = this.articleMapper.selectList(map);
		List<FishPostNews> list = new ArrayList<FishPostNews>();
		if(arts.size()>0) {
			for(Article a : arts) {
				FishPostNews news = new FishPostNews();
				Article con = this.articleMapper.selectOneContent(a.getContentId());
//				String pic = null;
//				for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
//					if(d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
//						if(d.contains(".jpg")) {
//							news.setPicture(d.substring(d.indexOf(".jpg")+4, d.indexOf(".mp4")+4));
//							break;
//						}else {
//							news.setPicture(d.substring(0, d.indexOf(".mp4")+4));
//							break;
//						}
//					}else if(d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)) {
//						pic = pic + d;
//					}
//				}
//				news.setPicture(pic);
//				for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
//					if(!d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)&& !d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
//						news.setContent(d);
//						break;
//					}
//				}
				if(!con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_1) && 
						!con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
					continue;
				}
				String pic = "";
				String[] cons = con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0);
				if(con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
					for(String d : cons) {
						if(d.contains(".jpg") && d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
							news.setPicture(d.substring(d.indexOf(".jpg")+4, d.indexOf(".mp4")+4));
							news.setMainPicture(d.substring(0, d.indexOf(".jpg")+4));
							break;
						}else if(!d.contains(".jpg") && d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)){
							news.setPicture(d.substring(0, d.indexOf(".mp4")+4));
							break;
						}
					}
					
				}else if(con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_1)){
					for(String d : cons) {
						if(d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)) {
							pic = pic + d + ApiConstant.SysConstant.DIVIDE_STR_0;
						}
					}
				}
				if(org.apache.commons.lang3.StringUtils.isNotBlank(pic)) {
					news.setPicture(pic);
				}
				String nr = "";
				for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
					if(!d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)&& !d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
						nr = nr + d + ApiConstant.SysConstant.DIVIDE_STR_0;
					}
				}
				news.setContent(nr);
				
				news.setNid(a.getId());
				news.setMainPicture(a.getMainPicture());
				news.setFishSiteId(a.getShopId().intValue());
				news.setFishSiteName(a.getFishSiteName());
				news.setDuration(a.getDuration());
				news.setFocus(a.getFocus());
				news.setCreateTime(new Timestamp(a.getCreateTime().getTime()));
				list.add(news);
			}
		}
		
//		List<FishPostNews> list = fishPostNewsMapper.selectByMap(map);
		if (list != null && list.size() > 0) {
			for (FishPostNews news : list) {
				news.setContentExt(convertContentToHtml(news.getContent()));
				news.setPictureExt(ExTransferHtmlUtils
						.convertPictureToHtml(news.getPicture()));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPostNewsService#selectByPutFish(java.util.Map)
	 */
	@Override
	public List<FishPostNews> selectByPutFish(Map<String, Object> map) {
		List<Article> arts = this.articleMapper.selectListExtForPutFish(map);
		List<FishPostNews> list = new ArrayList<FishPostNews>();
		if(arts.size()>0) {
			for(Article a : arts) {
				FishPostNews news = new FishPostNews();
				Article con = this.articleMapper.selectOneContent(a.getContentId());
				if(!con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_1) && 
						!con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
					continue;
				}
				String pic = "";
				String[] cons = con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0);
				if(con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
					for(String d : cons) {
						if(d.contains(".jpg") && d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
							news.setPicture(d.substring(d.indexOf(".jpg")+4, d.indexOf(".mp4")+4));
							news.setMainPicture(d.substring(0, d.indexOf(".jpg")+4));
							break;
						}else if(!d.contains(".jpg") && d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)){
							news.setPicture(d.substring(0, d.indexOf(".mp4")+4));
							break;
						}
					}
					
				}else if(con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_1)){
					for(String d : cons) {
						if(d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)) {
							pic = pic + d + ApiConstant.SysConstant.DIVIDE_STR_0;
						}
					}
				}
				if(org.apache.commons.lang3.StringUtils.isNotBlank(pic)) {
					news.setPicture(pic);
				}
				String nr = "";
				for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
					if(!d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)&& !d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
//						nr = nr + d + ApiConstant.SysConstant.DIVIDE_STR_0;
						nr = nr + d ;
					}
				}
				news.setContent(nr);
				news.setNid(a.getId());
				news.setMainPicture(news.getMainPicture()==null?a.getMainPicture():news.getMainPicture());
				news.setFishSiteId(a.getShopId().intValue());
				news.setFishSiteName(a.getFishSiteName());
				news.setDuration(a.getDuration());
				news.setFocus(a.getFocus());
				news.setCreateTime(new Timestamp(a.getCreateTime().getTime()));
				list.add(news);
			}
		}
//		List<FishPostNews> list = fishPostNewsMapper.selectByPutFish(map);
		
		if(list!=null && list.size()>0) {
			for(FishPostNews news:list) {
				news.setPictureExt(ExTransferHtmlUtils.convertPictureToHtml(news.getPicture()));
			}
		}
		return list;
	}

	/**
	 * 转化文本内容
	 * 
	 * @param content
	 * @return
	 */
	private String convertContentToHtml(String content) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(content)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<p style=\"text-align: left;\"><span style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size:40px\">");
		sb.append(content);
		sb.append("</span></p>");
		// System.out.println(sb.toString());
		return sb.toString();
	}
	
}

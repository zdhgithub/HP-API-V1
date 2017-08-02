package cn.heipiao.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.ArticleCommentMapper;
import cn.heipiao.api.mapper.ArticleMapper;
import cn.heipiao.api.mapper.LikeFocusMapper;
import cn.heipiao.api.mapper.ShareMapper;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.Comment;
import cn.heipiao.api.pojo.LikeFocus;
import cn.heipiao.api.pojo.Share;
import cn.heipiao.api.service.ArticleService;
import cn.heipiao.api.service.CommentService;
import cn.heipiao.api.service.LikeService;
import cn.heipiao.api.service.ShareService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ExTransferHtmlUtils;

/**
 * @author z
 * @version 1.0
 * @description
 * @date 2016年6月13日
 */
@Service
public class ShareServiceImpl implements ShareService {
	@Resource
	private ShareMapper shareMapper;
	@Resource
	private CommentService commentService;
	@Resource
	private LikeService likeService;
	@Resource
	private ArticleCommentMapper<Comment> articleCommentMapper;
	@Resource
	private ArticleMapper<Article> articleMapper;
	@Resource
	private LikeFocusMapper<LikeFocus> likeFocusMapper;
	@Resource
	private ArticleService articleService;

	@Override
	public Share queryShareById(Long id) throws Exception {
//		Share share = shareMapper.selectById(id);
		Article a = articleMapper.selectOne(id);
		Share share = new Share();
		share.setId(a.getId());
		share.setMainImg(a.getMainPicture());
		Article con = this.articleMapper.selectOneContent(a.getContentId());
		for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
			if(d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
				if(d.contains(".jpg")) {
					share.setImg(d.substring(d.indexOf(".jpg")+4, d.indexOf(".mp4")+4));//截取内容
					break;
				}else {
					share.setImg(d.substring(0, d.indexOf(".mp4")+4));
					break;
				}
			}
		}
		for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
			if(!d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)&& !d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
				share.setContent(d);
				break;
			}
		}
		share.setNickName(con.getNickname());
		share.setPortriat(con.getPortriat());
		
		share.setImgExt(ExTransferHtmlUtils.convertPictureToHtml(share.getImg()));
//		Long comment_num = commentService.countComments(id);
		Integer comment_num = this.articleCommentMapper.countArticleComents(id);
//		Long like_num = likeService.countLikes(id,
//				ApiConstant.LikeConstant.SHARE_FLAG);
		Integer like_num = this.likeFocusMapper.countLikeArticle(id);
		share.setCommentNum(comment_num.longValue());
		share.setLikesNum(like_num.longValue());
		return share;
	}

	@Override
	public List<Share> queryShares(Map<String, Object> params) throws Exception {
		Map<String,Object> par = new HashMap<String, Object>();
		par.put("status", 2);
		par.put("shopId", params.get("siteId"));
		par.put("type", ApiConstant.ArticleType.FISH_CATCH);
		par.put("start", params.get("start"));
		par.put("size", params.get("size"));
		List<Article> as = this.articleMapper.selectList(par);
		List<Share> list = new ArrayList<Share>();
		if(as.size()>0) {
			for(Article a : as) {
				Share share = new Share();
				share.setId(a.getId());
				Article con = this.articleMapper.selectOneContent(a.getContentId());
				share.setNickName(con.getNickname());
				share.setPortriat(con.getPortriat());
				if(!con.getContent().contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
					continue;
				}
				for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
					if(d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
						if(d.contains(".jpg")) {
							share.setImg(d.substring(d.indexOf(".jpg")+4, d.indexOf(".mp4")+4));//截取内容
							share.setMainImg(d.substring(0, d.indexOf(".jpg")+4));//截取主图
							break;
						}else {
							share.setImg(d.substring(0, d.indexOf(".mp4")+4));//截取内容
							break;
						}
					}
				}
				share.setMainImg(share.getMainImg()==null?a.getMainPicture():share.getMainImg());
				for(String d : con.getContent().split(ApiConstant.SysConstant.DIVIDE_STR_0)) {
					if(!d.contains(ApiConstant.SysConstant.DIVIDE_STR_1)&& !d.contains(ApiConstant.SysConstant.DIVIDE_STR_2)) {
						share.setContent(d);
						break;
					}
				}
				list.add(share);
			}
		}
		
//		List<Share> list = shareMapper.selectList(params);
		Long userId = (Long) params.get("userId");
		if (list.size() > 0) {
			for (Share s : list) {
				s.setImgExt(ExTransferHtmlUtils.convertPictureToHtml(s.getImg()));
				LikeFocus lk = this.likeFocusMapper.selectOneLikeArticle(userId.intValue(), s.getId());
				
//				Likes lk = likeService.queryLike(userId, s.getId(),
//						ApiConstant.LikeConstant.SHARE_FLAG);
				if (lk == null) {
					s.setLikeFlag("N");
				} else {
					s.setLikeFlag("Y");
				}
//				Long likes = likeService.countLikes(s.getId(),
//						ApiConstant.LikeConstant.SHARE_FLAG);
				Integer likes = this.likeFocusMapper.countLikeArticle(s.getId());
				s.setLikesNum(likes.longValue());
//				Long coms = commentService.countComments(s.getId());
				Integer coms = this.articleCommentMapper.countArticleComents(s.getId());
				s.setCommentNum(coms.longValue());
			}
		}
		return list;
	}

	@Override
	public void modifyShare(Share share) throws Exception {
		shareMapper.updateById(share);
	}

	@Override
	@Transactional
	public void publishShare(Share share) throws Exception {
		
		Article art = new Article();
//		Long maxId = this.articleMapper.selectMaxId();
//		art.setId(maxId + 1);
		art.setContentId(art.getId());
		art.setCreateTime(ExDateUtils.getDate());
		art.setUpdateTime(ExDateUtils.getDate());
		art.setType(ApiConstant.ArticleType.FISH_CATCH);
		art.setTitle(share.getContent());
		art.setSubTitle("鱼获");
		art.setStatus(2);
		art.setUid(share.getUserId().intValue());
		
		art.setMainPicture(share.getMainImg());
		art.setShopId(share.getSiteId().longValue());
		
		art.setContent(share.getContent()+ApiConstant.SysConstant.DIVIDE_STR_0
				+share.getImg()+ApiConstant.SysConstant.DIVIDE_STR_2
				+ApiConstant.SysConstant.DIVIDE_STR_0);
		art.setDescription(articleService.subDes(art.getContent()));
		
		this.articleMapper.insert(art);
		this.articleMapper.insertContent(art);
//		share.setFlag(ApiConstant.ShareConstant.share_status_able);
//		share.setCreateTime(ExDateUtils.getDate());
//		shareMapper.insert(share);
	}

	@Override
	public int countShares(String type, Integer siteId) throws Exception {
		int num = shareMapper.countShares(type, siteId);
		return num;
	}

	@Override
	@Transactional
	public Integer addClickNum(Integer sid) throws Exception {
//		shareMapper.addClickNum(sid);
		this.articleMapper.updateCount(sid.longValue());
		return null;
	}
//	public static void main(String[] args) {
//		String s = "D_EF73C3B2-90E8-484D-B419-46339EA2FF04/0.mp4";
//		System.out.println(s.subSequence(0, s.indexOf(".mp4")+4));
//	}
}

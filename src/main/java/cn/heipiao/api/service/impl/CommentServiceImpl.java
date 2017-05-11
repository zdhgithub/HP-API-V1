package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.ArticleCommentMapper;
import cn.heipiao.api.mapper.CommentMapper;
import cn.heipiao.api.mapper.LikeFocusMapper;
import cn.heipiao.api.pojo.Comment;
import cn.heipiao.api.pojo.LikeFocus;
import cn.heipiao.api.service.CommentService;
import cn.heipiao.api.service.LikeService;
import cn.heipiao.api.utils.ExDateUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月14日
 */
@Service
public class CommentServiceImpl implements CommentService {
	@Resource
	private CommentMapper commentMapper;
	@Resource
	private LikeService likeService;
	@Resource
	private ArticleCommentMapper<Comment> ArticleCommentMapper;
	@Resource
	private LikeFocusMapper<LikeFocus> likeFocusMapper;
//	private static final Logger logger = LoggerFactory
//			.getLogger(CommentServiceImpl.class);

	@Override
	public List<Comment> queryComments(Map<String, Object> params)
			throws Exception {
		
		List<Comment> coms = this.ArticleCommentMapper.selectList(params);
//		List<Comment> coms = commentMapper.selectList(params);
		if (coms != null && coms.size() > 0) {
			Long userId = (Long) params.get("userId");
			for (Comment c : coms) {
				// logger.debug("用户ID{},评论ID：{}", userId,c.getId());
				if (userId > 0) {
					LikeFocus lk = this.likeFocusMapper.selectOneLikeArticle(userId.intValue(), c.getSid());
//					Likes lk = likeService.queryLike(userId, c.getId(),
//							ApiConstant.LikeConstant.COMMENT_FLAG);
					if (lk == null) {
						c.setLikeFlag("N");//未赞
					} else {
						c.setLikeFlag("Y");//已赞
					}
				}else {
					c.setLikeFlag("N");//默认未赞
				}
				//点赞数量
				Integer likeNum = this.likeFocusMapper.countLikeArticle(c.getSid());
//				int likeNum = (int) likeService.countLikes(c.getId(),
//						ApiConstant.LikeConstant.COMMENT_FLAG);
				c.setLikesNum(likeNum);

			}
		}
		return coms;
	}

	@Override
	@Transactional(readOnly = false)
	public void publishComment(JSONObject jsonObject) throws Exception {
		Comment com = new Comment();
		com.setContent(jsonObject.getString("content"));
		com.setSid(jsonObject.getLong("sid"));
		com.setUserId(jsonObject.getLong("userId"));
//		com.setFlag(ApiConstant.CommentConstant.comment_status_able);
		com.setCreateTime(ExDateUtils.getDate());
		this.ArticleCommentMapper.insert(com);
//		commentMapper.insert(com);
	}

	@Override
	@Transactional(readOnly = false)
	public void checkComment(Comment com) throws Exception {
		commentMapper.updateById(com);
	}

	@Override
	public long countComments(Long sid) throws Exception {
		Long comsNum = commentMapper.countComments(sid);
		return comsNum;
	}

}

package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.HpArticleMapper;
import cn.heipiao.api.pojo.ActivityArticle;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.Campaign;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.FodderContent;
import cn.heipiao.api.pojo.HpArticle;
import cn.heipiao.api.service.CampaignService;
import cn.heipiao.api.service.HpArticleService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2017年3月29日
 */
@Service
public class HpArticleServiceImpl implements HpArticleService {

	@Resource
	private HpArticleMapper hpArticleMapper;
	
	@Resource
	private FishShopMapper fishShopMapper;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private CampaignService campaignService;
	
	@Value("${dynamic.bucket}")
	private String dynamicBucket ;
	
	
	@Value("${app.discovery.bucket}")
	private String appDiscoveryBucket ;
	
	
	
	@Override
	public List<HpArticle> getList(Map<String, Object> map) {
		return hpArticleMapper.selectListByRegionId(map);
	}

	@Override
	public void addPojo(HpArticle pojo) {
		pojo.setLikeCount(0);
		pojo.setCommentCount(0);
		pojo.setArticleTime(ExDateUtils.getCalendar().getTimeInMillis());
		hpArticleMapper.insertPojo(pojo);
	}

	@Override
	public List<HpArticle> getListByUid(Map<String, Object> map) {
		return hpArticleMapper.selectListByUid(map);
	}

	@Override
	public HpArticle getByArticleId(Long articleId) {
		return hpArticleMapper.selectById(articleId);
	}
	
	@Override
	public HpArticle getNormalByArticleId(Long articleId) {
		return hpArticleMapper.selectNormalById(articleId);
	}

	@Override
	public void addShopArticle(Article art) {
		HpArticle ha = new HpArticle();
		ha.setType(7);
		ha.setTypeDesc("大师经验");
		ha.setCategory(2);
		ha.setArticleCategory(2);
		ha.setTitle(art.getTitle());
		ha.setContentDetail(art.getContent());
		ha.setBusinessId(art.getShopId());
		ha.setBanner(StringUtils.isBlank(art.getMainPicture()) ? null:
				art.getMainPicture().startsWith("https://") && art.getMainPicture().startsWith("http://")
				? art.getMainPicture() : art.getMainPicture().startsWith("/") ? dynamicBucket + art.getMainPicture()
				:dynamicBucket + "/" + art.getMainPicture());
		ha.setArticleType(StringUtils.isBlank(ha.getBanner()) ? 0 : 1);
		if(art.getId() == null){
			return;
		}
		FishShop fs = fishShopMapper.selectFishShopByShopId(art.getShopId());
		if(fs != null){
			ha.setBusinessName(fs.getName());
			ha.setLatitude(fs.getLatitude());
			ha.setLongitude(fs.getLongitude());
			ha.setCityId(fs.getCityId());
			ha.setPositionName(fs.getAddr());
			ha.setOtherArticleId(art.getId());
			addPojo(ha);
		}
	}

	@Override
	public void addSiteArticle(Article art) {
		HpArticle ha = new HpArticle();
		ha.setType(art.getType());
		ha.setTypeDesc(art.getSubTitle());
		if(art.getId() == null){
			return;
		}
		if(art.getType() == 5){
			ha.setCategory(0);
			ha.setArticleUid(art.getUid().longValue());
			
		}else{
			ha.setCategory(1);
			switch (art.getSubTitle()) {
			case "开塘":
				ha.setType(1);
				break;
			case "闭塘":
				ha.setType(2);
				break;
			case "放鱼":
				ha.setType(3);
				break;
			case "经验":
				ha.setType(4);
				break;
			case "鱼获":
				ha.setType(5);
				break;
			case "活动":
				ha.setType(6);
				break;

			default:
				return;
			}
		}
		FishSite fs = fishSiteMapper.selectById(art.getShopId().intValue());
		if(fs != null){
			ha.setArticleCategory(2);
			ha.setBanner(StringUtils.isBlank(art.getMainPicture()) ? null:
				art.getMainPicture().startsWith("https://") && art.getMainPicture().startsWith("http://")
				? art.getMainPicture() : art.getMainPicture().startsWith("/") ? dynamicBucket + art.getMainPicture()
				:dynamicBucket + "/" + art.getMainPicture());
			ha.setArticleType(StringUtils.isBlank(ha.getBanner()) ? 0 : 1);
			ha.setCityId(fs.getCityId());
			ha.setLatitude(fs.getLatitude());
			ha.setLongitude(fs.getLongitude());
			ha.setTitle(art.getTitle());
			ha.setContentDetail(art.getContent());
			ha.setBusinessId(art.getShopId());
			ha.setBusinessName(fs.getFishSiteName());
			ha.setOtherArticleId(art.getId());
			ha.setPositionName(fs.getAddr());
			addPojo(ha);
		}
	}

	@Override
	public void addPojo(FodderContent pojo) {
		if(pojo.getId() == null || pojo.getStatus() != null && pojo.getStatus() != 1){
			return;
		}
		HpArticle ha = new HpArticle();
		ha.setOtherArticleId(pojo.getId().longValue());
		ha.setType(100);
		HpArticle ha1 = hpArticleMapper.selectByOtherArticleId(ha);
		boolean isNew = false;
		if(ha1 == null){
			ha.setCategory(10);
			ha.setArticleCategory(2);
			ha.setCityId(0);
			ha.setBusinessName("黑漂有态度");
			isNew = true;
		}else{
			ha = ha1;
		}
		
		ha.setBanner(StringUtils.isBlank(pojo.getCoverImg()) ? null:
			pojo.getCoverImg().startsWith("https://") && pojo.getCoverImg().startsWith("http://")
			? pojo.getCoverImg() : pojo.getCoverImg().startsWith("/") ? appDiscoveryBucket + pojo.getCoverImg()
			: appDiscoveryBucket + "/" + pojo.getCoverImg());
		ha.setArticleType(StringUtils.isBlank(ha.getBanner()) ? 0 : 1);
		ha.setTitle(pojo.getTitle());
		ha.setContentDetail(pojo.getContent());
		if(isNew){
			addPojo(ha);
		}else{
			hpArticleMapper.updateByArticleId(ha);
		}
	}

	@Override
	public List<HpArticle> getProfile(Map<String,Object> map) {
		return hpArticleMapper.selectProfile(map);
	}

	@Override
	public List<HpArticle> getAllList(Map<String, Object> map) {
		return hpArticleMapper.selectList(map);
	}

	@Override
	public Long getListCount(Map<String, Object> map) {
		return hpArticleMapper.selectListCount(map);
	}

	@Override
	public int update(HpArticle pojo) {
		return hpArticleMapper.update(pojo);
	}

	@Override
	public void addActivityArticle(ActivityArticle pojo) {
		if(pojo.getId() == null){
			return;
		}
		HpArticle ha = new HpArticle();
		ha.setOtherArticleId(pojo.getId().longValue());
		switch (pojo.getType()) {
		case 1:
			ha.setType(21);
			ha.setTypeDesc("人物");
			break;
		case 2:
			ha.setType(22);
			ha.setTypeDesc("图片");
			break;
		case 3:
			ha.setType(23);
			ha.setTypeDesc("技巧");
			break;
		default:
			return;
		}
		HpArticle ha1 = hpArticleMapper.selectByOtherArticleId(ha);
		boolean isNew = false;
		if(ha1 == null){
			ha.setCategory(11);
			ha.setArticleCategory(2);
			ha.setCityId(0);
			Campaign camp = campaignService.getCampaignById(pojo.getCid());
			if(camp == null){
				return;
			}
			ha.setBusinessName(camp.getName());
			isNew = true;
		}else{
			ha = ha1;
		}
		
		ha.setBanner(StringUtils.isBlank(pojo.getBanner()) ? null:
			pojo.getBanner().startsWith("https://") && pojo.getBanner().startsWith("http://")
			? pojo.getBanner() : pojo.getBanner().startsWith("/") ? appDiscoveryBucket + pojo.getBanner()
			: appDiscoveryBucket + "/" + pojo.getBanner());
		ha.setArticleType(pojo.getIsVideo() == 1 ? 2 : StringUtils.isBlank(ha.getBanner()) ? 0 : 1);
		ha.setTitle(pojo.getTitle());
		ha.setContentDetail(pojo.getContent());
		if(isNew){
			addPojo(ha);
		}else{
			hpArticleMapper.updateByArticleId(ha);
		}
	}

}

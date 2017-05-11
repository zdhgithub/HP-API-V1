package cn.heipiao.api.resources;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.heipiao.api.service.PartnerDBService;
import cn.heipiao.api.utils.ResultUtils;
/**
 * 合伙人库
 * @ClassName: PartnerDBResource
 * @Description: TODO
 * @author zf
 * @version 2.0
 */
//@Component
//@Consumes({ MediaType.APPLICATION_JSON })
//@Produces({ MediaType.APPLICATION_JSON })
//@Path("pdb")
@RestController
@RequestMapping(value = "pdb",produces="application/json")
public class PartnerDBResource {
	private static final Logger log = LoggerFactory
			.getLogger(PartnerDBResource.class);

	@Resource
	private PartnerDBService partnerDBService;
	/**
	 * 查询合伙人资料列表
	 * @param start
	 * @param size
	 * @return
	 * @since 2.0
	 */
//	@Path("{start}/{size}")
//	@GET
	@RequestMapping(value = "{start}/{size}",method = RequestMethod.GET )
	public String getList(@PathVariable("start") Integer start,
			@PathVariable("size") Integer size) {
		try {
			log.debug("start:{},size:{}", start, size);
			if (start == null || size == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.partnerDBService.getList(start, size));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web.front;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.log.FrontGuestbookLogLevel;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.DelHTMLUtil;
import com.thinkgem.jeesite.common.utils.JSONUtil;
import com.thinkgem.jeesite.common.utils.ResponseUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.entity.LeaveWords;
import com.thinkgem.jeesite.modules.cms.entity.LeaveWordsData;
import com.thinkgem.jeesite.modules.cms.entity.Reply;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.service.LeaveWordsService;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 留言板Controller
 * 
 * @author ThinkGem
 * @version 2013-3-15
 */
@Controller
@RequestMapping(value = "${frontPath}/leavewords")
public class LeaveWordsController extends BaseController {

	Logger logger = Logger
			.getLogger("com.thinkgem.jeesite.modules.cms.web.front.FrontLeaveWordsController--前台公共留言"); // "com.foo"
																										// 是实例进行命名，也可以任意

	@Autowired
	private LeaveWordsService leaveWordsService;

	/**
	 * 留言板
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String leaveWords(
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "5") Integer pageSize,
			Model model) {
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		model.addAttribute("site", site);
		model.addAttribute("category", new Category("28"));
		Page<LeaveWords> page = new Page<LeaveWords>(pageNo, pageSize);
		LeaveWords leaveWords = new LeaveWords();
		leaveWords.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		page = leaveWordsService.findPage(page, leaveWords);
		model.addAttribute("page", page);
		return "modules/cms/front/themes/" + site.getTheme()
				+ "/frontLeaveWords";
	}

	/**
	 * 删除留言
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(String leavewordid, Model model,
			RedirectAttributesModelMap modelMap) {

		LeaveWords leaveWords = leaveWordsService.get(leavewordid);
		leaveWordsService.delete(leaveWords);
		modelMap.addFlashAttribute("deleteSuccess", "删除成功");
		return "redirect:" + Global.getFrontPath() + "/leavewords";
	}

	/**
	 * 留言板-保存留言信息
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String leaveWordsSave(String imghtml, String editorValue,
			LeaveWords leaveWords, String validateCode,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		if (editorValue != null) {
			leaveWords.setContent(StringEscapeUtils.unescapeHtml4(editorValue));
		}
		if (imghtml != null) {
			Map imgs = leaveWordsService.img(StringEscapeUtils
					.unescapeHtml4(imghtml));
			LeaveWordsData leaveWordsData = new LeaveWordsData();

			List<String> list = (List<String>) imgs.get("newNum");

			List<LeaveWordsData> datas = new ArrayList<LeaveWordsData>();
			for (String path : list) {
				leaveWordsData = new LeaveWordsData();
				leaveWordsData.setImgs(path);
				datas.add(leaveWordsData);
			}
			leaveWords.setData(datas);
		}
		leaveWords.setIp(request.getRemoteAddr());
		leaveWords.setCreateDate(new Date());
		leaveWords.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		leaveWords.setName(UserUtils.getUser().getLoginName());
		leaveWordsService.save(leaveWords);
		/* addMessage(redirectAttributes, "提交成功，谢谢！"); */

		FrontGuestbookLogLevel.FrontGuestbookLog(logger, leaveWords.getIp()
				+ "::" + UserUtils.getUser().getLoginName() + " 提交一条留言");
		// }else{
		// addMessage(redirectAttributes, "验证码不正确。");
		// }
		// }else{
		// addMessage(redirectAttributes, "验证码不能为空。");
		// }
		return "redirect:" + Global.getFrontPath() + "/leavewords";
	}

	/**
	 * 查找某条留言
	 */
	@RequestMapping(value = "findById", method = RequestMethod.POST)
	public void findLeaveWord(String myid, Model model,
			HttpServletResponse response) {

		LeaveWords leaveWords = new LeaveWords();

		leaveWords = leaveWordsService.get(myid);

		List<String> href = new ArrayList<String>();
		for (LeaveWordsData data : leaveWords.getData()) {
			href.add(data.getImgs());
		}
		String json = JSONUtil.list2json(href);

		ResponseUtils.renderJson(response, json);
	}

	/**
	 * 留言回复提交
	 */
	@RequestMapping(value = "replysubmit", method = RequestMethod.POST)
	public void replysubmit(String comment_id, String editorValue, Model model,
			HttpServletResponse response) {

		Reply reply = new Reply();

		if (editorValue != null) {
			String str = StringEscapeUtils.unescapeHtml4(editorValue);
			str = DelHTMLUtil.delHTMLTag(str);
			if(str.contains("回复")&&str.contains(":"))
				str = str.substring(str.indexOf(":")+1);
			
				reply.setCONTENT(str);
		}
		
		
		// String json = JSONUtil.list2json(href);
		reply.setCOMMENT_ID(comment_id);
		reply.setREPLY_ID(UserUtils.getUser().getId());
		reply.setIP(UserUtils.getUser().getLoginIp());
		reply.setCreateDate(new Date());
		if (!leaveWordsService.getReplys(null,comment_id,"Y").isEmpty()||!leaveWordsService.getReplys(null,comment_id,"N").isEmpty()) {
			reply.setISFIRST("N");
			reply.setBYREPLY_ID(leaveWordsService.getReply(comment_id).getREPLY_ID());
		}else{
			reply.setISFIRST("Y");
			reply.setBYREPLY_ID(UserUtils.getByLoginName(leaveWordsService.get(comment_id).getName()).getId());
		}
		
		
		leaveWordsService.saveReply(reply);
		
		Map<String, Object> maps = Maps.newHashMap();
		
		maps.put("name", UserUtils.get(reply.getREPLY_ID()).getLoginName());
		maps.put("byname", UserUtils.get(reply.getBYREPLY_ID()).getLoginName());
		maps.put("id", reply.getID());
		maps.put("content", DelHTMLUtil.delHTMLTag(reply.getCONTENT()));
		maps.put("createDate", DateUtils.formatDateTime(reply.getCreateDate()));
		maps.put("ISFIRST", reply.getISFIRST());
		
		String json = JSONUtil.map2json(maps);
		
		ResponseUtils.renderJson(response, json);
	}
	

}

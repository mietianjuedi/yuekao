/**
 * 
 */
package com.zhangquan.cms.web.controllers;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangquan.cms.domain.Article;
import com.zhangquan.cms.domain.User;
import com.zhangquan.cms.service.BlogService;
import com.zhangquan.cms.utils.FileUploadUtil;
import com.zhangquan.cms.utils.PageHelpUtil;
import com.zhangquan.cms.web.Constant;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2018年1月10日 下午2:40:38
 */
@Controller
@RequestMapping("/my")
public class UserController {
	
	@Autowired
	private BlogService blogService;

	@RequestMapping({"/", "/index", "/home"})
	public String home(){
		return "user-space/home";
	}
	
	@RequestMapping({"/profile"})
	public String profile(){
		return "user-space/profile";
	}
	//查询博客
	@RequestMapping("blogs")
	public String blogs(Model m,HttpSession session,@RequestParam(defaultValue="1") int page){
		//获取登录用户
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		
		PageHelper.startPage(page,2);
		
		List<Article> blogs=blogService.blogs(user.getId());
		
		PageInfo<Article> pageInfo=new PageInfo<>(blogs);
		//分页页码
		String pInfo=PageHelpUtil.page("blogs", pageInfo, null);
		
		//数据传入前台
		m.addAttribute("blogs", pageInfo.getList());
		m.addAttribute("pInfo", pInfo);
		return "user-space/blog_list";
	}
	
	//根据id查询文章
	@RequestMapping("blog/edit")
	@ResponseBody
	public Article queryById(Model model,int id){
		Article article = blogService.queryById(id);
		return article;
	}
	
	//跳转到博客页面
	@RequestMapping("blogAdd")
	public String blogAdd(Article article){
		return "user-space/blog_add";
	}
	
	//保存博客
	@RequestMapping("blog/save")
	public String save(Article article,MultipartFile file,HttpServletRequest request,HttpSession session){
		//获取登录用户
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		//图片上传
		String picture = FileUploadUtil.upload(request, file);
		article.setPicture(picture);
		article.setAuthor(user);
		//添加博客
		blogService.save(article);
		//添加完重定向到查询
		return "redirect:../blogs";
	}
	
	@RequestMapping("update")
	public String update(Article article,
			MultipartFile file,HttpServletRequest request){
		//图片上传
		String picture = FileUploadUtil.upload(request, file);
		article.setPicture(picture);
		
		try {
			blogService.update(article);
		} catch (Exception e) {
			System.out.println("更新失败");
		}
		return "redirect:blogs";
	}
	
	@RequestMapping("remove")
	@ResponseBody
	public Object remove(int id){
		int num=0;
		try {
			num=blogService.remove(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num>0;
	}
	
	
	
	
}

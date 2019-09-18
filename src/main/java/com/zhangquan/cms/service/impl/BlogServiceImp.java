package com.zhangquan.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangquan.cms.dao.ArticleMapper;
import com.zhangquan.cms.domain.Article;
import com.zhangquan.cms.service.BlogService;
@Service("blogService")
public class BlogServiceImp implements BlogService {
	@Autowired
	private ArticleMapper articleMapper;

	

	@Override
	public List<Article> blogs(Integer id) {
		return articleMapper.blogs(id);
	}



	@Override
	public Article queryById(int id) {
		return articleMapper.selectByPrimaryKey(id);
	}



	@Override
	public void update(Article article) {
		articleMapper.update(article);
	}



	@Override
	public int remove(int id) {
		return articleMapper.remove(id);
	}



	@Override
	public void save(Article article) {
		articleMapper.save(article);
	}
}

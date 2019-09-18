package com.zhangquan.cms.service;

import java.util.List;

import com.zhangquan.cms.domain.Article;

public interface BlogService {

	List<Article> blogs(Integer id);

	Article queryById(int id);

	void update(Article article);

	int remove(int id);

	void save(Article article);

}

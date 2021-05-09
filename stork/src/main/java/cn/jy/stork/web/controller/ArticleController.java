package cn.jy.stork.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.pojo.ArticleCreatePojo;
import cn.jy.stork.biz.service.ArticleService;
import cn.jy.stork.dao.Article;
import cn.jy.stork.dao.ArticleSimplified;
import cn.jy.stork.web.Resp;

/**
 * 信息发布（文章）管理
 * 
 * @author jsh
 *
 */
@RestController
@RequestMapping("article")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	/**
	 * 文章列表。默认按时间倒序排列，支持按类别查询，也支持设定分页参数。注意这个接口返回的数据不提供文章正文。
	 * 
	 * @param category  类别。不输入则取所有类别
	 * @param pageIndex 页码，从0开始。不输入则缺省取0
	 * @param pageSize  每页条数。不输入则缺省取15
	 * @return
	 */
	@GetMapping("")
	public Resp list(@RequestParam(required = false) String category,
			@RequestParam(required = false, defaultValue = "0") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "15") Integer pageSize) {
		List<ArticleSimplified> ac=articleService.find(category,pageIndex,pageSize);
		return Resp.okWithData(ac);
	}

	/**
	 * 文章详细
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public Resp detail(@PathVariable Integer id) {
			Article ac=articleService.findById(id);
			if(ac==null) {
				return Resp.fail("找不到信息，错误的");
			}
			else
				return Resp.okWithData(ac);
	}

	/**
	 * 发表文章
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping("")
	public Resp post(@RequestBody ArticleCreatePojo pojo) {
		Article ac=articleService.add(pojo);
		return Resp.okWithData(ac);
	}

	/**
	 * 删除文章
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping(value = "{id}", params = { "op=remove" })
	public Resp remove(@PathVariable Integer id) {
		Article ac=articleService.findById(id);
		if(ac==null) {
			return Resp.fail("找不到信息，错误的");
		}
		else {
			articleService.delect(id);
			return Resp.ok();
		}
	}
}

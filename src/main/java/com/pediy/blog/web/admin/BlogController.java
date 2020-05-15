package com.pediy.blog.web.admin;

import com.pediy.blog.po.Blog;
import com.pediy.blog.po.Type;
import com.pediy.blog.po.User;
import com.pediy.blog.service.BlogService;
import com.pediy.blog.service.TagsService;
import com.pediy.blog.service.TypesService;
import com.pediy.blog.utils.MarkdownUtils;
import com.pediy.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypesService typesService;
    @Autowired
    private TagsService tagsService;

/*
    @GetMapping("/blog")
    public String listBlog(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("types",typesService.getAllType());
        return "admin/index";
    }
*/

    public void setTypeAndTagsAttribute(Model model){
        model.addAttribute("types",typesService.getAllType());
        model.addAttribute("tags",tagsService.listTags());
    }

    @GetMapping("/listallblog")
    public String listallblog(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typesService.getAllType());
        return "admin/index";
    }

    @PostMapping("/blog/search")
    public String listPageBlog(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("types",typesService.getAllType());
        return "admin/index::blogList";
    }

    //delete the blog
    @GetMapping("/blog/{id}/delete")
    public String removeOneBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
        return "redirect:/admin/listallblog";
    }

    @GetMapping("/editblog")
    public String writeBlog(Model model){
        model.addAttribute("blog",new Blog());
        setTypeAndTagsAttribute(model);
        return "admin/writeblog";
    }

    @PostMapping("/blog/save")
    public String saveBlog(Blog blog, HttpSession session, RedirectAttributes redirectAttributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typesService.getType(blog.getType().getId()));
        blog.setTags(tagsService.listTags(blog.getTagIds()));
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setView(0);
        blog.setUp(0);
        blog.setDown(0);
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        blogService.saveBlog(blog);
        return "redirect:/admin/listallblog";
    }

    @GetMapping("/blog/{id}/peek")
    public String peekBlog(@PathVariable Long id,Model model){
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blog",blog);
        return "admin/showblog";
    }
}

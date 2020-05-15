package com.pediy.blog.web.admin;

import com.pediy.blog.po.Tags;
import com.pediy.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagsController {
    @Autowired
    public TagsService tagsService;

    @GetMapping("/listAllTags")
    public String listAllTags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Tags> tags = tagsService.listTags(pageable);
        model.addAttribute("page",tags);
        return "admin/tags";
    }

    @PostMapping("/addTag")
    public String addTag(Tags tag, RedirectAttributes redirectAttributes){
        if (tagsService.getTagByTag(tag.getTag()) == null){
            Tags tags = tagsService.saveTag(tag);
            if(tags != null) {
                redirectAttributes.addFlashAttribute("msg", "操作成功");
            }else{
                redirectAttributes.addFlashAttribute("msg","操作失败");
            }
        }else{
            redirectAttributes.addFlashAttribute("msg", "标签已存在");
        }
        return "redirect:/admin/listAllTags";
    }

/*    @GetMapping("/tags")
    public String listTags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("tags",tagsService.listTags(pageable));
        return "admin/tags";
    }*/

    @GetMapping("/add-tags")
    public String addTags(Model model){
        model.addAttribute("tag",new Tags());
        return "admin/add-tags";
    }

    @PostMapping("/edittag/{id}")
    public String edittag(@PathVariable Long id,Tags tag,RedirectAttributes attributes){
        String tagName = tagsService.getTag(id).getTag();
        if(tagName != null && tagName.equals(tag.getTag())){
            attributes.addFlashAttribute("msg","标签类型已经存在,修改失败");
        }else{
            tagsService.saveTag(tag);
            attributes.addFlashAttribute("msg","修改成功");
        }
        return "redirect:/admin/listAllTags";
    }

    @GetMapping("/tags/{id}/input")
    public String input(@PathVariable Long id,Model model){
        Tags tag = tagsService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/add-tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id){
        tagsService.removeTags(id);
        return "redirect:/admin/listAllTags";
    }
}

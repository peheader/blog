package com.pediy.blog.web.admin;

import com.pediy.blog.po.Type;
import com.pediy.blog.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypesController {
    @Autowired
    public TypesService typesService;

    @GetMapping("/types")
    public String list(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typesService.listType(pageable));
        return "admin/types";
    }

    @PostMapping("/addtype")
    public String addtype(Type type, RedirectAttributes attributes){
        if (typesService.getTypeByName(type.getName()) == null) {
            Type t = typesService.saveType(type);
            if (t == null) {
                attributes.addFlashAttribute("msg", "操作失败");
            } else {
                attributes.addFlashAttribute("msg", "操作成功");
            }
        }else{
            attributes.addFlashAttribute("msg","类型已经存在");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/add-types")
    public String addtypes(Model model){
        model.addAttribute("type",new Type());
        return "admin/add-types";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id){
        typesService.removeType(id);
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String modifyTypes(@PathVariable Long id,Model model){
        model.addAttribute("type",typesService.getType(id));
        return "admin/add-types";
    }

    @PostMapping("/edittypes/{id}")
    public String commitEdit(@PathVariable Long id,Type type,RedirectAttributes attributes){
        String name = typesService.getType(id).getName();
        if (name != null && name.equals(type.getName())){
            attributes.addFlashAttribute("msg","类型已经存在，修改失败");
        }else{
            attributes.addFlashAttribute("msg","修改成功");
            typesService.updateType(id,type);
        }
        return "redirect:/admin/types";
    }
}

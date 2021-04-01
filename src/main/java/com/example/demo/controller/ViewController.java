package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.ShainInfoDao;
import com.example.demo.dto.ShainInfoDto;
import com.example.demo.form.SearchForm;
import com.example.demo.form.ViewForm;

@Controller
@RequestMapping(value = "/view")
public class ViewController {

    @Autowired
    private ShainInfoDao shainInfoDao;

    @ModelAttribute
    ViewForm setForm() {
        return new ViewForm();

    }

    @RequestMapping
    String init(ViewForm form, BindingResult result, Model model) {
        SearchForm form1 = (SearchForm) model.getAttribute("searchForm");

        ArrayList<ShainInfoDto> shainInfoDto = shainInfoDao.searchShainInfo(form1);

        model.addAttribute("list", shainInfoDto);

        return "View";
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    String update(ViewForm form, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("code", form.getCode());
        return "redirect:/register";
    }

    @RequestMapping(params = "delete", method = RequestMethod.POST)
    String delete(ViewForm form, SearchForm form1, BindingResult result, Model model,
            RedirectAttributes redirectAttributes,
            String code) {

        redirectAttributes.addFlashAttribute("code", form.getCode());
        shainInfoDao.deleteShainInfo(form.getCode());
        form1.setMsg("削除が完了しました。");
        redirectAttributes.addFlashAttribute("searchForm", form1);
        return "redirect:/search";
    }

    @RequestMapping(params = "newRegister", method = RequestMethod.POST)
    String newRegister(SearchForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        return "redirect:/register";
    }

    @RequestMapping(params = "searchback", method = RequestMethod.POST)
    String searchBack(SearchForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        form.setMsg("戻りました");
        redirectAttributes.addFlashAttribute("searchForm", form);
        return "redirect:/search";
    }

}

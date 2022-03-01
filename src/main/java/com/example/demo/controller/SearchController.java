package com.example.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.ShainInfoDao;
import com.example.demo.dto.ShainInfoDto;
import com.example.demo.filter.EncordingFilter;
import com.example.demo.form.SearchForm;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new EncordingFilter();
        return filter;
    }

    @Autowired
    private ShainInfoDao shainInfoDao;

    @ModelAttribute
    SearchForm setForm() {
        return new SearchForm();
    }

    @RequestMapping
    String init(SearchForm form, BindingResult result, Model model) {
        form.setCode(null);
        form.setMeisyouKanji(null);
        form.setSeibetsuMei(null);
        form.setShozokuMei(null);
        form.setNaisen(null);
        return "Search";
    }

    @RequestMapping(params = "search", method = RequestMethod.POST)
    String search(HttpServletRequest request, HttpServletResponse response, SearchForm form, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        ArrayList<ShainInfoDto> shainInfoDto = shainInfoDao.searchShainInfo(form);

        if (shainInfoDto == null || shainInfoDto.size() == 0) {
            form.setMsg("該当データが存在しません。");
            return "Search";
        }
        redirectAttributes.addFlashAttribute("searchForm", form);
        return "redirect:/view";
    }

    @RequestMapping(params = "newRegister", method = RequestMethod.POST)
    String newRegister(SearchForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        return "redirect:/register";
    }
}

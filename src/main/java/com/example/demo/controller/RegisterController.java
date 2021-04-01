package com.example.demo.controller;

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
import com.example.demo.form.RegisterForm;
import com.example.demo.form.SearchForm;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private ShainInfoDao shainInfoDao;

    //
    @ModelAttribute
    RegisterForm setForm() {
        return new RegisterForm();
    }

    @RequestMapping
    String init(RegisterForm form, BindingResult result, Model model) {
        String code = (String) model.getAttribute("code");
        if (code == null) {
            // 新規作成モード
            form.setRegisterFlg("0");
            return "Register";
        }
        // 修正モード
        form.setRegisterFlg("1");
        ShainInfoDto shainInfoDto = shainInfoDao.getShainInfo(code);
        form.setCode(shainInfoDto.getCode());
        form.setMeisyouKanji(shainInfoDto.getMeisyouKanji());
        form.setNaisen(shainInfoDto.getNaisen());
        form.setNyushaDate(shainInfoDto.getNyushaDate());
        form.setSeibetsuMei(shainInfoDto.getSeibetsuMei());
        form.setShozokuMei(shainInfoDto.getShozokuMei());

        return "Register";
    }

    @RequestMapping(params = "register", method = RequestMethod.POST)
    String register(RegisterForm form, SearchForm form1, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {

        if (form.getRegisterFlg().equals("0")) {
            form1.setMsg("登録が完了しました。");
            redirectAttributes.addFlashAttribute("searchForm", form1);
        } else if (form.getRegisterFlg().equals("1")) {

            form1.setMsg("修正が完了しました。");
            redirectAttributes.addFlashAttribute("searchForm", form1);
        }

        return "redirect:search";
    }

    @RequestMapping(params = "searchback", method = RequestMethod.POST)
    String init(SearchForm form, BindingResult result, Model model) {
        return "redirect:search";
    }
}

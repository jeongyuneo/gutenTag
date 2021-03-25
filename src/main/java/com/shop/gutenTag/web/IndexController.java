package com.shop.gutenTag.web;

import com.shop.gutenTag.config.auth.LoginUser;
import com.shop.gutenTag.config.auth.dto.SessionUser;
import com.shop.gutenTag.domain.posts.PostsRepository;
import com.shop.gutenTag.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final PostsRepository postsRepository;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        httpSession.invalidate();
        model.addAttribute("posts", postsRepository.findAll());
        return "index";
    }
}

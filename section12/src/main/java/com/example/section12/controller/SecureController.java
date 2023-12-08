package com.example.section12.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SecureController {

    @GetMapping("/")
    public String main(OAuth2AuthenticationToken token) {
        log.info(token.getPrincipal().toString());
        return "secure.html";
    }

}

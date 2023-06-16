package com.poly.da2.controller;

import com.poly.da2.entity.Userss;
import com.poly.da2.service.AccountService;
import com.poly.da2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {
    @Autowired
    UserService uService;
    @Autowired
    AccountService aService;
    @GetMapping
    public String userinfo() {
        return "user/userinfo";
    }
    @GetMapping("/userinfo-login")
    @ResponseBody
    public Userss getOneByUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            int userId = aService.findById(authentication.getName()).getUser().getId();
            return uService.findById(userId);
        }catch (NoSuchElementException nsee){
            String info=authentication.getPrincipal().toString();
            String email =info.substring(info.lastIndexOf("email")+6,info.length()-2);
            return uService.findById(aService.findById(email).getUser().getId());
        }
    }

    @PostMapping("/userinfo-login")
    @ResponseBody
    public ResponseEntity saveUserLogin( @RequestBody Userss u) {
        return ResponseEntity.ok(uService.update(u));
    }
    @PostMapping("/image")
    public String updateUserImage(@RequestParam("id") String id, @RequestParam("file") MultipartFile image) throws ChangeSetPersister.NotFoundException, IOException {
        uService.updateUserImage(Integer.parseInt(id), image.getOriginalFilename());
        Path path = Paths.get(System.getProperty("user.dir")+"/src/main/resources/static/assert/user/img", image.getOriginalFilename());
        Files.write(path, image.getBytes());
        return "redirect:/userinfo";
    }

}

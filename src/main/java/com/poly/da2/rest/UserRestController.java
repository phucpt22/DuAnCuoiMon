package com.poly.da2.rest;

import com.poly.da2.model.User;
import com.poly.da2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/Users")
public class UserRestController {
    @Autowired
    UserService uService;

    @GetMapping
    public List<User> getUsers(@RequestParam("admin") Optional<Boolean> admin) {
        if (admin.orElse(false)) {
            return uService.getAdministrators();
        }
        return uService.findAll();
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return uService.findAll();
    }

    @GetMapping("{id}")
    public User getOne(@PathVariable("id" )Integer id) {
        return uService.findById(id);
    }

    @PostMapping
    public User create(@RequestBody User User ) {
        return uService.create(User);
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id")String id,@RequestBody User User ) {
        return uService.update(User);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id")Integer id) {
        uService.delete(id);
    }
}

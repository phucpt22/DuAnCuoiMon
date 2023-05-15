package com.poly.da2.rest;

import com.poly.da2.entity.Userss;
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
    public List<Userss> getUsers(@RequestParam("admin") Optional<Boolean> admin) {
        if (admin.orElse(false)) {
            return uService.getAdministrators();
        }
        return uService.findAll();
    }

    @GetMapping("/all")
    public List<Userss> getAll() {
        return uService.findAll();
    }

    @GetMapping("{id}")
    public Userss getOne(@PathVariable("id" )Integer id) {
        return uService.findById(id);
    }

    @PostMapping
    public Userss create(@RequestBody Userss Userss) {
        return uService.create(Userss);
    }

    @PutMapping("{id}")
    public Userss update(@PathVariable("id")String id, @RequestBody Userss Userss) {
        return uService.update(Userss);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id")Integer id) {
        uService.delete(id);
    }
}

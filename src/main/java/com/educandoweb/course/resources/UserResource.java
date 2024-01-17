package com.educandoweb.course.resources;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{idUser}")
    public ResponseEntity<User> findById(@PathVariable Long idUser){
        User u = service.findById(idUser);
        return ResponseEntity.ok().body(u);
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = service.insert(obj);

        //Foi feito esse URI para que o retorno seja 201, que é o retorno correto quando é criado algo novo
        //assim utiliza/se o created, porém ele necessita do URI
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping (value = "/{idUser}")
    public ResponseEntity<Void> delete(@PathVariable Long idUser){
        service.delete(idUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping (value = "/{idUser}")
    public ResponseEntity<User> delete(@PathVariable Long idUser, @RequestBody User user){
        user = service.update(idUser, user);
        return ResponseEntity.ok().body(user);
    }
}

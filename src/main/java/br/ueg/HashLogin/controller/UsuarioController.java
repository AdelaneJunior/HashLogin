package br.ueg.HashLogin.controller;

import br.ueg.HashLogin.model.UsuarioDTO;
import br.ueg.HashLogin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(service.login(usuarioDTO));

    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastro(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(service.cadastro(usuarioDTO));
    }
}

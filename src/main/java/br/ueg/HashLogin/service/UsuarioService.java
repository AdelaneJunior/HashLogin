package br.ueg.HashLogin.service;

import br.ueg.HashLogin.model.Usuario;
import br.ueg.HashLogin.model.UsuarioDTO;
import br.ueg.HashLogin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private void criptografarSenha(Usuario usuario) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String senhaCodificada = bCryptPasswordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);
    }

    private boolean validaLogin(Usuario usuario){
        Usuario usuarioNoBd = repository.findUsuarioByEmail(usuario.getEmail()).orElse(null);
        if (Objects.nonNull(usuarioNoBd)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder.matches(usuario.getSenha(), usuarioNoBd.getSenha());
        }
        return false;
    }

    public boolean login(UsuarioDTO usuarioDTO){
        return validaLogin(toModel(usuarioDTO));
    }
    public UsuarioDTO cadastro(UsuarioDTO usuarioDTO){
        Usuario usuario = toModel(usuarioDTO);
        criptografarSenha(usuario);
        usuario = repository.saveAndFlush(usuario);
        return toDTO(usuario);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getEmail(), usuario.getSenha());
    }

    private Usuario toModel(UsuarioDTO usuarioDTO) {

        return Usuario.builder()
                .email(usuarioDTO.email())
                .senha(usuarioDTO.senha())
                .build();


    }


}

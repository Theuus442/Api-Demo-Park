package com.matheus.demo_park_api.service;

import com.matheus.demo_park_api.entity.Usuario;
import com.matheus.demo_park_api.exception.EntityNotFoundException;
import com.matheus.demo_park_api.exception.UsernameUniqueViolationException;
import com.matheus.demo_park_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Codifica a senha antes de salvar
        try {
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username %s já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado!", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new RuntimeException("As senhas não coincidem!");
        }

        Usuario user = buscarPorId(id);

        // Usa o PasswordEncoder para verificar a senha atual
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new RuntimeException("A senha atual está incorreta!");
        }

        user.setPassword(passwordEncoder.encode(novaSenha)); // Codifica a nova senha antes de salvar
        return usuarioRepository.save(user); // Salva o usuário com a senha atualizada
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com username '%s' não encontrado!", username))
        );
    }

    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}

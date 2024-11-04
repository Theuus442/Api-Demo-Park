package com.matheus.demo_park_api.web.controller.dto.mapper;

import com.matheus.demo_park_api.entity.Usuario;
import com.matheus.demo_park_api.web.controller.dto.UsuarioCreateDto;
import com.matheus.demo_park_api.web.controller.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    // Instância única de ModelMapper para reutilização
    private static final ModelMapper mapperMain = new ModelMapper();
    private static final TypeMap<Usuario, UsuarioResponseDto> propertyMapper;

    // Bloco de inicialização para configurar o TypeMap
    static {
        propertyMapper = mapperMain.createTypeMap(Usuario.class, UsuarioResponseDto.class);

        // Configura mapeamento manual do campo 'role'
        propertyMapper.addMappings(mapper -> mapper.map(src -> {
            // Verifica se o role existe e extrai o nome, removendo o prefixo "ROLE_" se presente
            if (src.getRole() != null) {
                return src.getRole().name().startsWith("ROLE_")
                        ? src.getRole().name().substring("ROLE_".length())
                        : src.getRole().name();
            }
            return null; // Retorna null se o role for null
        }, UsuarioResponseDto::setRole));
    }

    // Método para converter Usuario para UsuarioResponseDto
    public static UsuarioResponseDto toDto(Usuario user) {
        if (user == null) {
            return null;
        }
        // Retorna o DTO mapeado
        return mapperMain.map(user, UsuarioResponseDto.class);
    }

    // Método para converter UsuarioCreateDto para Usuario
    public static Usuario toUsuario(UsuarioCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        return mapperMain.map(createDto, Usuario.class);
    }

    // Método para converter uma lista de Usuario para uma lista de UsuarioResponseDto
    public static List<UsuarioResponseDto> toListDto(List<Usuario> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }
        // Mapeia cada usuário da lista para o DTO correspondente
        return users.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }
}

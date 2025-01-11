package com.ambystudio.forohub.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTORegistroUsuario(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,
        @NotBlank(message = "Login es obligatorio (Correo electronico)")
        @Email
        String login,
        @NotBlank(message = "Password es obligatorio")
        String password
) {
}

package com.ambystudio.forohub.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTORegistroUsuario(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,
        @NotBlank(message = "Login es obligatorio (Correo electronico)")
        @Email
        String login,
        @NotBlank(message = "Password es obligatorio")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{10,}$", message = "La contraseña debe tener al menos 10 caracteres, incluyendo al menos una letra y un número.")
        String password
) {
}

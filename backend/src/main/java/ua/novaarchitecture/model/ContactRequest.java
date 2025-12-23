package ua.novaarchitecture.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for contact form requests.
 * Contains validation rules for all form fields.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    @NotBlank(message = "Будь ласка, введіть ваше ім'я")
    @Size(min = 2, max = 100, message = "Ім'я має містити від 2 до 100 символів")
    private String name;

    @NotBlank(message = "Будь ласка, введіть email")
    @Email(message = "Введіть коректну email адресу")
    private String email;

    @Pattern(regexp = "^$|^(\\+380)?[0-9]{9,10}$", message = "Введіть коректний номер телефону (+380XXXXXXXXX)")
    private String phone;

    @NotBlank(message = "Будь ласка, введіть повідомлення")
    @Size(min = 10, max = 1000, message = "Повідомлення має містити від 10 до 1000 символів")
    private String message;
}

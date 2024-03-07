package com.example.househubadmin.dto.notary;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NotaryDtoForAdd {
    private Long id;
    private MultipartFile image;
    @Size(max = 100, message = "{error.field.size.max}")
    private String name;
    @Size(max = 100, message = "{error.field.size.max}")
    private String surname;
    @NotNull(message = "{error.field.empty}")
    @Pattern(regexp = "^\\+380(31|32|33|34|35|36|37|38|39|41|42|43|44|45|46|47|48|49|50|59|61|63|66|67|68|73|89|91|92|93|94|95|96|97|98|99)\\d{7}$", message = "{error.field.telephone}")
    private String phone;
    @NotNull(message = "{error.field.empty}")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{error.field.email}")
    private String email;
}
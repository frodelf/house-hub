package com.example.househubadmin.validator;

import com.example.househubadmin.dto.notary.NotaryDtoForAdd;
import com.example.househubadmin.service.NotaryService;
import com.example.househubadmin.service.UserService;
import com.example.househubadmin.validator.util.ValidImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class NotaryValidator implements Validator {
    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return NotaryDtoForAdd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NotaryDtoForAdd studentDtoForAdd = (NotaryDtoForAdd) target;
        if (studentDtoForAdd.getId() == null) {
            ValidImageUtil.validImage(studentDtoForAdd.getImage(), errors);
            if (userService.validPhone(studentDtoForAdd.getPhone()))
                errors.rejectValue("telephone", "", "The phone is already in use.");
            if (userService.validEmail(studentDtoForAdd.getEmail()))
                errors.rejectValue("email", "", "The email is already in use.");
        }
    }
}

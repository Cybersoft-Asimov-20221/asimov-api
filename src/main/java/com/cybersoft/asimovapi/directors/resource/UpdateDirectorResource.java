package com.cybersoft.asimovapi.directors.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateDirectorResource {

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String lastName;

    private int age;

    @NotNull
    @NotBlank
    @Size(max = 40)
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String phone;
}

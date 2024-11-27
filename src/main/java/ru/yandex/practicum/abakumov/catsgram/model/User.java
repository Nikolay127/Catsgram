package ru.yandex.practicum.abakumov.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = {"email"})
public class User {

    public Long id;
    public String username;
    public String email;
    private String password;
    public Instant registrationDate;
}

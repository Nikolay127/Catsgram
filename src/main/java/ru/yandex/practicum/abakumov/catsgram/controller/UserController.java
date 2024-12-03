package ru.yandex.practicum.abakumov.catsgram.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.abakumov.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.abakumov.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.abakumov.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        } else if (checkEmail(user)) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user; //пока не знаю, зачем мы возвращаем пользователя, но пусть пока будет по аналогии
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        } else if (checkEmail(user)) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        /*
        Будем изменять уже существующего пользователя, а не тупо менять одного на другого, так как
        пользователь может не передать фулл-пользователя, а только нужно ему поле для изменения
         */
        final User modifiedUser = users.get(user.getId());
        modifiedUser.username = user.username == null ? modifiedUser.username : user.username;
        modifiedUser.setPassword(user.getPassword() == null ? modifiedUser.getPassword() : user.getPassword());
        modifiedUser.email = user.email == null ? modifiedUser.email : user.email;
        return modifiedUser;
    }

    private boolean checkEmail(User user) {
        return users.values()
                .stream()
                .anyMatch(oneuser -> oneuser.getEmail().equals(user.getEmail()));
    }

}

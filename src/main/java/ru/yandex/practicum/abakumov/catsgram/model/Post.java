package ru.yandex.practicum.abakumov.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = {"id"})
public class Post {

    public Long id;
    public long authorId;
    public String description;
    public Instant postDate;

}

package ru.yandex.practicum.abakumov.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
public class Image {

    public Long id;
    public long postId;
    public String originalFileName;
    public String filePath;



}

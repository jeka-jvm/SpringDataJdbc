package org.example.springdatajdbc.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Setter
@Getter
@Table("books")
public class Book {

    @Id
    private Long id;
    private String title;
    private String author;
    private int publicationYear;

}

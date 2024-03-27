package com.example.grpctask.repository;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("books")
public class BookEntity {
    @Id
    @Column("id")
    private UUID id;
    @Column("title")
    private String title;
    @Column("author")
    private String author;
    @Column("isbn")
    private String isbn;
    @Column("quantity")
    private Integer quantity;

}

package com.example.study.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private String fileName;

    @Column
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;
}

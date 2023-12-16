package com.example.project.violearnback.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cifras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private String content;
    private String userName;
    private String cifrasFile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user_notes")
    private User user;

    @Override
    public String toString() {
    	return "Cifras [Id=" + Id + ", title=" + title + ", content=" + content + ", userName=" + userName
    			+ ", cifrasFile=" + cifrasFile + "]";
    }
}

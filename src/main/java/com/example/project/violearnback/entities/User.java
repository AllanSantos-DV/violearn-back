package com.example.project.violearnback.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
    private String userName;
	private String profileImage;
    private String email;
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    @JsonManagedReference("user_post")
    private List<Post> posts;

    @OneToMany
    @JoinColumn(name = "user_id")
    @JsonManagedReference("user_notes")
    private List<Cifras> notes;

    @Override
    public String toString() {
    	return "User [id=" + id + ", name=" + name + ", userName=" + userName + ", profileImage=" + profileImage
    			+ ", email=" + email + ", password=" + password + "]";
    }
}
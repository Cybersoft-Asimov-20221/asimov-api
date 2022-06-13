package com.cybersoft.asimovapi.teachers.domain.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cybersoft.asimovapi.directors.domain.model.entity.Director;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@Entity
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Integer point;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String first_name;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String last_name;

    @NotNull
    private Integer age;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotNull
    @NotBlank
    private String phone;

    //Relationships witch director
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    //Relationships
    //@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    //mappedBy = "teachers")
    //private List<Course> courses;
}

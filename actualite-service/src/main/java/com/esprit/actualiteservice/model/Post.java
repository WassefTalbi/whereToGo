package com.esprit.actualiteservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDate datePost;
    private Long idUser;
    @PrePersist
    protected void onCreate() {
        this.datePost = LocalDate.now();
    }
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.PENDING;
    private String rejectReason;
    @OneToMany(mappedBy ="post",cascade = CascadeType.ALL)
    private List<Comment> comments;

}

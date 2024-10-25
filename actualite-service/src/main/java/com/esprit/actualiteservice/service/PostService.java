package com.esprit.actualiteservice.service;

import com.esprit.actualiteservice.DTO.ActualitéDTO;
import com.esprit.actualiteservice.exception.PostExistsException;
import com.esprit.actualiteservice.modal.User;
import com.esprit.actualiteservice.model.Post;
import com.esprit.actualiteservice.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
    private PostRepository postRepository;
    public Post createPost(ActualitéDTO actualitéDTO, Long idUser) {
        Post post=new Post();
        post.setContent(actualitéDTO.getContent());
        post.setIdUser(idUser);
        return postRepository.save(post);
    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Long id, ActualitéDTO actualitéDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostExistsException("Post not trouvé avec l'id " + id));

        post.setContent(actualitéDTO.getContent());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostExistsException("Post not trouvé avec l'id " + id));

        postRepository.delete(post);
    }
    private Long getCurrentUser(){
        return 1L;
    }
}

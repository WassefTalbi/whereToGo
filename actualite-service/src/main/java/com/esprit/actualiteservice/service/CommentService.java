package com.esprit.actualiteservice.service;

import com.esprit.actualiteservice.DTO.ActualitéDTO;
import com.esprit.actualiteservice.exception.PostExistsException;
import com.esprit.actualiteservice.modal.User;
import com.esprit.actualiteservice.model.Comment;
import com.esprit.actualiteservice.model.Post;
import com.esprit.actualiteservice.repository.CommentRepository;
import com.esprit.actualiteservice.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    public Comment createComment(Long postId ,ActualitéDTO actualitéDTO, User user) {
        Comment comment=new Comment();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostExistsException("Post non trouvé pour cet ID :: " + postId));
        comment.setContent(actualitéDTO.getContent());
        comment.setIdUser(user.getIdUser());
        comment.setPost(post);
        return commentRepository.save(comment);
    }

   /* public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostExistsException("Post non trouvé pour cet ID :: " + postId));
        comment.setPost(post);

        return commentRepository.save(comment);
    }*/

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }


    public Comment updateComment(Long id, ActualitéDTO actualitéDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new PostExistsException("Commentaire not trouvé avec l'id " + id));

        comment.setContent(actualitéDTO.getContent());

        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new PostExistsException("Commentaire not trouvé avec l'id " + id));

        commentRepository.delete(comment);
    }
}


package com.esprit.actualiteservice.controller;

import com.esprit.actualiteservice.DTO.ActualitéDTO;
import com.esprit.actualiteservice.feignClient.UserClient;
import com.esprit.actualiteservice.modal.User;
import com.esprit.actualiteservice.model.Post;
import com.esprit.actualiteservice.service.PostService;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private PostService postService;
    private UserClient userClient;
    @PostMapping("/addPost")
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody @Valid ActualitéDTO actualitéDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            String idUser = "rrrrrrr-hgu";
            Post createdPost = postService.createPost(actualitéDTO, idUser);
            response.put("message", "Post créé avec succès");
            response.put("post", createdPost);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (FeignException.NotFound ex) {
            response.put("error", "Utilisateur non trouvé");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception ex) {
            response.put("error", "Erreur lors de la création du post");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getPosts")
    public ResponseEntity<Map<String, Object>> getAllPosts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Post> posts = postService.getAllPosts();
            response.put("message", "Liste de tous les posts");
            response.put("posts", posts);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("error", "Erreur lors de la récupération des posts");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("updatePost/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long id, @RequestBody @Valid ActualitéDTO actualitéDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Post updatedPost = postService.updatePost(id, actualitéDTO);
            response.put("message", "Post mis à jour avec succès");
            response.put("post", updatedPost);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("error", "Erreur lors de la mise à jour du post");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("deletePost/{id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            postService.deletePost(id);
            response.put("message", "Post supprimé avec succès");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception ex) {
            response.put("error", "Erreur lors de la suppression du post");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

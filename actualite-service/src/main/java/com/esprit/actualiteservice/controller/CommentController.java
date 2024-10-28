package com.esprit.actualiteservice.controller;

import com.esprit.actualiteservice.DTO.ActualitéDTO;
import com.esprit.actualiteservice.feignClient.UserClient;
import com.esprit.actualiteservice.modal.User;
import com.esprit.actualiteservice.model.Comment;
import com.esprit.actualiteservice.service.CommentService;
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
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private UserClient userClient;



    @PostMapping("/addComment/{idPost}")
    public ResponseEntity<Map<String, Object>> createComment(@PathVariable Long idPost,@RequestBody @Valid ActualitéDTO actualitéDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user =userClient.getCurrentConnected();
            Comment createdComment = commentService.createComment(idPost,actualitéDTO,user);
            response.put("message", "Commentaire créé avec succès");
            response.put("comment", createdComment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.put("error", "Erreur lors de la création du commentaire");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/allComments")
    public ResponseEntity<Map<String, Object>> getAllComments() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Comment> comments = commentService.getAllComments();
            response.put("message", "Liste de tous les commentaires");
            response.put("comments", comments);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("error", "Erreur lors de la récupération des commentaires");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("updateComment/{id}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable Long id,  @RequestBody @Valid ActualitéDTO actualitéDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Comment updatedComment = commentService.updateComment(id, actualitéDTO);
            response.put("message", "Commentaire mis à jour avec succès");
            response.put("comment", updatedComment);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("error", "Erreur lors de la mise à jour du commentaire");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("deleteComment/{id}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.deleteComment(id);
            response.put("message", "Commentaire supprimé avec succès");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception ex) {
            response.put("error", "Erreur lors de la suppression du commentaire");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}


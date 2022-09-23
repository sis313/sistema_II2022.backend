package ucb.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ucb.app.dto.CommentDto;
import ucb.app.model.Comment;
import ucb.app.service.CommentService;

@RestController
@RequestMapping("api/comment")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments() {
        List<CommentDto> comments = commentService.findAllDto();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping(path = "/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("commentId") Integer commentId) throws Exception {
        CommentDto comment = commentService.findByIdDto(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDto> postComment(@RequestBody Comment comment) {
        CommentDto response = commentService.saveDto(comment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/{commentId}")
    public ResponseEntity<CommentDto> putComment(@PathVariable("commentId") Integer commentId,
            @RequestBody Comment comment) {
        CommentDto response = commentService.updateDto(commentId, comment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable("commentId") Integer commentId) {
        CommentDto response = commentService.deleteByIdDto(commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

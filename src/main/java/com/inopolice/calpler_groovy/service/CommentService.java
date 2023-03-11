package com.inopolice.calpler_groovy.service;

import com.inopolice.calpler_groovy.entity.Comment;
import com.inopolice.calpler_groovy.exception.DataNotFoundException;
import com.inopolice.calpler_groovy.form.CommentForm;
import com.inopolice.calpler_groovy.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void createComment(Integer id, CommentForm commentForm, String username, Integer commentGroup) {
        Comment comment = new Comment();
        comment.setBoardId(id);
        comment.setText(commentForm.getText());
        comment.setAuthor(username);
        comment.setGroupNum(commentGroup);
        Comment createdComment = commentRepository.save(comment);
        if(commentGroup == 0) {
            createdComment.setGroupNum(createdComment.getId());
            commentRepository.save(createdComment);
        }
    }

    public List<Comment> getCommentByBoardId(Integer id) {
        return commentRepository.findByBoardIdOrderByGroupNumAscIdAsc(id);
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        }
        else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void deleteComment(Comment comment) {
        if (comment.getId() == comment.getGroupNum() && commentRepository.findByGroupNum(comment.getGroupNum()).size() != 1) {
                comment.setText("삭제된 댓글입니다");
                commentRepository.save(comment);
        }
        else {
            commentRepository.delete(comment);
        }
    }
}

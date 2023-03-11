package com.inopolice.calpler_groovy.controller;

import com.inopolice.calpler_groovy.entity.Comment;
import com.inopolice.calpler_groovy.form.CommentForm;
import com.inopolice.calpler_groovy.service.BoardService;
import com.inopolice.calpler_groovy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/comment")
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String CreateComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal,
                                @RequestParam(value = "commentGroup", defaultValue = "0") Integer commentGroup) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("board", boardService.getBoard(id));
            model.addAttribute("commentList", commentService.getCommentByBoardId(id));
            model.addAttribute("commentForm", commentForm);
            return "board_view";
        }
        commentService.createComment(id, commentForm, principal.getName(), commentGroup);
        return String.format("redirect:/board/view/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String DeleteComment(@PathVariable("id") Integer id, Principal principal) {
        Comment comment =  commentService.getComment(id);
        if (!comment.getAuthor().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
        }
        commentService.deleteComment(comment);
        return String.format("redirect:/board/view/%s", comment.getBoardId());
    }
}

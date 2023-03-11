package com.inopolice.calpler_groovy.controller;

import com.inopolice.calpler_groovy.entity.Board;
import com.inopolice.calpler_groovy.form.BoardForm;
import com.inopolice.calpler_groovy.form.CommentForm;
import com.inopolice.calpler_groovy.service.BoardService;
import com.inopolice.calpler_groovy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/board")
@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/list/{university}")
    public String BoardList(@PathVariable("university") String university,
                            Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
                            @RequestParam(value = "tag", defaultValue = "") String tagString) {
        List<String> tags = Arrays.asList(tagString.split(","));
        if (university.equals("")) return "redirect:/select";
        Page<Board> boardPage = boardService.getList(page, tags, university);
        int nowPage = boardPage.getPageable().getPageNumber() + 1;
        int startPage = Math.max(Math.min(Math.max(nowPage - 2, 1), boardPage.getTotalPages() - 4), 1);
        int endPage = Math.max(Math.min(startPage + 4, boardPage.getTotalPages()), 1);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("tagList", tags);
        model.addAttribute("tagString", tagString);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("1", "대학원");
        map.put("2", "학사 취업");
        map.put("3", "해외 유학");
        map.put("4", "박사 취업");
        map.put("5", "해외 취업");
        map.put("6", "창업");
        model.addAttribute("tagMap", map);
        model.addAttribute("university", university);
        return "board_list";
    }


    @GetMapping("/view/{id}")
    public String BoardView(Model model, @PathVariable("id") Integer id, CommentForm commentForm,
                            @RequestParam(value = "commentGroup", defaultValue = "0") Integer commentGroup) {
        model.addAttribute("board", boardService.getBoard(id));
        model.addAttribute("commentList", commentService.getCommentByBoardId(id));
        model.addAttribute("commentForm", commentForm);
        model.addAttribute("commentGroup", commentGroup);
        return "board_view";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String BoardCreate(Model model, BoardForm boardForm) {
        model.addAttribute("boardForm", boardForm);
        model.addAttribute("tagString", "");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("1", "대학원");
        map.put("2", "학사 취업");
        map.put("3", "해외 유학");
        map.put("4", "박사 취업");
        map.put("5", "해외 취업");
        map.put("6", "창업");
        model.addAttribute("tagMap", map);
        model.addAttribute("originFilename", "");
        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("postech", "포스텍");
        map2.put("pohangdae", "포항대학교");
        map2.put("other", "다른대학들(임시)");
        model.addAttribute("map", map2);
        return "board_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String BoardCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal, MultipartFile file) throws Exception {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }
        boardService.createBoard(boardForm, principal.getName(), file);
        return String.format("redirect:/board/list/%s", boardForm.getUniversity());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String BoardModify(BoardForm boardForm, @PathVariable("id") Integer id, Principal principal, Model model) {
        Board board = boardService.getBoard(id);
        if(!board.getAuthor().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        boardForm.setName(board.getName());
        boardForm.setDepartment(board.getDepartment());
        boardForm.setJob(board.getJob());
        boardForm.setExperience(board.getExperience());
        boardForm.setText(board.getText());
        boardForm.setLink(board.getLink());
        boardForm.setUniversity(board.getUniversity());
        model.addAttribute("boardForm", boardForm);
        String tagString = board.getTag();
        if (tagString == null) tagString = "";
        model.addAttribute("tagString", tagString);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("1", "대학원");
        map.put("2", "학사 취업");
        map.put("3", "해외 유학");
        map.put("4", "박사 취업");
        map.put("5", "해외 취업");
        map.put("6", "창업");
        model.addAttribute("tagMap", map);
        model.addAttribute("originFilename", board.getFilename());
        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("postech", "포스텍");
        map2.put("pohangdae", "포항대학교");
        map2.put("other", "다른대학들(임시)");
        model.addAttribute("map", map2);
        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String BoardModify(@Valid BoardForm boardForm, @PathVariable("id") Integer id, BindingResult bindingResult, Principal principal, MultipartFile file) throws Exception {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }
        Board board = boardService.getBoard(id);
        if (!board.getAuthor().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        boardService.modifyBoard(board, boardForm, file);
        return String.format("redirect:/board/view/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable("id") Integer id, Principal principal) {
        Board board = boardService.getBoard(id);
        if (!board.getAuthor().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        boardService.deleteBoard(board);
        return String.format("redirect:/board/list/%s", board.getUniversity());
    }
}

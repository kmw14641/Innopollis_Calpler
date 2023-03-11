package com.inopolice.calpler_groovy.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.inopolice.calpler_groovy.entity.Board;
import com.inopolice.calpler_groovy.exception.DataNotFoundException;
import com.inopolice.calpler_groovy.form.BoardForm;
import com.inopolice.calpler_groovy.repository.BoardCustomRepository;
import com.inopolice.calpler_groovy.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> getList(int page, List<String> tags, String university) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return boardRepository.findAllWithTag(pageable, tags, university);
    }

    public Board getBoard(Integer id) {
        Optional<Board> board = boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        }
        else {
            throw new DataNotFoundException("board not found");
        }
    }

    public void createBoard(BoardForm boardForm, String username, MultipartFile file) throws Exception {
        Board board = new Board();
        board.setName(boardForm.getName());
        board.setDepartment(boardForm.getDepartment());
        board.setJob(boardForm.getJob());
        board.setExperience(boardForm.getExperience());
        board.setText(boardForm.getText());
        board.setLink(boardForm.getLink());
        board.setAuthor(username);
        board.setTag(boardForm.getTag());
        board.setUniversity(boardForm.getUniversity());

        /*
        if(!file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            board.setFilename(fileName);
            board.setFilepath(fileName);
        }
        */
        if(!file.isEmpty()) {
            String constr = "AccountName=calpler;" +
                    "AccountKey=qaRBuGlk3lx9Kn3ED/+koTDtp/hSkq2ML7532ayddaMHLIMu352QmVpmCPMLqE8C2MS9xLOA3fhz+ASt5oqNvw==;" +
                    "EndpointSuffix=core.windows.net;" +
                    "DefaultEndpointsProtocol=https;";
            BlobContainerClient container = new BlobContainerClientBuilder()
                    .connectionString(constr)
                    .containerName("calplercontainer")
                    .buildClient();
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            BlobClient blob = container.getBlobClient(fileName);
            blob.upload(file.getInputStream(), file.getSize(), true);
            board.setFilename(fileName);
            board.setFilepath("https://calpler.blob.core.windows.net/calplercontainer/" + fileName);
        }

        boardRepository.save(board);
    }

    public void modifyBoard(Board board, BoardForm boardForm, MultipartFile file) throws Exception{
        board.setName(boardForm.getName());
        board.setDepartment(boardForm.getDepartment());
        board.setJob(boardForm.getJob());
        board.setExperience(boardForm.getExperience());
        board.setText(boardForm.getText());
        board.setLink(boardForm.getLink());
        board.setTag(boardForm.getTag());
        board.setUniversity(boardForm.getUniversity());

        /*
        if (!file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";

            if (board.getFilename() != null) {
                File deleteFile = new File(projectPath, board.getFilename());
                if (deleteFile.exists()) deleteFile.delete();
            }

            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            board.setFilename(fileName);
            board.setFilepath(fileName);
        }
        */

        if(!file.isEmpty()) {

            String constr = "AccountName=calpler;" +
                    "AccountKey=qaRBuGlk3lx9Kn3ED/+koTDtp/hSkq2ML7532ayddaMHLIMu352QmVpmCPMLqE8C2MS9xLOA3fhz+ASt5oqNvw==;" +
                    "EndpointSuffix=core.windows.net;" +
                    "DefaultEndpointsProtocol=https;";
            BlobContainerClient container = new BlobContainerClientBuilder()
                    .connectionString(constr)
                    .containerName("calplercontainer")
                    .buildClient();

            if(board.getFilename() != null) {
                BlobClient deleteBlob = container.getBlobClient(board.getFilename());
                deleteBlob.deleteIfExists();
            }

            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            BlobClient blob = container.getBlobClient(fileName);
            blob.upload(file.getInputStream(), file.getSize(), true);
            board.setFilename(fileName);
            board.setFilepath("https://calpler.blob.core.windows.net/calplercontainer/" + fileName);
        }

        boardRepository.save(board);
    }

    public void deleteBoard(Board board) {
        if (board.getFilename() != null) {
            String constr = "AccountName=calpler;" +
                    "AccountKey=qaRBuGlk3lx9Kn3ED/+koTDtp/hSkq2ML7532ayddaMHLIMu352QmVpmCPMLqE8C2MS9xLOA3fhz+ASt5oqNvw==;" +
                    "EndpointSuffix=core.windows.net;" +
                    "DefaultEndpointsProtocol=https;";
            BlobContainerClient container = new BlobContainerClientBuilder()
                    .connectionString(constr)
                    .containerName("calplercontainer")
                    .buildClient();
            BlobClient deleteBlob = container.getBlobClient(board.getFilename());
            deleteBlob.deleteIfExists();
        }
        boardRepository.delete(board);
    }
}

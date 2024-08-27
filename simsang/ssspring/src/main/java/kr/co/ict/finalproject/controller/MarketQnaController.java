package kr.co.ict.finalproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.ict.finalproject.service.MarketQnaService;
import kr.co.ict.finalproject.vo.MarketQnaVO;

@RestController
@RequestMapping("/marketqna")
public class MarketQnaController {
    // 개발자 : 김건우 윤수영
    @Autowired
    private MarketQnaService marketqnaservice;

    private final static Path uploadDir = Paths.get("uploads");

    private String getExtension(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.indexOf(".");
        if (index > -1) {
            return fileName.substring(index);
        }
        return "";
    }

    @PostMapping(value = "/qnaadd", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> addQna(
            @RequestParam("qna") String qnaJson,
            @RequestPart(value = "file", required = false) List<MultipartFile> file) {
        ObjectMapper objectMapper = new ObjectMapper();
        MarketQnaVO vo = new MarketQnaVO();
        if (file != null && !file.isEmpty()) {
            String imgName = null;
            for (MultipartFile multipartFile : file) {
                imgName = UUID.randomUUID() + getExtension(multipartFile);
                Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                try {
                    vo = objectMapper.readValue(qnaJson, MarketQnaVO.class);
                    Files.copy(multipartFile.getInputStream(), destinationFile);
                    marketqnaservice.add(vo);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save file: " + imgName, e);
                }
            }
        }
        return ResponseEntity.ok().body(vo);
    }

    @PostMapping(value = "/qnaupdate", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> updateQna(
            @RequestPart("qna") String qnaJson,
            @RequestPart(value = "file", required = false) List<MultipartFile> file) {
        ObjectMapper objectMapper = new ObjectMapper();
        MarketQnaVO vo;
        String imgName = null;
        try {
            vo = objectMapper.readValue(qnaJson, MarketQnaVO.class);
            if (file != null && !file.isEmpty()) {
                for (MultipartFile multipartFile : file) {
                    imgName = UUID.randomUUID() + getExtension(multipartFile);
                    Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                    Files.copy(multipartFile.getInputStream(), destinationFile);
                }
            }
            marketqnaservice.update(vo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Updating diary failed");
        }
        return ResponseEntity.ok().body("Updated successfully");
    }

    @GetMapping("/qnalist")
    public List<MarketQnaVO> listQna() {
        return marketqnaservice.list();
    }

    @GetMapping("/qnadetail/{num}")
    public ResponseEntity<?> detailQna(@PathVariable(name = "num") int num) {
        MarketQnaVO qnaDetail = marketqnaservice.detail(num);
        if (qnaDetail != null) {
            return ResponseEntity.ok().body(qnaDetail);
        } else {
            return ResponseEntity.status(404).body("QnA not found");
        }
    }

    @DeleteMapping("/qnadelete/{num}")
    public ResponseEntity<?> deleteQna(@PathVariable(name = "num") int num) {
        try {
            marketqnaservice.delete(num);
            return ResponseEntity.ok().body("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Deletion failed");
        }
    }

}

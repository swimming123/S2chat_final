package kr.co.ict.finalproject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.ict.finalproject.service.QnaService;
import kr.co.ict.finalproject.vo.QnaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/qna")
public class QnaController {
    // 개발자 : 정준영 윤수영
    @Autowired
    private QnaService qnaservice;

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
            @RequestPart(value = "file", required = false) List<MultipartFile> mf) {
        ObjectMapper objectMapper = new ObjectMapper();
        QnaVO vo;
        String imgName = null;
        try {
            vo = objectMapper.readValue(qnaJson, QnaVO.class);
            if (mf != null && !mf.isEmpty()) {
                for (MultipartFile multipartFile : mf) {
                    imgName = UUID.randomUUID() + getExtension(multipartFile);
                    Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                    Files.copy(multipartFile.getInputStream(), destinationFile);
                    vo.setImgname(imgName);
                }
            }
            qnaservice.add(vo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Updating diary failed");
        }
        return ResponseEntity.ok().body("Updated successfully");
    }

    @PostMapping("/qnaupdate")
    public ResponseEntity<?> updateQna(
            @RequestParam("qna") String qnaJson,
            @RequestPart(value = "file", required = false) List<MultipartFile> mf) {
        ObjectMapper objectMapper = new ObjectMapper();
        QnaVO vo;
        String imgName = null;
        try {
            vo = objectMapper.readValue(qnaJson, QnaVO.class);
            if (mf != null && !mf.isEmpty()) {
                for (MultipartFile multipartFile : mf) {
                    imgName = UUID.randomUUID() + getExtension(multipartFile);
                    Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                    Files.copy(multipartFile.getInputStream(), destinationFile);
                    vo.setImgname(imgName);
                }
            }
            qnaservice.update(vo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Updating diary failed");
        }
        return ResponseEntity.ok().body("Updated successfully");
    }

    @GetMapping("/qnalist/{membernum}")
    public ResponseEntity<?> listQna(@PathVariable(name = "membernum") int membernum) {
        List<QnaVO> qnaList = qnaservice.list(membernum);
        return ResponseEntity.ok().body(qnaList);
    }

    @GetMapping("/qnadetail/{num}")
    public ResponseEntity<?> detailQna(@PathVariable(name = "num") int num) {
        QnaVO qnaDetail = qnaservice.detail(num);
        if (qnaDetail != null) {
            return ResponseEntity.ok().body(qnaDetail);
        } else {
            return ResponseEntity.status(404).body("QnA not found");
        }
    }

    @DeleteMapping("/qnadelete/{num}")
    public ResponseEntity<?> deleteQna(@PathVariable(name = "num") int num) {
        try {
            qnaservice.delete(num);
            return ResponseEntity.ok().body("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Deletion failed");
        }
    }
}

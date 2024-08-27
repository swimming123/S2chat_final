package kr.co.ict.finalproject.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import kr.co.ict.finalproject.service.DiaryService;
import kr.co.ict.finalproject.vo.DiaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/diary")
public class DiaryController {
    // 개발자 : 김건우, 박성호, 허호준, 윤수영
    private final static Path uploadDir = Paths.get("uploads");

    @Autowired
    private DiaryService diaryService;

    private String getExtension(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.indexOf(".");
        if (index > -1) {
            return fileName.substring(index);
        }
        return "";
    }

    @PostMapping(value = "/diaryAdd", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> diaryAdd(
            @RequestParam("newEntry") String mydiaryJson,
            @RequestPart(value = "diaryimg", required = false) List<MultipartFile> mf) {
        ObjectMapper objectMapper = new ObjectMapper();
        DiaryVO vo = new DiaryVO();
        try {
            vo = objectMapper.readValue(mydiaryJson, DiaryVO.class);
            if (mf != null && !mf.isEmpty()) {
                // 디렉토리 존재 여부 확인 및 생성
                if (!Files.exists(uploadDir)) {
                    try {
                        Files.createDirectories(uploadDir);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to create upload directory", e);
                    }
                }

                for (MultipartFile multipartFile : mf) {
                    String imgName = UUID.randomUUID() + getExtension(multipartFile);
                    Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                    try {
                        Files.copy(multipartFile.getInputStream(), destinationFile);
                        vo.setDiaryimg(imgName);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save file: " + imgName, e);
                    }
                }
            }
            diaryService.diaryAdd(vo);
            return ResponseEntity.ok().body(vo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Adding diary failed");
        }
    }

    @PostMapping(value = "/diaryUpdate", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> updateDiary(
            @RequestParam("newEntry") String newEntryJson,
            @RequestPart(value = "diaryimg", required = false) List<MultipartFile> mf) {
        ObjectMapper objectMapper = new ObjectMapper();
        DiaryVO diaryVO;
        String imgName = null;
        try {
            diaryVO = objectMapper.readValue(newEntryJson, DiaryVO.class);
            if (mf != null && !mf.isEmpty()) {
                for (MultipartFile multipartFile : mf) {
                    imgName = UUID.randomUUID() + getExtension(multipartFile);
                    Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                    Files.copy(multipartFile.getInputStream(), destinationFile);
                    diaryVO.setDiaryimg(imgName); // 새로운 이미지 이름을 DiaryVO에 설정
                }
            }
            diaryService.diaryUpdate(diaryVO);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일기 수정 실패");
        }
        return ResponseEntity.ok().body(diaryVO);
    }

    @GetMapping("/diaryList/{membernum}")
    public List<DiaryVO> diaryList(@PathVariable("membernum") int membernum) {
        List<DiaryVO> diaries = diaryService.diaryList(membernum);
        return diaries;
    }

    @GetMapping("/diaryDetail/{num}")
    public DiaryVO diaryDetail(@PathVariable int num) {
        return diaryService.diaryDetail(num);
    }

    @DeleteMapping("/diaryDelete/{num}")
    public void diaryDelete(@PathVariable int num) {
        diaryService.diaryDelete(num);
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename)
            throws MalformedURLException {
        Path file = uploadDir.resolve(filename).normalize().toAbsolutePath();
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok().body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
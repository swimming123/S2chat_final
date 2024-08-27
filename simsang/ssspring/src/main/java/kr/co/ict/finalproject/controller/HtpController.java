package kr.co.ict.finalproject.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ict.finalproject.service.HtpService;
import kr.co.ict.finalproject.vo.HtpVO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/htp")
public class HtpController {
    // 개발자 : 허호준 윤수영
    @Autowired
    private HtpService htpService;

    private final static Path uploadDir = Paths.get("uploads");

    private String getExtension(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.indexOf(".");
        if (index > -1) {
            return fileName.substring(index);
        }
        return "";
    }

    @GetMapping("/htpre")
    public List<HtpVO> htpre() {
        return htpService.htplist();
    }

    @PostMapping(value = "/htpadd", produces = "application/json; charset=UTF-8")
    public void htpadd(@RequestPart(value = "htpfile", required = false) List<MultipartFile> htpfile,
            @ModelAttribute HtpVO vo) throws IOException {
        if (htpfile != null && !htpfile.isEmpty()) {
            String imgName = null;
            for (MultipartFile multipartFile : htpfile) {
                imgName = UUID.randomUUID() + getExtension(multipartFile);
                Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                try {
                    Files.copy(multipartFile.getInputStream(), destinationFile);
                    vo.setHtpimg(imgName);
                    htpService.htpadd(vo);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save file: " + imgName, e);
                }
            }
        }
    }

    @PostMapping("htpdelete")
    public void htpdelete(@RequestBody int num) {

    }

}

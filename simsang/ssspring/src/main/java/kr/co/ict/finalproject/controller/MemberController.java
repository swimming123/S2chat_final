package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ict.finalproject.service.MemberService;
import kr.co.ict.finalproject.vo.MemberVO;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberController {
    // 개발자 : 이승희 윤수영
    private final static Path uploadDir = Paths.get("uploads");

    @Autowired
    private MemberService memberService;

    private String getExtension(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.indexOf(".");
        if (index > -1) {
            return fileName.substring(index);
        }
        return "";
    }

    @PostMapping("/add")
    public void add(@ModelAttribute MemberVO vo) {
        memberService.add(vo);
    }

    @PostMapping(value = "/memAdd", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> memAdd(@RequestPart(value = "file", required = false) List<MultipartFile> mf,
            @ModelAttribute MemberVO vo) {
        String imgName = null;
        try {
            if (mf != null && !mf.isEmpty()) {
                for (MultipartFile multipartFile : mf) {
                    imgName = UUID.randomUUID() + getExtension(multipartFile);
                    Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                    Files.copy(multipartFile.getInputStream(), destinationFile);
                    vo.setImgn(imgName);
                }
            }
            memberService.add(vo);
            return ResponseEntity.ok().body(vo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing member addition");
        }
    }

    @RequestMapping("/list")
    public List<MemberVO> list() {
        return memberService.list();
    }

    @DeleteMapping("/delete/{member_num}")
    public void delete(@PathVariable("member_num") int member_num) {
        memberService.delete(member_num);
    }

    @PutMapping("/update/{member_id}")
    public void update(@PathVariable("member_id") String id, @RequestBody MemberVO member) {
        memberService.update(member);
    }

    @PostMapping("/idcheck")
    public int idcheck(@RequestParam("id") String id) {
        return memberService.idCheck(id);
    }

}
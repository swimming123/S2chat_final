package kr.co.ict.finalproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ict.finalproject.service.ReviewService;
import kr.co.ict.finalproject.vo.ReviewVO;

@RestController
@RequestMapping("/review")
public class ReviewController {
    // 개발자 : 송지미 윤수영
    @Autowired
    private ReviewService reviewService;

    private final static Path uploadDir = Paths.get("uploads");

    private String getExtension(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.indexOf(".");
        if (index > -1) {
            return fileName.substring(index);
        }
        return "";
    }

    @PostMapping(value = "/reviewinsert", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> addReview(@RequestPart(value = "file", required = false) MultipartFile mf,
            @ModelAttribute ReviewVO vo) throws IOException {
        String imgName = null;
        try {
            if (mf != null && !mf.isEmpty()) {
                imgName = UUID.randomUUID() + getExtension(mf);
                Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                try {
                    Files.copy(mf.getInputStream(), destinationFile);
                    vo.setReview_imagename(imgName);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save file: " + imgName, e);
                }
            }
            reviewService.addReview(vo);
            return ResponseEntity.ok("리뷰등록성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file");
        }
    }

    @PostMapping("/reviewUpdate/{num}")
    public ResponseEntity<?> updateReview(@PathVariable("num") Long num, @RequestBody ReviewVO reviewDetails) {
        reviewService.updateReview(num, reviewDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reviewlist")
    public ResponseEntity<?> getReviews() {
        List<ReviewVO> reviews = reviewService.getAllReview();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/reviewpro")
    public ResponseEntity<?> getReviewpro(@RequestParam("productnum") String productnum) {
        List<ReviewVO> reviews = reviewService.getReviewpro(productnum);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviewDetail/{num}")
    public ResponseEntity<ReviewVO> getReviewDetail(@PathVariable("num") Long num) {
        Optional<ReviewVO> reviewOptional = reviewService.getReviewDetail(num);
        if (reviewOptional.isPresent()) {
            return new ResponseEntity<>(reviewOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/reviewDelete/{num}")
    public ResponseEntity<Void> deleteReview(@PathVariable("num") Long num) {
        reviewService.deleteReview(num);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

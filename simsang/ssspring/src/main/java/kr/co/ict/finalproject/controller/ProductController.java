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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ict.finalproject.service.ProductService;
import kr.co.ict.finalproject.vo.ProductVO;

@RestController
@RequestMapping("/product")
public class ProductController {
    // 개발자 : 송지미 윤수영
    @Autowired
    private ProductService productService;

    private final static Path uploadDir = Paths.get("uploads");

    private String getExtension(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.indexOf(".");
        if (index > -1) {
            return fileName.substring(index);
        }
        return "";
    }

    @PostMapping(value = "/proAdd", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> proAdd(@RequestPart(value = "file", required = false) List<MultipartFile> mf,
            @ModelAttribute ProductVO vo) {
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
                    vo.setProduct_image(imgName);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save file: " + imgName, e);
                }
            }
        }
        productService.proAdd(vo);
        return ResponseEntity.ok().body(vo);
    }

    @PostMapping(value = "/proUpdate", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> proUpdate(@RequestPart(value = "product") ProductVO vo,
            @RequestPart(value = "file", required = false) List<MultipartFile> mf) {
        String imgName = null;
        try {
            if (mf != null && !mf.isEmpty()) {
                for (MultipartFile multipartFile : mf) {
                    imgName = UUID.randomUUID() + getExtension(multipartFile);
                    Path destinationFile = uploadDir.resolve(imgName).normalize().toAbsolutePath();
                    try {
                        Files.copy(multipartFile.getInputStream(), destinationFile);
                        vo.setImagePath(imgName);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save file: " + imgName, e);
                    }
                }
            }
            productService.proUpdate(vo);
            return ResponseEntity.ok().body(vo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing member addition");
        }
    }

    @GetMapping("/proList")
    public ResponseEntity<List<ProductVO>> proList() {
        return ResponseEntity.ok(productService.list());
    }

    @GetMapping("/proDetail/{num}")
    public ProductVO proDetail(@PathVariable("num") int num) {
        return productService.detail(num);
    }

    @DeleteMapping("/proDelete/{num}")
    public ResponseEntity<String> proDelete(@PathVariable("num") int num) {
        productService.delete(num);
        return ResponseEntity.ok("상품삭제성공");
    }

}

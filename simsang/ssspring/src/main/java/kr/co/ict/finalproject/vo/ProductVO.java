package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("productvo")
public class ProductVO {
    // 개발자 : 송지미
    private int product_num;
    private String product_category;
    private String product_image;
    private String product_name;
    private double product_price;
    private int product_stock;
    private String product_manufacturer;
    private String product_registrant;
    @JsonIgnore
    private MultipartFile mfile;
    private String imagePath;
    private int product_hit;

}

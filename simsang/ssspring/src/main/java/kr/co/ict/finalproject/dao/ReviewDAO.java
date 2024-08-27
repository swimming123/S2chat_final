package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ict.finalproject.vo.ReviewVO;

@Mapper
public interface ReviewDAO {
    // 개발자 : 송지미
    void insertReview(ReviewVO review);

    List<ReviewVO> selectAllReview();

    List<ReviewVO> selectProductnum(@Param("productnum") String productnum);

    ReviewVO selectReviewById(@Param("review_num") Long review_num);

    void updateReview(ReviewVO review);

    void deleteReview(@Param("review_num") Long review_num);
}

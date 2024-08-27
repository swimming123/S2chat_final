package kr.co.ict.finalproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.ReviewDAO;
import kr.co.ict.finalproject.vo.ReviewVO;

@Service
public class ReviewService {
    // 개발자 : 송지미

    private final ReviewDAO reviewDAO;

    @Autowired
    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public List<ReviewVO> getAllReview() {
        return reviewDAO.selectAllReview();
    }

    public List<ReviewVO> getReviewpro(String productnum) {
        return reviewDAO.selectProductnum(productnum);
    }

    public void addReview(ReviewVO reviewVO) {
        reviewDAO.insertReview(reviewVO);
    }

    public Optional<ReviewVO> getReviewDetail(Long num) {
        return Optional.ofNullable(reviewDAO.selectReviewById(num));
    }

    public void updateReview(Long num, ReviewVO reviewDetails) {
        reviewDetails.setReview_num(num);
        reviewDAO.updateReview(reviewDetails);
    }

    public void deleteReview(Long num) {
        reviewDAO.deleteReview(num);

    }
}

package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ict.finalproject.vo.EmotionVO;

@Mapper
public interface EmotionDao {
    // 개발자 : 박성호
    public List<EmotionVO> listEmotion(@Param("membernum") int membernum);

    public List<EmotionVO> weekEmotion(@Param("membernum") int membernum);

    public List<EmotionVO> monthEmotion(@Param("membernum") int membernum);

    public List<EmotionVO> happydays(@Param("membernum") int membernum);

    public List<EmotionVO> bubbleemotion(@Param("membernum") int membernum);

}

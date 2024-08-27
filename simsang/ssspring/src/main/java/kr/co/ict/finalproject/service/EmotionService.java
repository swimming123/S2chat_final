package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.EmotionDao;
import kr.co.ict.finalproject.vo.EmotionVO;

@Service
public class EmotionService {
    // 개발자 : 박성호

    @Autowired
    private EmotionDao emotionDao;

    public List<EmotionVO> listEmotion(int membernum) {
        return emotionDao.listEmotion(membernum);
    }

    public List<EmotionVO> weekEmotion(int membernum) {
        return emotionDao.weekEmotion(membernum);
    }

    public List<EmotionVO> monthEmotion(int membernum) {
        return emotionDao.monthEmotion(membernum);
    }

    public List<EmotionVO> happydays(int membernum) {
        return emotionDao.happydays(membernum);
    }

    public List<EmotionVO> bubbleemotion(int membernum) {
        return emotionDao.bubbleemotion(membernum);
    }
}

package kr.co.ict.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.TalkDAO;
import kr.co.ict.finalproject.vo.TalkVO;

@Service
public class TalkService {
    // 개발자 : 정준영

    @Autowired
    private TalkDAO talkDAO;

    public void add(TalkVO vo) {
        talkDAO.add(vo);
    }
}

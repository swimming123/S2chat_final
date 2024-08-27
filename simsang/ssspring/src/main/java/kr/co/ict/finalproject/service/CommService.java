package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.CommDAO;
import kr.co.ict.finalproject.vo.CommVO;

@Service
public class CommService {
    // 개발자 : 정준영

    @Autowired
    private CommDAO commDAO;

    public void add(CommVO vo) {
        commDAO.add(vo);
    }

    public List<CommVO> list() {
        return commDAO.list();
    }

    public CommVO detail(int num) {
        return commDAO.detail(num);
    }
}

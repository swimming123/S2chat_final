package kr.co.ict.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.QnaDAO;
import kr.co.ict.finalproject.vo.QnaVO;

import java.util.List;

@Service
public class QnaService {
    // 개발자 : 정준영

    @Autowired
    private QnaDAO qnadao;

    public void add(QnaVO vo) {
        qnadao.add(vo);
    }

    public List<QnaVO> list(int membernum) {
        return qnadao.list(membernum);
    }

    public QnaVO detail(int num) {
        return qnadao.detail(num);
    }

    public void delete(int num) {
        qnadao.delete(num);
    }

    public void update(QnaVO vo) {
        qnadao.update(vo);
    }
}

package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.ResultDao;
import kr.co.ict.finalproject.vo.ResultVO;

@Service
public class ResultService {
    // 개발자 : 박성호

    @Autowired
    private ResultDao resultDao;

    public List<ResultVO> listResult() {
        return resultDao.listResult();
    }

    public List<ResultVO> weekCount(int membernum) {
        return resultDao.weekCount(membernum);
    }
}

package kr.co.ict.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import kr.co.ict.finalproject.dao.HtpDao;
import kr.co.ict.finalproject.vo.HtpVO;

@Service
public class HtpService {
    // 개발자 : 허호준

    @Autowired
    private HtpDao htpDao;

    public void htpadd(HtpVO vo) {
        htpDao.htpadd(vo);
    }

    public List<HtpVO> htplist() {
        return htpDao.htplist();
    }

    public void htpdelete(int num) {
        htpDao.htpdelete(num);
    }
}

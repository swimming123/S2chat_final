package kr.co.ict.finalproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.ict.finalproject.dao.MarketQnaDAO;
import kr.co.ict.finalproject.vo.MarketQnaVO;

@Service
public class MarketQnaService {
    // 개발자 : 김건우

    @Autowired
    private MarketQnaDAO marketqnadao;

    public void add(MarketQnaVO vo) {
        marketqnadao.add(vo);

    }

    public List<MarketQnaVO> list() {
        return marketqnadao.list();
    }

    public MarketQnaVO detail(int num) {
        return marketqnadao.detail(num);
    }

    public void delete(int num) {
        marketqnadao.delete(num);
    }

    public void update(MarketQnaVO vo) {
        marketqnadao.update(vo);
    }

}

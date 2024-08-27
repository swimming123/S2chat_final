package kr.co.ict.finalproject.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import kr.co.ict.finalproject.dao.ProductDAO;
import kr.co.ict.finalproject.vo.ProductVO;

@Service
public class ProductService {
    // 개발자 : 송지미
    @Autowired
    private ProductDAO productsDao;

    public void proAdd(ProductVO vo) {
        productsDao.proAdd(vo);
    }

    public List<ProductVO> list() {
        return productsDao.proList();
    }

    public ProductVO detail(int num) {
        hit(num);
        return productsDao.proDetail(num);
    }

    public void delete(int num) {
        productsDao.proDelete(num);
    }

    public int proTotalCount(Map<String, String> map) {
        return productsDao.proTotalCount(map);
    }

    public void proUpdate(ProductVO vo) {
        productsDao.proUpdate(vo);
    }

    public void hit(int num) {
        productsDao.proHit(num);
    }
}

package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.AddressDAO;
import kr.co.ict.finalproject.vo.AddressVO;

@Service
public class AddressService {
    // 개발자 : 이승희
    @Autowired
    private AddressDAO addressDAO;

    public void add(AddressVO vo) {
        addressDAO.addressadd(vo);
    }

    public List<AddressVO> list(Long membernum) {
        return addressDAO.addresslist(membernum);
    }

    public AddressVO detail(Long addressnum) {
        return addressDAO.addressdetail(addressnum);
    }

    public void update(AddressVO vo) {
        addressDAO.addressupdate(vo);
    }

    public void delete(Long num) {
        addressDAO.addressdelete(num);
    }

}
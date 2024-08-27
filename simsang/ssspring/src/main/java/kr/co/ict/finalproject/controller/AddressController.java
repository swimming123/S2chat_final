package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.AddressService;
import kr.co.ict.finalproject.vo.AddressVO;

@RestController
@RequestMapping("/address")
public class AddressController {
    // 개발자 : 이승희
    @Autowired
    public AddressService addressService;

    @PostMapping("add")
    public void addressadd(@ModelAttribute AddressVO vo) {
        addressService.add(vo);
    }

    @PostMapping("list")
    public List<AddressVO> addresslist(@RequestParam("membernum") Long membernum) {
        return addressService.list(membernum);
    }

    @PostMapping("detail")
    public AddressVO addressdetail(@RequestParam("addressnum") Long addressnum) {
        return addressService.detail(addressnum);
    }

    @PostMapping("update")
    public void addressupdate(@ModelAttribute AddressVO vo) {
        addressService.update(vo);
    }

    @PostMapping("delete")
    public void delete(@RequestParam("addressnum") Long addressnum) {
        addressService.delete(addressnum);
    }
}

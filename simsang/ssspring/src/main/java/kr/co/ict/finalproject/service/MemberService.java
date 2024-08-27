package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.MemberDAO;
import kr.co.ict.finalproject.vo.MemberVO;

@Service
public class MemberService {
    // 개발자 : 이승희
    @Autowired
    private MemberDAO memberDAO;

    public void add(MemberVO vo) {
        memberDAO.memberAdd(vo);
    }

    public List<MemberVO> list() {
        return memberDAO.memberlist();
    }

    public MemberVO detail(int num) {
        return memberDAO.memberDetail(num);
    }

    public void delete(int num) {
        memberDAO.memberDelete(num);
    }

    public void update(MemberVO member) {
        memberDAO.memberUpdate(member);
    }

    public int idCheck(String id) {
        return memberDAO.idCheck(id);
    }

    public MemberVO findid(String name, String email) {
        return memberDAO.findid(name, email);
    }

    public MemberVO getMemberById(String id) {
        return memberDAO.getMemberById(id);
    }
}
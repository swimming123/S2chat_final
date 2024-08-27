package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ict.finalproject.vo.MemberVO;

@Mapper
public interface MemberDAO {
    // 개발자 : 이승희
    public void memberAdd(MemberVO vo);

    public List<MemberVO> memberlist();

    public MemberVO memberDetail(int num);

    public void memberDelete(int num);

    void memberUpdate(MemberVO member);

    int idCheck(String id);

    int emailCheck(String email);

    int nameEmailCheck(@Param("name") String name, @Param("email") String email);

    int idEmailCheck(@Param("id") String id, @Param("email") String email);

    int checkidpwd(@Param("id") String id, @Param("pwd") String pwd);

    MemberVO findid(@Param("name") String name, @Param("email") String email);

    void pwdUpdate(@Param("id") String name, @Param("email") String email, @Param("authCode") String authCode);

    MemberVO getMemberById(String id);
}
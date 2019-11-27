package sec01.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		MemberDAO dao = new MemberDAO(); //SQL문으로 조회할 MemberDAO 객체를 생성합니다.
		List list = dao.listMembers(); 	 // listMembers()메서드로 회원 정보를 조회합니다.
		
		out.print("<html><body>");
		out.print("<table border=1 align='center'><tr align='center' bgcolor='lightgreen'>");
		out.print("<td>아이디</td><td>비밀번호</td><td>이름</td><td>이메일</td><td>가입일</td></tr>");
		
		for (int i =0; i <list.size(); i++) {
			MemberVO vo = (MemberVO) list.get(i); // 조회한 회원 정보를 for문과 <tr> 태그를 이용해 리스트로 출력합니다.
			String id = vo.getId();
			String pwd = vo.getPwd();
			String name = vo.getName();
			String email = vo.getEmail();
			Date joinDate = vo.getJoinDate();
			out.print("<tr><td>"+ id + "</td><td>"+ pwd + "</td><td>" + name + "</td><td>" + email + "</td><td>" + joinDate + "</td></tr>");
		}
		out.print("</body></html>");
		
		
	}
}

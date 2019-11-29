package sec02.ex02;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member3")
public class MemberServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doHandle(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		PrintWriter out = response.getWriter();
		String command = request.getParameter("command"); // command값을 받아옵니다.

		if (command != null && command.equals("addMember")) // 회원 가입창에서 전송된 command가 addMember이면 전송된 값들을 받아옵니다.
		{
			String _id = request.getParameter("id");
			String _pwd = request.getParameter("pwd");
			String _name = request.getParameter("name");
			String _email = request.getParameter("email");
			MemberVO vo = new MemberVO();
			vo.setId(_id);
			vo.setPwd(_pwd);
			vo.setName(_name);
			vo.setEmail(_email);
			dao.addMember(vo);

		} else if (command != null && command.contentEquals("delMember")) {
			String id = request.getParameter("id");
			dao.delMember(id);
		}

		List list = dao.listMembers();
		out.print("<html><body>");
		out.print("<table border=1><tr align='center' bgcolor='lightgreen'>");
		out.print("<td>아이디</td><td>비밀번호</td><td>이름</td><td>이메일</td><td>가입일</td><td>삭제</td></tr>");

		for (int i = 0; i < list.size(); i++) {
			MemberVO membervo = (MemberVO) list.get(i);
			String id = membervo.getId();
			String pwd = membervo.getPwd();
			String name = membervo.getName();
			String email = membervo.getEmail();
			Date joinDate = membervo.getJoinDate();

			out.print("<tr><td>" + id + "</td><td>" + pwd + "</td><td>" + name + "</td><td>" + email + "</td><td>"
					+ joinDate + "</td><td>" + "<a href='/pro07/member3?command=delMember&id=" + id
					+ "'>삭제</a></td></tr>");
		}
		out.print("</table></body></html>");
		out.print("<a href='/pro07/MemberForm.html'>새 회원 가입하기</a>");
	}
}

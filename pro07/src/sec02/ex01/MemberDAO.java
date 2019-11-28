package sec02.ex01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
//	private static final String driver = "oracle.jdbc.driver.OracleDriver";
//	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	private static final String user = "scott";
//	private static final String pwd = "scott";
	
	private Connection con;
	private DataSource dataFactory;
	private PreparedStatement pstmt;
	
//	public void connDB() {
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, user, pwd);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
	public MemberDAO() {
		try { Context ctx = new InitialContext();
		Context envContext = (Context) ctx.lookup("java:/comp/env");  //JNDI에 접근하기 위해 기본 경로를 지정합니다.
		dataFactory = (DataSource) envContext.lookup("jdbc/oracle"); // 톰캣 context.xml에 설정한 name 값인 jdbc/oracle을 이용해 톰캣이 미리 연결한 DataSource를 받아옵니다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List listMembers() {
		
		List list = new ArrayList();
		try {
			// connDB();
			con = dataFactory.getConnection(); //DataSource를 이용해 데이터베이스에 연결합니다.
			String query = "select * from t_member";
			pstmt = con.prepareStatement(query); // prepareStatement() 메서드에 SQL문을 전달해 PrepareStatement 객체를 생성합니다.
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO vo = new MemberVO();
				
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

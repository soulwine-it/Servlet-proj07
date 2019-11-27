package sec01.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberDAO {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String user = "scott";
	private static final String pwd = "scott";
	private Connection con;
	private Statement stmt;

	public List listMembers() {
		List list = new ArrayList();
		try {
			connDB(); // 네 가지의 정보로 데이터베이스에 연결합니다.
			String query = "select * from t_member";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);  // SQL문으로 회원 정보를 조회합니다.
			System.out.println(rs);
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				System.out.println("안뇽");
				
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				
				list.add(vo);
				
				
			}
			rs.close();
			stmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list; // 조회한 레코드의 개수만큼 MemberVO 객체를 저장한 ArrayList를 반환합니다.
	}
	
	
	private void connDB() {
		try {
			Class.forName(driver);
			System.out.println("Oracle 드라이버 로딩 성공");
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("Connection 생성 성공");
			stmt = con.createStatement();
			System.out.println("Statement 생성 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

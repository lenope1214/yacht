package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import 야추_서버.방;
import 야추_서버.유저;

public class OracleDB {
//	Connection conn;
//	static PreparedStatement pstm;
//	static ResultSet rs;
	static String sql;
	DBConnection DBconn;

	public OracleDB() {
		DBconn = DBConnection.getInstance();
//		conn = DBconn.getConnection();
//		pstm = null;
//		rs = null;
	}

	public boolean 회원가입(String id, String pw, String name) throws SQLException {
		Connection conn = null;
		try {
			conn = DBconn.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement pstm = null;
		ResultSet rs = null;
		sql = "insert into yat_user values(?, ?, ?)";
		System.out.println("id : " + id + "," + "pw : " + pw + "," + "name : " + name);
		// id,pw,name
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, pw);
			pstm.setString(3, name);

			rs = pstm.executeQuery();

			if (rs.next()) {
//				System.out.println("회원가입이 완료되었습니다.");
				return true;
			} else {
//				System.out.println("회원가입에 실패했습니다. 다시 시도해 주세요.");
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("오류 : " + e.getCause());
		}finally{
			if(rs!=null)rs.close();
			if(pstm!=null)pstm.close();
			if(conn!=null)conn.close();
		}

		

		return false;

	}

	public String 로그인(String id, String pw) throws SQLException {
		// TODO Auto-generated method stub
		if (checkId(id)) {
			if (checkPw(pw)) {
				System.out.println("들어온 유저가 맞음.");
				Connection conn = null;
				try {
					conn = DBconn.getConnection();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PreparedStatement pstm = null;
				ResultSet rs = null;
				sql = "select user_name from yat_user where user_id = ?";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, id);
					rs = pstm.executeQuery();
					if (rs.next()) {
						return rs.getString(1);
					} else {
						return null;
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("오류 : " + e.getCause());
				}finally{
					if(rs!=null)rs.close();
					if(pstm!=null)pstm.close();
					if(conn!=null)conn.close();
				}

		
				return null;

			} else {
				System.out.println("비밀번호 틀림!");
			}
			return null;
		} else {
			System.out.println("아이디 없음!");
			return null;
		}
	}

	public boolean checkId(String id) throws SQLException { // 해당 아이디가 있는지 없는지 확인
		Connection conn = null;
		try {
			conn = DBconn.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement pstm = null;
		ResultSet rs = null;
		sql = "select user_id from yat_user";
//		System.out.println();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			while (rs.next()) {
				String nowId = rs.getString(1);
				if (nowId.equals(id)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null)rs.close();
			if(pstm!=null)pstm.close();
			if(conn!=null)conn.close();
		}

		return false;
	}

	public boolean checkPw(String pw) throws SQLException { // 해당 비밀번호가 있는지 없는지 확인
		Connection conn = null;
		try {
			conn = DBconn.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement pstm = null;
		ResultSet rs = null;
		sql = "select user_pw from yat_user";

		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			while (rs.next()) {
				String nowPw = rs.getString(1);
				if (nowPw.equals(pw)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null)rs.close();
			if(pstm!=null)pstm.close();
			if(conn!=null)conn.close();
		}

		return false;
	}


	public void 방생성(방 room) throws SQLException {
		Connection conn = null;
		try {
			conn = DBconn.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "insert into yat_room values(?, ?, ?)";
		// 1: 방장 유저 소켓
		// 2: 일반 유저 소켓
		// 3: 방제목
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, room.get유저들().get(0).getSocket().getPort());
			pstm.setString(2, room.get방장이름());
			pstm.setString(3, room.get제목());
			
			rs = pstm.executeQuery();

			if (rs.next()) {
				System.out.println("DB > 방생성 > 성공");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DB > 방생성 > 실패. 오류 발생!");
		}finally{
			if(rs!=null)rs.close();
			if(pstm!=null)pstm.close();
			if(conn!=null)conn.close();
		}
	}

	public boolean 방삭제(int port) throws SQLException {
		Connection conn = null;
		try {
			conn = DBconn.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "delete from yat_room where u1_socket= ?";
		// 1: 방장 유저 소켓
		// 2: 일반 유저 소켓
		// 3: 방제목
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, port);
			
			rs = pstm.executeQuery();
			if (rs.next()) {
				System.out.println("DB > 방삭제 > 성공");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null)rs.close();
			if(pstm!=null)pstm.close();
			if(conn!=null)conn.close();
		}
		return false;
	}

	public ArrayList<String> 방목록가져오기() throws SQLException {
		Connection conn = null;
		try {
			conn = DBconn.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select u1_socket, title from yat_room";
		// 1: 방장 유저 소켓
		// 2: 일반 유저 소켓
		// 3: 방제목
		
		ArrayList<String> 방목록  = new ArrayList<String>();
		try {
			pstm = conn.prepareStatement(sql);
			
			rs = pstm.executeQuery();
			while(rs.next()) { // 방이 있을때만 true겠죠? ㅎ-ㅎ
				// u1 socket
				// u2 socket
				// title
				String 소켓제목 = "";
				소켓제목 += rs.getInt(1);
				소켓제목 += rs.getString(2);
				방목록.add(소켓제목);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			if(rs!=null)rs.close();
			if(pstm!=null)pstm.close();
			if(conn!=null)conn.close();
		}
		return 방목록;
	}

	/*
	 * void 메서드명(){ Connection conn = null; try { conn = DBconn.getConnection(); }
	 * catch (Exception e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); } PreparedStatement pstm = null; ResultSet rs = null;
	 * String sql=""; }
	 */
}
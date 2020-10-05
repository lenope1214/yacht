package 야추_서버;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
	static ArrayList<GameUser> userList = new ArrayList<GameUser>();
	public static ArrayList<PrintWriter> m_OutputList;

	public static void main(String[] args) {
		System.out.println("게임 서버가 활성화 됐습니다.");
		try {
			ServerSocket s_socket = new ServerSocket(8888); // 포트번호
			m_OutputList = new ArrayList<PrintWriter>();

			while (true) {
				// 연결동안 계속 돌면서 데이터 확인

				Socket c_socket = s_socket.accept();
				GameUser c_thread = new GameUser(c_socket);
				c_thread.setSocket(c_socket);

				userList.add(c_thread);
				m_OutputList.add(new PrintWriter(c_socket.getOutputStream()));

				c_thread.start();
			}
		} catch (IOException e) {
			System.out.println("오류");
			e.printStackTrace();
		}

		System.out.println("게임 서버가 비활성화 됐습니다.");
	}

}

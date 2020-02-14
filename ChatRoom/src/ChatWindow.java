import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow {

	JFrame f;
	JTextArea ta;
	JTextField tf;
	JComboBox cb;
	public static int total;
	public ChatWindow(String name)
	{
		f = new JFrame();
		f.setSize(600, 400);
		f.setTitle("蓝桥聊天室" + " - " + name + "     当前在线人数:" + ++total);
		f.setLocation(300, 200);
		ta = new JTextArea();//显示聊天消息区域
		JScrollPane sp = new JScrollPane(ta);
		ta.setEditable(false);
		tf = new JTextField();//输入消息框
		cb = new JComboBox();//下拉框
		cb.addItem("All");
		JButton jb = new JButton("私聊窗口");
		JPanel pl = new JPanel(new BorderLayout());
		pl.add(cb);
		pl.add(jb, BorderLayout.WEST);
		JPanel p = new JPanel(new BorderLayout());
		p.add(pl, BorderLayout.WEST);
		p.add(tf);
		f.getContentPane().add(p, BorderLayout.SOUTH);
		f.getContentPane().add(sp);
		f.setVisible(true);//让窗口显示出来
		
		showAllUser(name);
	}
	public void showAllUser(String name)
	{
		/*
		 * 加载驱动
		 * 创建连接
		 */
		String url="jdbc:oracle:thin:@39.108.161.127:1521:orcl";
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url, "liwei","lw1234");
			
			String sql="SELECT * FROM tb_users";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				String username=rs.getString(1);
				String ip=rs.getString(2);
				int port=rs.getInt(3);
				if(!name.equals(username))
				{
					ta.append(username+"正在聊天室\n");
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
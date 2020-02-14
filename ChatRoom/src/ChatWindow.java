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
		f.setTitle("����������" + " - " + name + "     ��ǰ��������:" + ++total);
		f.setLocation(300, 200);
		ta = new JTextArea();//��ʾ������Ϣ����
		JScrollPane sp = new JScrollPane(ta);
		ta.setEditable(false);
		tf = new JTextField();//������Ϣ��
		cb = new JComboBox();//������
		cb.addItem("All");
		JButton jb = new JButton("˽�Ĵ���");
		JPanel pl = new JPanel(new BorderLayout());
		pl.add(cb);
		pl.add(jb, BorderLayout.WEST);
		JPanel p = new JPanel(new BorderLayout());
		p.add(pl, BorderLayout.WEST);
		p.add(tf);
		f.getContentPane().add(p, BorderLayout.SOUTH);
		f.getContentPane().add(sp);
		f.setVisible(true);//�ô�����ʾ����
		
		showAllUser(name);
	}
	public void showAllUser(String name)
	{
		/*
		 * ��������
		 * ��������
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
					ta.append(username+"����������\n");
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
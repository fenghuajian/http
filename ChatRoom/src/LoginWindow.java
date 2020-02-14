import javax.swing.*;

import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

//�˽⼴��
public class LoginWindow implements ActionListener{
	JTextField accountField;//��Ա����
	JFrame jframe;
	public LoginWindow()
	{
		jframe = new JFrame("��¼����");// �����ӣ���Χǽ�壩
		jframe.setSize(400, 300);

		JPanel jpanel = new JPanel();// ����//��װ����
		jpanel.setLayout(null);

		JButton loginButton = new JButton("��¼");// ���//�����ϰ�װ����
		loginButton.setBounds(130, 200, 100, 30);
		loginButton.addActionListener(this);

		JLabel accountLabel = new JLabel("�ʺţ�");
		accountLabel.setBounds(50, 70, 100, 30);

		accountField = new JTextField("");
		accountField.setBounds(100, 70, 150, 30);

		jpanel.add(loginButton);
		jpanel.add(accountLabel);
		jpanel.add(accountField);

		jframe.setContentPane(jpanel);
		jframe.setResizable(false);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);// �ɼ�
	}
	public static void main(String args[]) {
		new LoginWindow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * �߼������û�����ip��port���ӵ����ݿ⣩
		 * 1���������ݿ�
		 */
		Connection conn=null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
		    String ip=addr.getHostAddress();
		    
			String url="jdbc:oracle:thin:@39.108.161.127:1521:orcl";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url, "liwei", "lw1234");
			conn.setAutoCommit(false);
			
			String sql="SELECT * FROM tb_users WHERE username=?";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, accountField.getText());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				sql="UPDATE tb_users SET ip=? WHERE username=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, ip);
				pstmt.setString(2,accountField.getText());
				pstmt.executeUpdate();
				pstmt.close();
				conn.commit();
			}
			else
			{
				sql="INSERT INTO tb_users VALUES(?,?,?)";
				pstmt.close();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1,accountField.getText());
				pstmt.setString(2, ip);
				pstmt.setInt(3,8888);
				pstmt.executeUpdate();//ִ��SQL
				conn.commit();//�ύ����
			}
			
			ChatWindow cw=new ChatWindow(accountField.getText());
			jframe.setVisible(false);
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		finally
		{
			//�ر����ݿ�����
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
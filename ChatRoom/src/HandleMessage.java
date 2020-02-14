import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * ��һְ��ԭ��
 * @author liwei
 *
 */
public class HandleMessage {

	/**
	 * ������Ϣ
	 */
	public void receiveMessage(){
		try
		{
			byte buff[]=new byte[1024];
			DatagramPacket p=new DatagramPacket(buff,buff.length);//�洢���ջ򷢵�����
			DatagramSocket ds=new DatagramSocket(8888);
			ds.receive(p);
			String message=new String(buff);
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void sendMessage(String message,String ip,int port)
	{
		try
		{
			byte buff[]=message.getBytes();
			DatagramPacket p=new DatagramPacket(buff,buff.length);//�洢���ջ򷢵�����
			p.setAddress(InetAddress.getLocalHost());
			p.setPort(port);
			DatagramSocket ds=new DatagramSocket();
			ds.send(p);
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
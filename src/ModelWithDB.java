import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

class ModelWithDB{
	
	private static final String URL = "jdbc:mysql://localhost:3306/memo";
	private static final String ID = "root";
	private static final String PW = "1004";
	
	Connection con = null;
	
	public ModelWithDB(){
		
		
		
		//�����Ǹ� ���ڷ� �ٲ��ذ� url(�ּ�), �ּҴ� Ư�� ��ǻ�͸� ã�� ������.
		//Ư�� ��ǻ���� Ư�� ���α׷��� ã�ư��� ���ؼ�... Ư�� ���α׷��� �Ҵ�Ǵ� ���� ������ Port�̴�. 1���� 60000���� ���� ����, 2000���� ���� �̹� ǥ������ ���ǰ� �ִ�. ������α׷��� ���� ���� ����, 
		//MySQL�� �⺻ ��Ʈ�� 3306�̴�. ������ ������ + ��Ʈ 
		
		//ǥ�� �������� http://������ �Ǵ� url�� ���� ��Ʈ�� �� http�� ǥ���̶� ��Ʈ�� �Էµ��� �ʾ��� ���� �ڵ����� �ο���, http�� ��Ʈ�� 80
		//Ư�� ���α׷��� Access �ϱ� ���� �ּ�ü��� �������� �̸� + ������ (�ּ�) + ��Ʈ�� �־�� ��
		//�����ͺ��̽��� �����ϱ� ���ؼ��� �� ������ ��ġ�� ��
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //����̹� ���� �ε�
		
	}

	
	public Memo create(Memo memo, ArrayList<Memo> list){
		
		 //jdbc:mysql�� ��������, //localhost�� ���� �ּ�, 3306�� ��Ʈ, memo�� �����ͺ��̽� �̸�
		//DriverManager.getConnection("�����ͺ��̽��� �ּ�", "����� ���̵�", "��й�ȣ");
		// 1. �����ͺ��̽� ����
		try(Connection con = DriverManager.getConnection(URL, ID, PW)){
			// 2. ������ ����
			// 2.1 ���� ����
			String query = " insert into memo(name,content,datetime) values(?,?,?)"; //��ĭ ��°� ����.
			// 2.2 ������ ���� ������ ���·� ������ش�.
			PreparedStatement pstmt = con.prepareStatement(query); //����ǥ�� ���� �����⸦ ��ٸ�
			// 2.3 ����ǥ�� ���� ����
			pstmt.setString(1, memo.name);
			pstmt.setString(2, memo.content);
			pstmt.setDate(3, new Date(System.currentTimeMillis())); //�ð����� ������ ó���ϴ� �ʿ��� ������ �Ѱ��ش�.
			// 2.4 ������ �����ϸ� �ȴ�.
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println("�ȵǳ׿�");
			e.printStackTrace();
		}

		// 3. 
		
		return memo;
		
	}
	
	public void numUpdate(ArrayList<Memo> list){
		for(Memo memo : list){
			memo.no = list.indexOf(memo)+1;
		}
	}
	
	public void delete(Scanner sc, ArrayList<Memo> list){
		//view.println("�� ��ȣ�� �Է��ϼ���");
		
		String tempNo = sc.nextLine();
		int no = Integer.parseInt(tempNo);
		
		for(Memo memo : list){ //�ϳ��� ���� �� memo���� ArrayList�� �ִ� ��ü�� �ּҰ� ����.
			if(memo.no == no){
				list.remove(memo);
				break;
			}
		}	
		numUpdate(list);
	}
	
	public Memo update(int indexNo, ArrayList<Memo> list){
		
		for(Memo memo : list){	
			if(memo.no == indexNo){		
				return memo;		
			}
		}		
		return null;
	}	
	
	public ArrayList<Memo> getList(){
		ArrayList<Memo> list = new ArrayList<>();
		
		// 1. �����ͺ��̽� ����
		try(Connection con = DriverManager.getConnection(URL, ID, PW)){
			// 2. ������ ����
			// 2.1 ���� ����
			String query = "select * from memo"; //��ĭ ��°� ����.
			// 2.2 ������ ������ ���� ������ ���·� ������ش�.
			Statement stmt = con.createStatement();
			PreparedStatement pstmt = con.prepareStatement(query); //����ǥ�� ���� �����⸦ ��ٸ�
			
			// 2.4 ������ �����ϸ� �ȴ�.
			ResultSet set = stmt.executeQuery(query); //select�� ������� �������� �� ����
			// ������� �ݺ��ϸ鼭 �ϳ��� ���� �� �ִ�.
			while(set.next()){
				Memo memo = new Memo();
				//�޸� Ŭ������ ���� �������ְ�
				memo.no = set.getInt("no");
				memo.name = set.getString("name");
				memo.content = set.getString("content");
				memo.datetime = set.getString("datetime");
				list.add(memo);
				
			};
			
		}catch(Exception e){
			System.out.println("�ȵǳ׿�");
			e.printStackTrace();
		}

		// 3. 
		
		return list;
		
	}
}
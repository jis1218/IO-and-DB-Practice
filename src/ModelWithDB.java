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
		
		
		
		//아이피를 문자로 바꿔준게 url(주소), 주소는 특정 컴퓨터를 찾기 위함임.
		//특정 컴퓨터의 특정 프로그램을 찾아가기 위해선... 특정 프로그램에 할당되는 내부 번지가 Port이다. 1부터 60000번대 까지 있음, 2000번대 밑은 이미 표준으로 사용되고 있다. 상용프로그램은 쓰고 있지 않음, 
		//MySQL의 기본 포트는 3306이다. 소켓은 아이피 + 포트 
		
		//표준 프로토콜 http://아이피 또는 url이 들어가고 포트가 들어감 http는 표준이라 포트가 입력되지 않았을 때는 자동으로 부여됨, http의 포트는 80
		//특정 프로그램에 Access 하기 위한 주소체계는 프로토콜 이름 + 아이피 (주소) + 포트가 있어야 함
		//데이터베이스에 접속하기 위해서는 이 과정을 거치게 됨
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //드라이버 동적 로드
		
	}

	
	public Memo create(Memo memo, ArrayList<Memo> list){
		
		 //jdbc:mysql은 프로토콜, //localhost는 나의 주소, 3306은 포트, memo는 데이터베이스 이름
		//DriverManager.getConnection("데이터베이스의 주소", "사용자 아이디", "비밀번호");
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(URL, ID, PW)){
			// 2. 쿼리를 실행
			// 2.1 쿼리 생성
			String query = " insert into memo(name,content,datetime) values(?,?,?)"; //한칸 띄는게 좋다.
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다.
			PreparedStatement pstmt = con.prepareStatement(query); //물음표로 값이 들어오기를 기다림
			// 2.3 물음표의 값을 세팅
			pstmt.setString(1, memo.name);
			pstmt.setString(2, memo.content);
			pstmt.setDate(3, new Date(System.currentTimeMillis())); //시간값은 로직을 처리하는 쪽에서 서버로 넘겨준다.
			// 2.4 쿼리를 실행하면 된다.
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println("안되네요");
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
		//view.println("글 번호를 입력하세요");
		
		String tempNo = sc.nextLine();
		int no = Integer.parseInt(tempNo);
		
		for(Memo memo : list){ //하나씩 꺼낼 때 memo에는 ArrayList에 있는 객체의 주소가 들어간다.
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
		
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(URL, ID, PW)){
			// 2. 쿼리를 실행
			// 2.1 쿼리 생성
			String query = "select * from memo"; //한칸 띄는게 좋다.
			// 2.2 앞으로 쿼리를 실행 가능한 상태로 만들어준다.
			Statement stmt = con.createStatement();
			PreparedStatement pstmt = con.prepareStatement(query); //물음표로 값이 들어오기를 기다림
			
			// 2.4 쿼리를 실행하면 된다.
			ResultSet set = stmt.executeQuery(query); //select한 결과값을 돌려받을 수 있음
			// 결과셋을 반복하면서 하나씩 꺼낼 수 있다.
			while(set.next()){
				Memo memo = new Memo();
				//메모 클래스의 값을 셋팅해주고
				memo.no = set.getInt("no");
				memo.name = set.getString("name");
				memo.content = set.getString("content");
				memo.datetime = set.getString("datetime");
				list.add(memo);
				
			};
			
		}catch(Exception e){
			System.out.println("안되네요");
			e.printStackTrace();
		}

		// 3. 
		
		return list;
		
	}
}
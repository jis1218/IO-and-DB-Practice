import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

class View{
	
	private final String DB_DIR = "D:/java-neon/eclipse/java";
	private final String DB_FILE = "memo.txt";
	private File database = null;
	
	// 전체 메모를 저장하는 저장소
	ArrayList<Memo> list = new ArrayList<>();
	
	public View(){
		//생성자 : new를 하면 생성자가 호출된다. 만약 생성자를 작성하지 않고 호출하면 작성하지 않았지만 생략된 생성자가 호출된다.
		
		File dir = new File(DB_DIR);
		if(!dir.exists()){
			dir.mkdirs(); //디렉토리 형태로 생성을 해주는데 dirs와 dir의 차이는 dirs는 경로상에 디렉토리가 없으면 자동 생성을 해준다. dir은 경로가 미리 만들어져 있어야 한다.
		}
		
		File file = new File(DB_DIR + File.separator + DB_FILE); //OS마다 file separator가 다름 '/' 아니면 '\', 따라서 OS에 맞는 separator를 쓰기 위해 File.separator를 사용
		
		if(!file.exists()){ //IO(input, output)Exception이 일어날 경우에 다음과 같은 예외처리
			try{
			file.createNewFile();
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
		database = file;		
	}	
	//Stream은 연속된 데이터의 모양, 단방향으로 데이터를 읽거나 쓸수 있음, 닫지 않으면 계속 읽을 수 있지만 시스템의 자원을 계속 쓸 수 있으므로 닫아줘야 한다.
	private final String COLUMN_SEP = "::";
	
	public Memo create(Scanner sc, Memo memo){
			
		//글번호
				
		println("이름을 입력하세요 : ");
		memo.name = sc.nextLine();
		println("내용을 입력하세요 : ");
		memo.content = sc.nextLine();
		
		//날짜
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		 long time = System.currentTimeMillis();
		
		 memo.datetime = sdf.format(time);
		
		FileOutputStream fos = null;
		

		
		try {
			//1. 쓰는 Stream을 연다.
			fos = new FileOutputStream(database, true); //우리가 만든 데이터베이스를 던져주면 된다.
			//2. Stream을 중간처리... (텍스트의 encoding을 변경하는 작업)
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			//3. 버퍼처리
			//그냥 스트림을 쓰면 하나에 하나씩만 처리, 읽고 가져오고 해야 하는데 그러면 쓰는 리소스가 많아짐, 버퍼는 여러개를 한번에 읽을 수 있음
			BufferedWriter bw = new BufferedWriter(osw);
			
			//저장할 내용을 구분자로 분리하여 한줄의 문자열로 바꾼다.
			String row = memo.no + COLUMN_SEP + memo.name + COLUMN_SEP + memo.content + COLUMN_SEP + memo.datetime + "\n";
			
			bw.append(row); //실제는 최상위 객체인 Exception 하나로 처리한다.
			bw.close(); //또는 bw.flush();   버퍼에 있는 내용을 흘려보내줘야 하는데 버퍼에 어느정도 양이 차야 흘려보내준다. 다 차기전에 flush 해주는 역할이 flush()이고 close를 해주면 다 흘려보내준다.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//혹시라도 중간에 error가 나서 fos.close()를 못해줄 수 있기 때문에 finally에 한다.
			if(fos!=null){
				try {
					fos.close(); //close도 try catch 문으로 해줘야 한다.
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
//		자바 7부터 이렇게 바뀌었다. try문에 이렇게 써줌으로써 finally를 안써도 됨
//		
//		try(FileOutputStream fos = new FileOutputStream(database, true)) {
//			//1. 쓰는 Stream을 연다.
//			fos = new FileOutputStream(database, true); //우리가 만든 데이터베이스를 던져주면 된다.
//			//2. Stream을 중간처리... (텍스트의 encoding을 변경하는 작업)
//			OutputStreamWriter osw = new OutputStreamWriter(fos);
//			//3. 버퍼처리
//			//그냥 스트림을 쓰면 하나에 하나씩만 처리, 읽고 가져오고 해야 하는데 그러면 쓰는 리소스가 많아짐, 버퍼는 여러개를 한번에 읽을 수 있음
//			BufferedWriter bw = new BufferedWriter(osw);
//			
//			//저장할 내용을 구분자로 분리하여 한줄의 문자열로 바꾼다.
//			String row = memo.no + COLUMN_SEP + memo.name + COLUMN_SEP + memo.content + COLUMN_SEP + memo.datetime + "\n";
//			
//			bw.append("저장할 내용을 담으면 된다."); //실제는 최상위 객체인 Exception 하나로 처리한다.
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
		//model.create(memo);
		
		return memo;
		
	}
	
	public void showList(ArrayList<Memo> list) {
		// ArrayList 저장소를 반복문을 돌면서 한줄씩 출력
		
		for(Memo memo : list){
			print("  No : " + memo.no);
			print("  Author : " + memo.name);
			println("  Content : " + memo.content);
			
		}
		
	}
	
	public void read(Scanner sc, ArrayList<Memo> list){
		System.out.println("글 번호를 입력하시오 : ");
		
		String tempNo = sc.nextLine();
		int no = Integer.parseInt(tempNo);
		
		for(Memo memo : list){
			if(memo.no == no){ 
				println(" No : " + memo.no);
				println(" Author : " + memo.name);
				println(" Content : " + memo.content);
				
				//숫자로 입력받은 날짜를 실제 날짜로 변경해주는 것
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				String formattedDate = sdf.format(memo.datetime);
				
				println(formattedDate);
			}
		}
	}
	
	public int findNoForUpdate(Scanner sc, ArrayList<Memo> list){
		System.out.println("글 번호를 입력하시오 : ");
		String tempNo = sc.nextLine();
		
		return Integer.parseInt(tempNo);
	}
	
	public void screenModifed(Scanner sc, Memo memo){
		println("새로운 이름을 입력하시오 : ");
		memo.name = sc.nextLine();
		println("새로운 내용을 입력하시오 : ");
		memo.content = sc.nextLine();	
	}
	
	/*public void update(Scanner sc, ArrayList<Memo> list){
	
		System.out.println("글 번호를 입력하시오 : ");
		
		String tempNo = sc.nextLine();
		int no = Integer.parseInt(tempNo);
		//int index=0;
		for(Memo memo : list){
			if(memo.no == no){ 
				view.println(" No : " + memo.no);
				view.println(" Author : " + memo.name);
				view.println(" Content : " + memo.content);
				//index = list.indexOf(memo);
				
				view.println("이름을 입력하세요 : ");
				memo.name = sc.nextLine();
				view.println("내용을 입력하세요 : ");
				memo.content = sc.nextLine();
				
				//숫자로 입력받은 날짜를 실제 날짜로 변경해주는 것
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				String formattedDate = sdf.format(memo.datetime);
				
				view.println(formattedDate);
			}
		}
		
		//이미 만들어져 있는 메모 객체에 넣는 것이기 때문에 set을 할 필요가 없음
//		System.out.println(index);
//		memo1.no = no;
//		System.out.println("바꿀 이름을 입력하시오 : ");
//		memo1.name = sc.nextLine();
//		System.out.println("바꿀 내용을 입력하세요 : ");
//		memo1.content = sc.nextLine();
//		list.set(index, memo1);
		
		//numUpdate();
	}*/
	
	public void print(String string){
		System.out.print(string);
	}
	
	public void println(String string){
		System.out.println(string);
	}
	
	public ArrayList<Memo> getList(){
		
		//데이터가 중복으로 쌓이지 않도록 저장소를 지워주는 작업이 필요한 경우가 있다.
		list.clear();
		// 1. 읽는 stream을 연다.
		try(FileInputStream fis = new FileInputStream(database)){
			// 2. 실제 파일  encoding을 바꿔주는 래퍼 클래스를 사용
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); //Character의 encoding set, 텍스트 파일은 글자를 쓰면 encoding set에 따라 저장되는 형식이 다름 ex)아스키의 경우 특정 숫자로 저장
			// 3. 버퍼처리
			BufferedReader br = new BufferedReader(isr);
			
			String row = br.readLine();
			
			while((br.readLine())!=null){
				String tempRow[] = row.split(COLUMN_SEP);
				//1::fds::fdsaf::fasdgd
				//tempRow[0] = 1
				//tempRow[1] = fds
				//tempRow[2] = fdsaf
				//tempRow[3] = fasdgd
				
				Memo memo = new Memo();
				memo.no = Integer.parseInt(tempRow[0]);
				memo.name = tempRow[1];
				memo.content = tempRow[2];
				memo.datetime = tempRow[3];
				
				list.add(memo);
				
			}
			
		}catch(Exception e){
			
		}
		
		return list;
	}
	
}
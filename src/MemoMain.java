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

/*
 * 자바 입출력
 * Fileoutputstream으로 작업을 했는데 fileoutputstream은 byte 영역에 있고 bufferedwrite는 char 영역에 있음, 그 중간 역할을 해주는게 outputstreamwriter
 * 
 */

/*
 * 출력은 System.out.print로 다 한다.
 * 입력은 Scanner를 이용한다.
 * 
 * 
 * Controller의 create 함수의 return 값을 Memo로 함으로써 함수의 역할을 입출력으로 제한함, 그리고 View에도 create를 만든다.
 * 
 * 콘트롤러에서 이렇게 한다.
 * memo = View.create()
 * Model.create(memo)
 * 
 * 이게 MVC를 따르는 코딩임, MVC 패턴, MVC 아키텍쳐라 불림, MVC
 * 
 * 
 */
public class MemoMain {
	
	ModelWithDB model = new ModelWithDB();
	View view = new View();
	
	public static void main(String[] args) {
		//입력을 받아서 처리하는 도구
		Scanner sc = new Scanner(System.in);
		MemoMain memoMain = new MemoMain(); 
		
		ArrayList<Memo> list = new ArrayList<>();
		
		//명령어를 입력받아서 후속처리
		//c- create : 데이터 입력 모드로 전환
		//r- read   : 데이터 읽기 모드로 전환
		//u- update : 데이터 수정모드
		//d- delete : 데이터 삭제모드
		String command = "";
		
		while(!command.equals("exit")){
			
			Memo memo = new Memo();
			
			memoMain.view.println("--------명령어를 입력하세요----------");
			memoMain.view.println("c : 쓰기, r : 읽기, u : 수정, d : 삭제, ㅣ: 목록");
			memoMain.view.println("exit : 종료");
			memoMain.view.println("-------------------------------");
			
			command = sc.nextLine(); // 키보드 입력 중에 Enter키가 입력될 때까지 대기
			
			switch(command){
			case "c":
				memoMain.view.create(sc, memoMain.model.create(memo, list));
				break;
				
				//메모 데이터에 대한 조작이 필요할 경우 모두 컨트롤러에서 작업한다.
				
			case "r":
				memoMain.view.read(sc, list);
				break;
			case "u":
				//memoMain.model.update(sc, list);
				
				
				int a = memoMain.view.findNoForUpdate(sc, list);
				memoMain.view.screenModifed(sc, memoMain.model.update(a, list));
				
				break;
				
			case "d":
				memoMain.model.delete(sc, list);
				
				break;
				
			case "l":
				ArrayList<Memo> list2 = memoMain.model.getList();
				memoMain.view.showList(list2);
				break;
			}
		}
	}
	//키보드 입력을 받는 함수 
	//1. 이름 입력
	//2. 내용 입력
}

//데이터를 저장하는 저장소를 관리하는 객체


//화면 입출력을 관리하는 객체


//개별 글 한개 한개를 저장하는 클래스


class Memo{
	int no;
	String name;
	String content;
	String datetime;
}
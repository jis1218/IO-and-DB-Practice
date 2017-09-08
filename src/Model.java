import java.util.ArrayList;
import java.util.Scanner;

class Model{
	
	

	View view = new View();
	//int lastIndex = 0;
	
	public Memo create(Memo memo, ArrayList<Memo> list){
		
		memo.no = list.size()+1;
		
		//memo 객체의 내용을 파일에 쓴다.
		

		list.add(memo);
		
		return memo;
		
	}
	
	public void numUpdate(ArrayList<Memo> list){
		for(Memo memo : list){
			memo.no = list.indexOf(memo)+1;
		}
	}
	
	public void delete(Scanner sc, ArrayList<Memo> list){
		view.println("글 번호를 입력하세요");
		
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
}
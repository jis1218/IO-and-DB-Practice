import java.util.ArrayList;
import java.util.Scanner;

class Model{
	
	

	View view = new View();
	//int lastIndex = 0;
	
	public Memo create(Memo memo, ArrayList<Memo> list){
		
		memo.no = list.size()+1;
		
		//memo ��ü�� ������ ���Ͽ� ����.
		

		list.add(memo);
		
		return memo;
		
	}
	
	public void numUpdate(ArrayList<Memo> list){
		for(Memo memo : list){
			memo.no = list.indexOf(memo)+1;
		}
	}
	
	public void delete(Scanner sc, ArrayList<Memo> list){
		view.println("�� ��ȣ�� �Է��ϼ���");
		
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
}
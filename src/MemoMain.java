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
 * �ڹ� �����
 * Fileoutputstream���� �۾��� �ߴµ� fileoutputstream�� byte ������ �ְ� bufferedwrite�� char ������ ����, �� �߰� ������ ���ִ°� outputstreamwriter
 * 
 */

/*
 * ����� System.out.print�� �� �Ѵ�.
 * �Է��� Scanner�� �̿��Ѵ�.
 * 
 * 
 * Controller�� create �Լ��� return ���� Memo�� �����ν� �Լ��� ������ ��������� ������, �׸��� View���� create�� �����.
 * 
 * ��Ʈ�ѷ����� �̷��� �Ѵ�.
 * memo = View.create()
 * Model.create(memo)
 * 
 * �̰� MVC�� ������ �ڵ���, MVC ����, MVC ��Ű���Ķ� �Ҹ�, MVC
 * 
 * 
 */
public class MemoMain {
	
	ModelWithDB model = new ModelWithDB();
	View view = new View();
	
	public static void main(String[] args) {
		//�Է��� �޾Ƽ� ó���ϴ� ����
		Scanner sc = new Scanner(System.in);
		MemoMain memoMain = new MemoMain(); 
		
		ArrayList<Memo> list = new ArrayList<>();
		
		//��ɾ �Է¹޾Ƽ� �ļ�ó��
		//c- create : ������ �Է� ���� ��ȯ
		//r- read   : ������ �б� ���� ��ȯ
		//u- update : ������ �������
		//d- delete : ������ �������
		String command = "";
		
		while(!command.equals("exit")){
			
			Memo memo = new Memo();
			
			memoMain.view.println("--------��ɾ �Է��ϼ���----------");
			memoMain.view.println("c : ����, r : �б�, u : ����, d : ����, ��: ���");
			memoMain.view.println("exit : ����");
			memoMain.view.println("-------------------------------");
			
			command = sc.nextLine(); // Ű���� �Է� �߿� EnterŰ�� �Էµ� ������ ���
			
			switch(command){
			case "c":
				memoMain.view.create(sc, memoMain.model.create(memo, list));
				break;
				
				//�޸� �����Ϳ� ���� ������ �ʿ��� ��� ��� ��Ʈ�ѷ����� �۾��Ѵ�.
				
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
	//Ű���� �Է��� �޴� �Լ� 
	//1. �̸� �Է�
	//2. ���� �Է�
}

//�����͸� �����ϴ� ����Ҹ� �����ϴ� ��ü


//ȭ�� ������� �����ϴ� ��ü


//���� �� �Ѱ� �Ѱ��� �����ϴ� Ŭ����


class Memo{
	int no;
	String name;
	String content;
	String datetime;
}
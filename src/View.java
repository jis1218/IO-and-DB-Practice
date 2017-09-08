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
	
	// ��ü �޸� �����ϴ� �����
	ArrayList<Memo> list = new ArrayList<>();
	
	public View(){
		//������ : new�� �ϸ� �����ڰ� ȣ��ȴ�. ���� �����ڸ� �ۼ����� �ʰ� ȣ���ϸ� �ۼ����� �ʾ����� ������ �����ڰ� ȣ��ȴ�.
		
		File dir = new File(DB_DIR);
		if(!dir.exists()){
			dir.mkdirs(); //���丮 ���·� ������ ���ִµ� dirs�� dir�� ���̴� dirs�� ��λ� ���丮�� ������ �ڵ� ������ ���ش�. dir�� ��ΰ� �̸� ������� �־�� �Ѵ�.
		}
		
		File file = new File(DB_DIR + File.separator + DB_FILE); //OS���� file separator�� �ٸ� '/' �ƴϸ� '\', ���� OS�� �´� separator�� ���� ���� File.separator�� ���
		
		if(!file.exists()){ //IO(input, output)Exception�� �Ͼ ��쿡 ������ ���� ����ó��
			try{
			file.createNewFile();
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
		database = file;		
	}	
	//Stream�� ���ӵ� �������� ���, �ܹ������� �����͸� �аų� ���� ����, ���� ������ ��� ���� �� ������ �ý����� �ڿ��� ��� �� �� �����Ƿ� �ݾ���� �Ѵ�.
	private final String COLUMN_SEP = "::";
	
	public Memo create(Scanner sc, Memo memo){
			
		//�۹�ȣ
				
		println("�̸��� �Է��ϼ��� : ");
		memo.name = sc.nextLine();
		println("������ �Է��ϼ��� : ");
		memo.content = sc.nextLine();
		
		//��¥
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		 long time = System.currentTimeMillis();
		
		 memo.datetime = sdf.format(time);
		
		FileOutputStream fos = null;
		

		
		try {
			//1. ���� Stream�� ����.
			fos = new FileOutputStream(database, true); //�츮�� ���� �����ͺ��̽��� �����ָ� �ȴ�.
			//2. Stream�� �߰�ó��... (�ؽ�Ʈ�� encoding�� �����ϴ� �۾�)
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			//3. ����ó��
			//�׳� ��Ʈ���� ���� �ϳ��� �ϳ����� ó��, �а� �������� �ؾ� �ϴµ� �׷��� ���� ���ҽ��� ������, ���۴� �������� �ѹ��� ���� �� ����
			BufferedWriter bw = new BufferedWriter(osw);
			
			//������ ������ �����ڷ� �и��Ͽ� ������ ���ڿ��� �ٲ۴�.
			String row = memo.no + COLUMN_SEP + memo.name + COLUMN_SEP + memo.content + COLUMN_SEP + memo.datetime + "\n";
			
			bw.append(row); //������ �ֻ��� ��ü�� Exception �ϳ��� ó���Ѵ�.
			bw.close(); //�Ǵ� bw.flush();   ���ۿ� �ִ� ������ ���������� �ϴµ� ���ۿ� ������� ���� ���� ��������ش�. �� �������� flush ���ִ� ������ flush()�̰� close�� ���ָ� �� ��������ش�.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//Ȥ�ö� �߰��� error�� ���� fos.close()�� ������ �� �ֱ� ������ finally�� �Ѵ�.
			if(fos!=null){
				try {
					fos.close(); //close�� try catch ������ ����� �Ѵ�.
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
//		�ڹ� 7���� �̷��� �ٲ����. try���� �̷��� �������ν� finally�� �Ƚᵵ ��
//		
//		try(FileOutputStream fos = new FileOutputStream(database, true)) {
//			//1. ���� Stream�� ����.
//			fos = new FileOutputStream(database, true); //�츮�� ���� �����ͺ��̽��� �����ָ� �ȴ�.
//			//2. Stream�� �߰�ó��... (�ؽ�Ʈ�� encoding�� �����ϴ� �۾�)
//			OutputStreamWriter osw = new OutputStreamWriter(fos);
//			//3. ����ó��
//			//�׳� ��Ʈ���� ���� �ϳ��� �ϳ����� ó��, �а� �������� �ؾ� �ϴµ� �׷��� ���� ���ҽ��� ������, ���۴� �������� �ѹ��� ���� �� ����
//			BufferedWriter bw = new BufferedWriter(osw);
//			
//			//������ ������ �����ڷ� �и��Ͽ� ������ ���ڿ��� �ٲ۴�.
//			String row = memo.no + COLUMN_SEP + memo.name + COLUMN_SEP + memo.content + COLUMN_SEP + memo.datetime + "\n";
//			
//			bw.append("������ ������ ������ �ȴ�."); //������ �ֻ��� ��ü�� Exception �ϳ��� ó���Ѵ�.
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
		// ArrayList ����Ҹ� �ݺ����� ���鼭 ���پ� ���
		
		for(Memo memo : list){
			print("  No : " + memo.no);
			print("  Author : " + memo.name);
			println("  Content : " + memo.content);
			
		}
		
	}
	
	public void read(Scanner sc, ArrayList<Memo> list){
		System.out.println("�� ��ȣ�� �Է��Ͻÿ� : ");
		
		String tempNo = sc.nextLine();
		int no = Integer.parseInt(tempNo);
		
		for(Memo memo : list){
			if(memo.no == no){ 
				println(" No : " + memo.no);
				println(" Author : " + memo.name);
				println(" Content : " + memo.content);
				
				//���ڷ� �Է¹��� ��¥�� ���� ��¥�� �������ִ� ��
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				String formattedDate = sdf.format(memo.datetime);
				
				println(formattedDate);
			}
		}
	}
	
	public int findNoForUpdate(Scanner sc, ArrayList<Memo> list){
		System.out.println("�� ��ȣ�� �Է��Ͻÿ� : ");
		String tempNo = sc.nextLine();
		
		return Integer.parseInt(tempNo);
	}
	
	public void screenModifed(Scanner sc, Memo memo){
		println("���ο� �̸��� �Է��Ͻÿ� : ");
		memo.name = sc.nextLine();
		println("���ο� ������ �Է��Ͻÿ� : ");
		memo.content = sc.nextLine();	
	}
	
	/*public void update(Scanner sc, ArrayList<Memo> list){
	
		System.out.println("�� ��ȣ�� �Է��Ͻÿ� : ");
		
		String tempNo = sc.nextLine();
		int no = Integer.parseInt(tempNo);
		//int index=0;
		for(Memo memo : list){
			if(memo.no == no){ 
				view.println(" No : " + memo.no);
				view.println(" Author : " + memo.name);
				view.println(" Content : " + memo.content);
				//index = list.indexOf(memo);
				
				view.println("�̸��� �Է��ϼ��� : ");
				memo.name = sc.nextLine();
				view.println("������ �Է��ϼ��� : ");
				memo.content = sc.nextLine();
				
				//���ڷ� �Է¹��� ��¥�� ���� ��¥�� �������ִ� ��
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				String formattedDate = sdf.format(memo.datetime);
				
				view.println(formattedDate);
			}
		}
		
		//�̹� ������� �ִ� �޸� ��ü�� �ִ� ���̱� ������ set�� �� �ʿ䰡 ����
//		System.out.println(index);
//		memo1.no = no;
//		System.out.println("�ٲ� �̸��� �Է��Ͻÿ� : ");
//		memo1.name = sc.nextLine();
//		System.out.println("�ٲ� ������ �Է��ϼ��� : ");
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
		
		//�����Ͱ� �ߺ����� ������ �ʵ��� ����Ҹ� �����ִ� �۾��� �ʿ��� ��찡 �ִ�.
		list.clear();
		// 1. �д� stream�� ����.
		try(FileInputStream fis = new FileInputStream(database)){
			// 2. ���� ����  encoding�� �ٲ��ִ� ���� Ŭ������ ���
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); //Character�� encoding set, �ؽ�Ʈ ������ ���ڸ� ���� encoding set�� ���� ����Ǵ� ������ �ٸ� ex)�ƽ�Ű�� ��� Ư�� ���ڷ� ����
			// 3. ����ó��
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
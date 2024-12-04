import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;
import java.util.Scanner;

public class FTPClient {

	static Scanner sc = new Scanner(System.in);;
	static FTPClient client = new FTPClient();

	public static void disconnectFTP() {
		try {
			System.out.println("\n서버를 종료하겠습니다. ");
			System.out.println("====================");
			client.disconnect(true);
		}
		catch(Exception e) {
			System.out.println("\n종료가 되지 않았습니다.");
			disconnectFTP();
		}
		ftpMenu();

	}

	public static void downloadFTP() {
		try {
			String[] fileList = client.listNames(); 

			for(String file: fileList) {
				System.out.println(file);
			}

			System.out.print("다운받을 파일명을 입력하세요\n>>>");
			String downloadFile =  sc.nextLine(); 

			System.out.print("다운받을 경로와 저장할 파일명을 입력하세요\n>>>");
			String downloadPath =  sc.nextLine(); 

			client.download(downloadFile, new File(downloadPath));

			System.out.println("file download Success\n");

		}
		catch(Exception e){
			System.out.println("file download Fail\n");
		}
		finally {
			ftpAction();	
		}

	}

	public static void uploadFTP() {
		try {
			String[] fileList = client.listNames(); //server에 있는 파일 list 출력 

			for(String file: fileList) {
				System.out.println(file);
			}


			System.out.print("업로드할 파일의 경로를 입력하세요\n>>>");

			File filePath = new File(sc.nextLine());
			client.upload(filePath);
			System.out.println("file upload Success\n");

			ftpAction();

		}
		catch(Exception e){
			System.out.println("file upload Fail\n");
		}
		finally {
			ftpAction();	
		}

	}


	public static void ftpAction() {

		int actionNum;
		try {
			System.out.print("1. Upload File\n"
					+ "2. Download File\n"
					+ "3. Disconnect FTP server\n>>>");
			actionNum = Integer.parseInt(sc.nextLine());

		}
		catch(Exception e){
			System.out.println("다시 입력해주세요.\n");
			ftpAction();
			return;
		}

		if(actionNum==1) {
			uploadFTP();
		}
		else if(actionNum==2) {
			downloadFTP();
		}
		else if(actionNum==3) {
			disconnectFTP();
		}
		else {
			System.out.println("다시 입력해주세요.\n");
			ftpAction();
			return;
		}

	}

	public static void ftpLogin() {

		try {
			System.out.print("\ninput Server ID: ");
			String serverID = sc.nextLine();

			System.out.print("\ninput Server Passwd: ");
			String serverPasswd = sc.nextLine();

			client.login(serverID, serverPasswd);

			System.out.println("Login Success");
		}
		catch(Exception e){
			System.out.println("id 또는 password가 틀렸습니다.");
			ftpLogin();
			return;
		}
		ftpAction();

	}



	public static void ftpConnect() {
		String serverAdd = "";
		int serverPort = 21;

		try {
			System.out.print("\n접속할 서버의 주소를 입력해주세요: ");
			serverAdd = sc.nextLine();

			System.out.print("\n접속할 서버의 포트번호를 입력해주세요: ");
			serverPort = Integer.parseInt(sc.nextLine());
		}
		catch(Exception e) {
			System.out.println("다시 입력해주세요.\n");
			ftpConnect();
			return;
		}


		try {
			client.connect(serverAdd);		
			System.out.println("FTP Server is connected :)");
		}
		catch(Exception e){
			System.out.println("FTP Server is disconnected :(");
			ftpConnect();
		}

		ftpLogin();

	}

	public static void ftpMenu() {
		System.out.println("=== FTP Client Program ===");
		System.out.println("1. Connect FTP server");
		System.out.print("2. Exit Program\n>>>");

		int menuNum = Integer.parseInt(sc.nextLine());
		System.out.println(menuNum);

		if(menuNum==1) {
			ftpConnect();
		}
		else if(menuNum==2) {
			System.out.println("프로그램을 종료하겠습니다.\n");
			System.exit(0);
		}
		else {
			System.out.println("다시 입력해주세요.\n");
			ftpMenu();
			return;
		}

	}



	public static void main(String[] args) {

		ftpMenu();

	}
	

}

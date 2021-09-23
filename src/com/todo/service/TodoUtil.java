package com.todo.service;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[추가하기]\n"
				+ "제목 > ");
		
		title = sc.next().trim();
		sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.println("중복된 제목은 사용할 수 없습니다.");
			sc.close();
			return;
		}
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();

		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가되었습니다.\n");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[삭제하기]\n"
				+ "제목 > ");
		
		String title = sc.next().trim();
		
		int exist=0;
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.\n");
				exist=1;
				break;
			}
		}
		if(exist==0) {
			System.out.println("해당하는 제목이 없습니다.\n");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[수정하기]\n"
				+ "제목 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("해당하는 제목이 없습니다.");
			sc.close();
			return;
		}

		System.out.print("새로운 제목 > ");
		String new_title = sc.next().trim();
		sc.nextLine();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 제목은 사용할 수 없습니다.");
			sc.close();
			return;
		}
		
		System.out.print("새로운 설명 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.\n");
			}
		}
	}

	public static void listAll(TodoList l) {
		System.out.println("\n[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "]\t" + item.getDesc() + " - " + item.getCurrent_date());
		}
		System.out.println();
	}
	
	public static void loadList(TodoList l, String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String str;
			int num=0;
			while((str = reader.readLine()) != null) {
				if(str.equals("")) {break;}
				StringTokenizer st = new StringTokenizer(str, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				TodoItem i = new TodoItem(title, desc, current_date);
				l.addItem(i);
				num++;
			}
			reader.close();
			System.out.println(num + "개의 항목을 불러왔습니다.");
		} catch(IOException e) {
			System.out.println("해당하는 파일이 없습니다.");
		}
		
	}
	
	public static void saveList(TodoList l, String fileName) {
		int num=0;
		try {
			File f = new File(fileName);
			if(!f.exists()) {f.createNewFile();}
			
			FileWriter fw = new FileWriter(f);
			for(TodoItem i : l.getList()) {
				fw.write(i.toSaveString());
				num++;
			}
			fw.close();
		} catch(IOException e) {
			System.out.println("파일에서 오류가 발생했습니다.");
		}
		System.out.println("총 " + num + "개의 파일을 저장했습니다.");
	}
}

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
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[추가하기]\n"
				+ "제목 > ");
		
		title = sc.next().trim();
		sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.println("중복된 제목은 사용할 수 없습니다.\n");
			return;
		}

		System.out.print("카테고리 > ");
		category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("내용 > ");
		desc = sc.nextLine();
		
		System.out.print("마감일자 > ");
		due_date = sc.next().trim();

		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		list.addItem(t);
		System.out.println("추가되었습니다.\n");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[삭제하기]\n"
				+ "번호 > ");
		Integer num = sc.nextInt();
		sc.nextLine();
		if(num<1 || num>l.getNumOf()) {
			System.out.println("해당하는 번호의 아이템이 없습니다.\n");
			return;
		}
		
		TodoItem i = l.get(num);
		System.out.println(num + ". [" + i.getCategory() + "] " + i.getTitle() + " - " + i.getDesc() + " - " + i.getDue_date() + " - " + i.getCurrent_date());
		System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
		String yn = sc.nextLine();
		
		if(yn.equals("y")) {
			l.deleteItem(i);
			System.out.println("삭제되었습니다.\n");
		}
		else if(yn.equals("n")){
			System.out.println("취소했습니다.\n");
		}
		else {
			System.out.println("y/n 중에서 선택하지 않았습니다.\n");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[수정하기]\n"
				+ "번호 > ");
		Integer num = sc.nextInt();
		if(num<1 && num>l.getNumOf()) {
			System.out.println("해당하는 번호의 아이템이 없습니다.\n");
			return;
		}
		
		TodoItem i = l.get(num);
		System.out.println(num + ". [" + i.getCategory() + "] " + i.getTitle() + " - " + i.getDesc() + " - " + i.getDue_date() + " - " + i.getCurrent_date());
		System.out.print("새로운 제목 > ");
		String new_title = sc.next().trim();
		sc.nextLine();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 제목은 사용할 수 없습니다.\n");
			return;
		}
		
		System.out.print("새로운 카테고리 > ");
		String new_category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("새로운 설명 > ");
		String new_description = sc.nextLine();
		
		System.out.print("새로운 마감일자 > ");
		String new_due_date = sc.next().trim();
		
		l.deleteItem(i);
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
		l.addItem(t);
		System.out.println("수정되었습니다.\n");
	}
	
	public static void findItem(TodoList l, String keyword) {
		int count=0;
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(keyword) || item.getDesc().contains(keyword)) {
				System.out.println(	l.indexOf(item)+1 + ". [" + item.getCategory() + "] " 
						+ item.getTitle() + " - " + item.getDesc() + " - "
						+ item.getDue_date() + " - " + item.getCurrent_date());
				count++;
			}
		}
		if(count==0) {
			System.out.println("해당하는 항목을 찾지 못했습니다.\n");
		}
		else {
			System.out.println("총 " + count + "개의 항목을 찾았습니다.");
			System.out.println();
		}
	}
	
	public static void findItemCategory(TodoList l, String keyword) {
		int count=0;
		for(TodoItem item : l.getList()) {
			if(item.getCategory().contains(keyword)) {
				System.out.println(	l.indexOf(item)+1 + ". [" + item.getCategory() + "] " 
						+ item.getTitle() + " - " + item.getDesc() + " - "
						+ item.getDue_date() + " - " + item.getCurrent_date());
				count++;
			}
		}
		if(count==0) {
			System.out.println("해당하는 항목을 찾지 못했습니다.\n");
		}
		else {
			System.out.println("총 " + count + "개의 항목을 찾았습니다.");
			System.out.println();
		}
	}

	public static void listAll(TodoList l) {
		System.out.println("\n[전체 목록, 총 " + l.getNumOf() + "개]");
		for (TodoItem item : l.getList()) {
			System.out.println(	l.indexOf(item)+1 + ". [" + item.getCategory() + "] " 
								+ item.getTitle() + " - " + item.getDesc() + " - "
								+ item.getDue_date() + " - " + item.getCurrent_date());
		}
		System.out.println();
	}
	
	public static void listAllCategory(TodoList l) {
		HashSet<String> cateSet = new HashSet<String>();
		for (TodoItem item : l.getList()) {
			cateSet.add(item.getCategory());
		}
		Iterator<String> iter = cateSet.iterator();
		while(iter.hasNext()) {
			System.out.print(iter.next());
			if(iter.hasNext()) {System.out.print(" / ");}
		}
		System.out.println();
		System.out.println("총 " + cateSet.size() + "개의 카테고리가 등록되어 있습니다.\n");
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
				String category = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				TodoItem i = new TodoItem(title, category, desc, due_date, current_date);
				l.addItem(i);
				num++;
			}
			reader.close();
			System.out.println(num + "개의 항목을 불러왔습니다.");
		} catch(IOException e) {
			System.out.println("해당하는 파일이 없습니다.");
			System.out.println();
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

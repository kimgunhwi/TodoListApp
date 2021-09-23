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
				+ "[�߰��ϱ�]\n"
				+ "���� > ");
		
		title = sc.next().trim();
		sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.println("�ߺ��� ������ ����� �� �����ϴ�.");
			sc.close();
			return;
		}
		
		System.out.print("���� > ");
		desc = sc.nextLine().trim();

		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.\n");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�����ϱ�]\n"
				+ "���� > ");
		
		String title = sc.next().trim();
		
		int exist=0;
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("�����Ǿ����ϴ�.\n");
				exist=1;
				break;
			}
		}
		if(exist==0) {
			System.out.println("�ش��ϴ� ������ �����ϴ�.\n");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�����ϱ�]\n"
				+ "���� > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("�ش��ϴ� ������ �����ϴ�.");
			sc.close();
			return;
		}

		System.out.print("���ο� ���� > ");
		String new_title = sc.next().trim();
		sc.nextLine();
		if (l.isDuplicate(new_title)) {
			System.out.println("�ߺ��� ������ ����� �� �����ϴ�.");
			sc.close();
			return;
		}
		
		System.out.print("���ο� ���� > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.\n");
			}
		}
	}

	public static void listAll(TodoList l) {
		System.out.println("\n[��ü ���]");
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
			System.out.println(num + "���� �׸��� �ҷ��Խ��ϴ�.");
		} catch(IOException e) {
			System.out.println("�ش��ϴ� ������ �����ϴ�.");
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
			System.out.println("���Ͽ��� ������ �߻��߽��ϴ�.");
		}
		System.out.println("�� " + num + "���� ������ �����߽��ϴ�.");
	}
}

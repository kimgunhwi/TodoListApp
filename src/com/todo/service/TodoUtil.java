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
				+ "[�߰��ϱ�]\n"
				+ "���� > ");
		
		title = sc.next().trim();
		sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.println("�ߺ��� ������ ����� �� �����ϴ�.\n");
			return;
		}

		System.out.print("ī�װ� > ");
		category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("���� > ");
		desc = sc.nextLine();
		
		System.out.print("�������� > ");
		due_date = sc.next().trim();

		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.\n");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�����ϱ�]\n"
				+ "��ȣ > ");
		Integer num = sc.nextInt();
		sc.nextLine();
		if(num<1 || num>l.getNumOf()) {
			System.out.println("�ش��ϴ� ��ȣ�� �������� �����ϴ�.\n");
			return;
		}
		
		TodoItem i = l.get(num);
		System.out.println(num + ". [" + i.getCategory() + "] " + i.getTitle() + " - " + i.getDesc() + " - " + i.getDue_date() + " - " + i.getCurrent_date());
		System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
		String yn = sc.nextLine();
		
		if(yn.equals("y")) {
			l.deleteItem(i);
			System.out.println("�����Ǿ����ϴ�.\n");
		}
		else if(yn.equals("n")){
			System.out.println("����߽��ϴ�.\n");
		}
		else {
			System.out.println("y/n �߿��� �������� �ʾҽ��ϴ�.\n");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�����ϱ�]\n"
				+ "��ȣ > ");
		Integer num = sc.nextInt();
		if(num<1 && num>l.getNumOf()) {
			System.out.println("�ش��ϴ� ��ȣ�� �������� �����ϴ�.\n");
			return;
		}
		
		TodoItem i = l.get(num);
		System.out.println(num + ". [" + i.getCategory() + "] " + i.getTitle() + " - " + i.getDesc() + " - " + i.getDue_date() + " - " + i.getCurrent_date());
		System.out.print("���ο� ���� > ");
		String new_title = sc.next().trim();
		sc.nextLine();
		if (l.isDuplicate(new_title)) {
			System.out.println("�ߺ��� ������ ����� �� �����ϴ�.\n");
			return;
		}
		
		System.out.print("���ο� ī�װ� > ");
		String new_category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("���ο� ���� > ");
		String new_description = sc.nextLine();
		
		System.out.print("���ο� �������� > ");
		String new_due_date = sc.next().trim();
		
		l.deleteItem(i);
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
		l.addItem(t);
		System.out.println("�����Ǿ����ϴ�.\n");
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
			System.out.println("�ش��ϴ� �׸��� ã�� ���߽��ϴ�.\n");
		}
		else {
			System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
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
			System.out.println("�ش��ϴ� �׸��� ã�� ���߽��ϴ�.\n");
		}
		else {
			System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
			System.out.println();
		}
	}

	public static void listAll(TodoList l) {
		System.out.println("\n[��ü ���, �� " + l.getNumOf() + "��]");
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
		System.out.println("�� " + cateSet.size() + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n");
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
			System.out.println(num + "���� �׸��� �ҷ��Խ��ϴ�.");
		} catch(IOException e) {
			System.out.println("�ش��ϴ� ������ �����ϴ�.");
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
			System.out.println("���Ͽ��� ������ �߻��߽��ϴ�.");
		}
		System.out.println("�� " + num + "���� ������ �����߽��ϴ�.");
	}
}

package com.todo.dao;

import java.util.*;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public int getNumOf() {
		return list.size();
	}
	
	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());
	}

	public void listAll() {
		System.out.println("\n[��ü ���, �� " + this.getNumOf() + "��]");
		for (TodoItem item : list) {
			System.out.println(	list.indexOf(item)+1 + ". [" + item.getCategory() + "] " 
								+ item.getTitle() + " - " + item.getDesc() + " - "
								+ item.getDue_date() + " - " + item.getCurrent_date());
		}
		System.out.println();
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}

	public TodoItem get(Integer num) {
		for (TodoItem item : list) {
			if (num.equals(list.indexOf(item)+1)) {
				return item;
			}
		}
		return null;
	}
}

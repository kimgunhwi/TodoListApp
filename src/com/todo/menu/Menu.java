package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<TodoList 관리 명령어>");
        System.out.println("add - 추가하기");
        System.out.println("del - 삭제하기");
        System.out.println("edit - 수정하기");
        System.out.println("ls - 확인하기");
        System.out.println("ls_name_asc - 정렬하기(제목순)");
        System.out.println("ls_name_desc - 역정렬하기(제목순)");
        System.out.println("ls_date - 정렬하기(시간순)");
        System.out.println("exit - 나가기");
        System.out.println();
    }
    
    public static void prompt() {
    	System.out.print("명령어 > ");
    }
}

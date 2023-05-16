package com.task;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        TaskManagement taskManagement = new TaskManagement();
        Scanner input = new Scanner(System.in);
        System.out.println("=== Tasks Management ===");

        do{
            System.out.println("1. Add Task type");
            System.out.println("2. Remove Task type");
            System.out.println("3. Display all task types");
            System.out.println("4. Add Task");
            System.out.println("5. Remove Task");
            System.out.println("6. Display all tasks");
            System.out.println("7. Exit program");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Please choose a function: ");
            String sChoice = scanner.nextLine();

            int choice = 0;

            try {
                choice = checkChoice(sChoice);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            switch (choice){
                case 1:
                    System.out.println("--- Add Task Type ---");
                    taskManagement.addType();
                    break;
                case 2: {
                    System.out.println("--- Remove Type ---");
                    taskManagement.removeType();
                }
                break;
                case 3:
                    System.out.println("--- Display All Types ---");
                    System.out.printf("%-10s %-20s %n", "Type ID", "Type Name");
                    taskManagement.displayAllType();
                    break;
                case 4:
                    System.out.println("--- Add task ---");
                    taskManagement.addTask();
                    break;
                case 5:
                    taskManagement.removeTask();
                    break;
                case 6:
                    System.out.println("--- Display all task ---");
                    System.out.printf("%-10s %-20s %-20s %-20s %-10s %-20s %-20s %n", "Task ID", "Task name", "Task Type", "Date", "Time", "Assignee", "Reviewer");
                    taskManagement.displayAllTask();
                    break;
                case 7:
                    System.out.println("Program ended!");
                    return;
            }
        }
        while (true);
    }

    public static int checkChoice(String sChoice) throws Exception{
        if(sChoice.equals("")){
            throw new Exception(">> Err: Choose can not be empty!");
        }
        int choice;
        try{
            choice = Integer.parseInt(sChoice);
        }
        catch (Exception e){
            throw new Exception(">> Err: Please enter a number!");
        }
        if (choice > 0 && choice < 8){
            return choice;
        }else {
            throw new Exception(">> Err: Please enter a number form 1 to 7!");
        }
    }
}

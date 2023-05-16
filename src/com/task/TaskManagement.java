package com.task;

import com.util.DBUtil;

import java.sql.*;
import java.util.Scanner;

public class TaskManagement {
    Scanner input = new Scanner(System.in);
    TaskType type = null;
    Tasks task = null;

    public void addType(){
        try {
            TaskType type = new TaskType();
            type.inputType();
            if(type.getTypeName() !=null){
                Connection connection = DBUtil.getConnection();
                CallableStatement callableStatement = connection.prepareCall("{call addType(?)}");
                callableStatement.setString(1, type.getTypeName());

                if(callableStatement.executeUpdate() > 0){
                    System.out.println("Type added!");
                }

                callableStatement.close();
                connection.close();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void removeType(){
        try {
            System.out.print("Enter Type's ID to remove: ");
            String sFind = input.nextLine();
            int findID = checkFindID(sFind);
            type = findTypeByID(findID);
            if (type != null){
                System.out.println("Type found!");
                System.out.printf("%-10s %-20s %n", "Type ID", "Type Name");
                type.displayType();
                Connection connection = DBUtil.getConnection();
                CallableStatement callableStatement = connection.prepareCall("{call removeType(?)}");
                callableStatement.setInt(1, type.getTypeID());

                if (callableStatement.executeUpdate() > 0){
                    System.out.println("Remove success!");
                }

                callableStatement.close();
                connection.close();
            }else {
                System.out.println("Type not found!");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void displayAllType(){
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareCall("{call getAllTypes}");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                TaskType type = new TaskType(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
                type.displayType();
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void addTask(){
        try {
            Tasks inTask = new Tasks();
            inTask.inputTask();
            try {
                task = checkNullInput(inTask);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
            if (task != null){
                Connection connection = DBUtil.getConnection();
                CallableStatement callableStatement = connection.prepareCall("{call addTask(?, ?, ?, ?, ?, ?)}");
                callableStatement.setString(1, task.getTaskName());
                callableStatement.setInt(2, task.getTypeID());
                callableStatement.setString(3, task.getDate());
                callableStatement.setFloat(4, task.getTime());
                callableStatement.setString(5, task.getAssignee());
                callableStatement.setString(6, task.getReviewer());

                if(callableStatement.executeUpdate() > 0){
                    System.out.println("Task added!");
                }
                callableStatement.close();
                connection.close();
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void removeTask(){
        try {
            System.out.print("Enter Task's ID to remove: ");
            String sFind = input.nextLine();
            int findID = checkFindID(sFind);
            task = findTaskByID(findID);
            if (task != null){
                System.out.println("Task found!");
                System.out.printf("%-10s %-20s %n", "Task ID", "Task Name");
                task.displayTask();
                Connection connection = DBUtil.getConnection();
                CallableStatement callableStatement = connection.prepareCall("{call removeTask(?)}");
                callableStatement.setInt(1, task.getTaskID());

                if (callableStatement.executeUpdate() > 0){
                    System.out.println("Remove success!");
                }

                callableStatement.close();
                connection.close();
            }else {
                System.out.println("Task not found!");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void displayAllTask(){
        try {
            Connection connection = DBUtil.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call getAllTask}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Tasks tasks = new Tasks(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getFloat(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                tasks.display();
            }
            resultSet.close();
            callableStatement.close();
            connection.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public TaskType findTypeByID(int findID){
        try {
            Connection connection = DBUtil.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call findType(?)}");
            callableStatement.setInt(1, findID);

            ResultSet resultSet = callableStatement.executeQuery();
            if(resultSet.next()){
                TaskType resultType = new TaskType(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
                return resultType;
            }

            resultSet.close();
            callableStatement.close();
            connection.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public Tasks findTaskByID(int findID){
        try {
            Connection connection = DBUtil.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call findTask(?)}");
            callableStatement.setInt(1, findID);

            ResultSet resultSet = callableStatement.executeQuery();
            if(resultSet.next()){
                Tasks resultTask = new Tasks(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getFloat(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                return resultTask;
            }

            resultSet.close();
            callableStatement.close();
            connection.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public int checkFindID(String sFind) throws Exception{
        if (sFind.isEmpty()){
            throw new Exception("Input can not be empty!");
        }
        int findID = 0;
        try {
            findID = Integer.parseInt(sFind);
        }catch (Exception ex){
            throw new Exception("Find ID must be a number!");
        }
        if (findID > 0){
            return findID;
        }else {
            throw new Exception("Find ID must be positive number!");
        }
    }

    public Tasks checkNullInput(Tasks tasks)throws Exception{
        if(
                tasks.getTaskName() == null ||
                tasks.getTypeID() == 0 ||
                tasks.getDate() == null ||
                tasks.getTime() == 0 ||
                tasks.getAssignee() == null ||
                tasks.getReviewer() == null
        ){
            throw new Exception("Any input field has wrong! \n >> Can not add task!");
        }else {
            return tasks;
        }
    }
}

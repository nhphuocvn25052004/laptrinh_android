package com.example.todolist;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TaskDatabaseHelper dbHelper;  // Biến để thao tác với cơ sở dữ liệu
    private TaskAdapter adapter;          // Biến quản lý adapter cho RecyclerView
    private ArrayList<Task> taskList;     // Danh sách công việc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Gán giao diện từ XML

        // Khởi tạo DatabaseHelper và lấy danh sách công việc từ cơ sở dữ liệu
        dbHelper = new TaskDatabaseHelper(this);
        taskList = dbHelper.getAllTasks();

        // Thiết lập RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Thiết lập Layout cho danh sách

        // Khởi tạo Adapter và kết nối với RecyclerView
        adapter = new TaskAdapter(taskList, position -> {
            dbHelper.deleteTask(taskList.get(position).getId());  // Xóa công việc trong database
            taskList.remove(position);                            // Xóa công việc trong danh sách
            adapter.notifyItemRemoved(position);                  // Cập nhật giao diện
        });

        recyclerView.setAdapter(adapter);

        // Bắt sự kiện khi người dùng nhấn nút "Thêm Công Việc"
        findViewById(R.id.btnAddTask).setOnClickListener(v -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Công Việc");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);  // Chỉ cho phép nhập văn bản
        builder.setView(input);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String task = input.getText().toString();
            if (!task.isEmpty()) {
                dbHelper.addTask(task);                        // Thêm công việc vào database
                taskList.clear();
                taskList.addAll(dbHelper.getAllTasks());       // Cập nhật lại danh sách từ database
                adapter.notifyDataSetChanged();                // Cập nhật giao diện
            } else {
                Toast.makeText(this, "Vui lòng nhập công việc", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}

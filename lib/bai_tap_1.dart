import 'package:flutter/material.dart';

// 1. Tạo class Student
class Student {
  String name;
  String? email;
  String? phone;

  Student({required this.name, this.email, this.phone});
}

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: StudentListScreen(),
    );
  }
}

class StudentListScreen extends StatelessWidget {
  const StudentListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    // 2. Danh sách 5 sinh viên
    List<Student> students = [
      Student(name: "Nguyễn Văn An", email: "an@sv.edu.vn", phone: "0901 234 567"),
      Student(name: "Trần Thị Bình", email: null, phone: "0912 888 999"),
      Student(name: "Lê Minh Chi", email: "chi@sv.edu.vn", phone: null),
      Student(name: "Phạm Quốc Dũng", email: null, phone: null),
      Student(name: "Võ Thanh Em", email: "em@sv.edu.vn", phone: "0933 111 222"),
    ];

    // 5. Đếm số sinh viên thiếu email bằng vòng for
    int missingEmailCount = 0;
    for (var s in students) {
      if (s.email == null) {
        missingEmailCount++;
      }
    }

    // 3. Dùng vòng for để tạo danh sách thẻ Widget
    List<Widget> cardList = [];
    for (var s in students) {
      cardList.add(
        Container(
          width: double.infinity,
          margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 6),
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(12),
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                s.name,
                style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 4),
              // 4. Xử lý null dùng toán tử ??
              Text(
                "Email: ${s.email ?? "Chưa cập nhật"}",
                style: TextStyle(
                  color: s.email == null ? Colors.red[400] : Colors.grey[700],
                  fontSize: 13,
                ),
              ),
              const SizedBox(height: 2),
              Text(
                "SĐT: ${s.phone ?? "Chưa cập nhật"}",
                style: TextStyle(
                  color: s.phone == null ? Colors.red[400] : Colors.grey[700],
                  fontSize: 13,
                ),
              ),
            ],
          ),
        ),
      );
    }

    return Scaffold(
      backgroundColor: const Color(0xFFF1F5F9),
      appBar: AppBar(
        title: const Text("Danh sách sinh viên"),
        backgroundColor: const Color(0xFF1E3A8A),
        foregroundColor: Colors.white,
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView(
              padding: const EdgeInsets.only(top: 10),
              children: cardList,
            ),
          ),
          Container(
            padding: const EdgeInsets.all(16),
            color: Colors.white,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  "Tổng: ${students.length} sinh viên",
                  style: const TextStyle(fontWeight: FontWeight.bold),
                ),
                Text(
                  "Thiếu email: $missingEmailCount",
                  style: const TextStyle(
                    color: Colors.red,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
import 'package:flutter/material.dart';

// 1. Class Student
class Student {
  String name;
  double? score;

  Student({required this.name, this.score});
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
      home: GradeBoardScreen(),
    );
  }
}

class GradeBoardScreen extends StatelessWidget {
  const GradeBoardScreen({super.key});

  String getRank(double score) {
    if (score >= 8.0) return "Giỏi";
    if (score >= 6.5) return "Khá";
    if (score >= 5.0) return "Trung bình";
    return "Yếu";
  }

  @override
  Widget build(BuildContext context) {
    // Dữ liệu theo đề bài
    List<Student> students = [
      Student(name: "Nguyễn Văn An", score: 9.0),
      Student(name: "Trần Thị Bình", score: 7.2),
      Student(name: "Lê Minh Chi", score: null),
      Student(name: "Phạm Quốc Dũng", score: 4.5),
      Student(name: "Võ Thanh Em", score: 5.8),
    ];

    // 3. Dùng for + if để tính toán các chỉ số
    double totalScore = 0;
    int gradedCount = 0;
    int gioicount = 0;
    int noScoreCount = 0;
    double maxScore = 0;

    for (var s in students) {
      if (s.score == null) {
        noScoreCount++;
      } else {
        totalScore += s.score!;
        gradedCount++;

        if (s.score! >= 8.0) gioicount++;
        if (s.score! > maxScore) maxScore = s.score!;
      }
    }

    double avgScore = gradedCount > 0 ? totalScore / gradedCount : 0;

    return Scaffold(
      backgroundColor: const Color(0xFFF1F5F9),
      appBar: AppBar(
        title: const Text("Bảng điểm lớp"),
        backgroundColor: const Color(0xFF1E3A8A),
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            // Thẻ xanh tổng quan ở trên
            Container(
              margin: const EdgeInsets.all(16),
              padding: const EdgeInsets.all(20),
              decoration: BoxDecoration(
                color: const Color(0xFF1E3A8A),
                borderRadius: BorderRadius.circular(16),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    "Điểm trung bình lớp",
                    style: TextStyle(color: Colors.white70, fontSize: 12),
                  ),
                  const SizedBox(height: 4),
                  Text(
                    avgScore.toStringAsFixed(2),
                    style: const TextStyle(
                      color: Colors.white,
                      fontSize: 32,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 16),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      _buildSummaryItem("Giỏi", "$gioicount"),
                      _buildSummaryItem("Chưa có điểm", "$noScoreCount"),
                      _buildSummaryItem("Cao nhất", "$maxScore"),
                    ],
                  )
                ],
              ),
            ),

            // Danh sách sinh viên
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Align(
                alignment: Alignment.centerLeft,
                child: Text(
                  "DANH SÁCH • ${students.length} SINH VIÊN",
                  style: const TextStyle(
                    fontSize: 12,
                    fontWeight: FontWeight.bold,
                    color: Colors.grey,
                  ),
                ),
              ),
            ),
            const SizedBox(height: 8),

            // 2. Dùng for hiển thị thông tin từng sinh viên
            for (var s in students)
              Container(
                margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          s.name,
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 15,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          s.score == null ? "Chưa có điểm" : getRank(s.score!),
                          style: TextStyle(
                            color: s.score == null ? Colors.grey : Colors.green[600],
                            fontSize: 12,
                          ),
                        ),
                      ],
                    ),
                    Text(
                      s.score == null ? "—" : "${s.score}",
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ],
                ),
              ),
            const SizedBox(height: 20),
          ],
        ),
      ),
    );
  }

  Widget _buildSummaryItem(String label, String value) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label, style: const TextStyle(color: Colors.white70, fontSize: 11)),
        const SizedBox(height: 2),
        Text(
          value,
          style: const TextStyle(
            color: Colors.white,
            fontWeight: FontWeight.bold,
            fontSize: 15,
          ),
        ),
      ],
    );
  }
}
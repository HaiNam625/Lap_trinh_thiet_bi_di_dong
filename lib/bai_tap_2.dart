import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: AcademicRankScreen(),
    );
  }
}

class AcademicRankScreen extends StatelessWidget {
  const AcademicRankScreen({super.key});

  // 2. Hàm xếp loại học lực dựa trên điểm số
  String getRank(double score) {
    if (score < 0 || score > 10) return "Không hợp lệ";
    if (score >= 8.0) return "Giỏi";
    if (score >= 6.5) return "Khá";
    if (score >= 5.0) return "Trung bình";
    return "Yếu";
  }

  // 3. Hàm chọn màu chữ / màu nền theo xếp loại
  Color getRankColor(String rank) {
    if (rank == "Giỏi") return Colors.green;
    if (rank == "Khá") return Colors.blue;
    if (rank == "Trung bình") return Colors.orange;
    if (rank == "Yếu") return Colors.red;
    return Colors.grey;
  }

  @override
  Widget build(BuildContext context) {
    // 1. Khai báo điểm chính = 8.5
    double mainScore = 8.5;
    String mainRank = getRank(mainScore);

    // 4. Danh sách các điểm cần kiểm tra thêm: 9, 7, 5.5, 3, 11
    List<double> testScores = [9, 7, 5.5, 3, 11];

    return Scaffold(
      backgroundColor: const Color(0xFFF1F5F9),
      appBar: AppBar(
        title: const Text("Xếp loại học lực"),
        backgroundColor: const Color(0xFF1E3A8A),
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Khung hiển thị điểm trung bình chính (8.5)
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(20),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(16),
              ),
              child: Column(
                children: [
                  const Text(
                    "Điểm trung bình",
                    style: TextStyle(color: Colors.grey, fontSize: 13),
                  ),
                  const SizedBox(height: 8),
                  Text(
                    "$mainScore",
                    style: const TextStyle(
                      fontSize: 32,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 12),
                  Container(
                    width: double.infinity,
                    padding: const EdgeInsets.symmetric(vertical: 10),
                    alignment: Alignment.center,
                    decoration: BoxDecoration(
                      color: getRankColor(mainRank).withOpacity(0.1),
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: Text(
                      mainRank,
                      style: TextStyle(
                        color: getRankColor(mainRank),
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    ),
                  )
                ],
              ),
            ),
            const SizedBox(height: 24),

            const Text(
              "KIỂM TRA THÊM",
              style: TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.bold,
                color: Colors.grey,
              ),
            ),
            const SizedBox(height: 8),

            // Danh sách các điểm kiểm tra thêm
            Container(
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(12),
              ),
              child: Column(
                children: [
                  for (var score in testScores) ...[
                    Padding(
                      padding: const EdgeInsets.symmetric(
                        horizontal: 16,
                        vertical: 12,
                      ),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text("Điểm $score"),
                          Container(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 12,
                              vertical: 4,
                            ),
                            decoration: BoxDecoration(
                              color: getRankColor(getRank(score)).withOpacity(0.1),
                              borderRadius: BorderRadius.circular(6),
                            ),
                            child: Text(
                              getRank(score),
                              style: TextStyle(
                                color: getRankColor(getRank(score)),
                                fontWeight: FontWeight.bold,
                                fontSize: 12,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    if (score != testScores.last) const Divider(height: 1),
                  ]
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
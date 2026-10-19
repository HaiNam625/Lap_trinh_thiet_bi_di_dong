
## 1. MÔ TẢ NGẮN GỌN BÀI TẬP
Tổng hợp các bài tập thực hành giao diện và logic bằng **Flutter (Dart)**[cite: 23, 24, 25, 26]:
* **Bài 1:** Hiển thị danh sách sinh viên, xử lý dữ liệu bị thiếu (`nullable`) bằng toán tử `??` và thống kê sinh viên thiếu email[cite: 25].
* **Bài 2:** Phân loại học lực từ điểm số sử dụng cấu trúc điều kiện `if / else`[cite: 24].
* **Bài 3:** Bảng điểm lớp tổng hợp kết hợp `for` + `if` + `nullable`, tính điểm trung bình và tìm các chỉ số lớp[cite: 23].
* **Bài thực hành 3:** Máy tính 2 số cơ bản (`+`, `-`, `*`, `/`) tương tác bằng `TextField` và quản lý trạng thái (`state`)[cite: 26].

## 2. MỤC TIÊU DẠT ĐƯỢC
* Nắm vững các cú pháp cơ bản trong Dart: Vòng lập `for`, câu lệnh điều kiện `if / else`, kiểm tra `null` (`Null Safety`)[cite: 23, 24, 25].
* Sử dụng thành thạo các Widget giao diện cơ bản: `Scaffold`, `AppBar`, `Column`, `Row`, `Container`, `Text`, `SizedBox`, `TextField`, `ElevatedButton`[cite: 23, 24, 25, 26].
* Hiểu cách xử lý sự kiện người dùng và quản lý trạng thái đơn giản với `StatefulWidget` và `setState()`[cite: 26].

## 3. GIẢI THÍCH NGẮN GỌN CÁC HÀM CHÍNH

* **`student.email ?? "Chưa cập nhật"` (Bài 1):** Kiểm tra thuộc tính `email`, nếu là `null` thì tự động thay bằng chuỗi "Chưa cập nhật"[cite: 25].
* **`String getRank(double score)` (Bài 2 & 3):** Nhận vào điểm số, dùng `if / else` kiểm tra phạm vi điểm để trả về xếp loại tương ứng ("Giỏi", "Khá", "Trung bình", "Yếu", "Không hợp lệ")[cite: 23, 24].
* **`Color getRankColor(String rank)` (Bài 2):** Trả về màu sắc hiển thị phù hợp với từng xếp loại học lực[cite: 24].
* **`void _calculate(String operator)` (Thực hành 3):** Lấy dữ liệu từ 2 `TextField` qua `TextEditingController`, ép kiểu sang số bằng `double.tryParse()`, thực hiện phép tính và gọi `setState()` để cập nhật kết quả lên màn hình[cite: 26].




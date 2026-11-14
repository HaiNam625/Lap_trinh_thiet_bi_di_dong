# UTH SMARTTASKS & FIREBASE - LẬP TRÌNH DI ĐỘNG
**Họ và tên:** [Điền tên của bạn vào đây]
**MSSV:** [Điền MSSV của bạn vào đây]
**Lớp:** [Điền tên lớp của bạn vào đây]

## Mục tiêu bài tập
- **Thực hành 1:** Hiểu và áp dụng kiến trúc **MVVM**, xây dựng chức năng thông báo (`SmartTasksNotificationScreen`) với ViewModel, tự động chuyển về giao diện `emptyView` sau 10 giây không có thông báo.
- **Bài tập về nhà Tuần 04:** Tiếp tục phát triển ứng dụng `UTH SmartTasks`, dựng màn hình Thêm mới công việc theo mô hình **MVVM** với giao diện UI/UX trực quan, đầy đủ các trường (Tên, Ghi chú, Nhóm, Ngày, Giờ, Nhắc nhở, Mức ưu tiên).
- **Firebase:** Tích hợp Firebase Authentication và Realtime Database.

## Kết quả đạt được & Các tính năng
1. **Thực hành 1 (Thông báo theo mô hình MVVM):**
    - Quản lý danh sách thông báo thông qua `SmartTasksViewModel`.
    - Sau 10 giây, danh sách tự động về rỗng (`emptyView`) hiển thị thông báo chưa có thông báo mới, kèm nút `+ Thêm việc cần làm` và `Cài đặt nhắc việc`.
    - Hỗ trợ nút "Tải lại mẫu" để kiểm tra lại hiệu ứng đếm ngược 10 giây bất cứ lúc nào.
2. **BTVN Tuần 04 (Màn hình Thêm mới theo MVVM):**
    - Thiết kế giao diện "Việc mới" chuẩn theo dự án mẫu (Hủy, Tên công việc, Ghi chú, Nhóm học tập/cuộc sống, Ngày, Giờ, Nhắc tôi switch, Mức ưu tiên thấp/vừa/cao).
    - Lưu công việc trực tiếp vào danh sách quản lý qua ViewModel.
3. **Ứng dụng UTH SmartTasks hoàn chỉnh:**
    - Giao diện Trang chủ, Danh sách công việc, Chi tiết công việc, Thêm việc mới, Thông báo và Cài đặt.
4. **Firebase (Bài 1 & Bài 2):**
    - Đăng nhập/Đăng ký với Firebase Authentication.
    - Đồng bộ thời gian thực với Firebase Realtime Database.

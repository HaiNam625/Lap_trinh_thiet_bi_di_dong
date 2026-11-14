# MÔ TẢ MÃ NGUỒN (SOURCE CODE)

Dự án được xây dựng bằng Android Studio, sử dụng ngôn ngữ Kotlin và thư viện Jetpack Compose theo kiến trúc **MVVM**.

## Cấu trúc các file chính:
1. **`MainActivity.kt`**:
    - Quản lý điều hướng (Navigation) giữa Menu chính, Thực hành 1 (Thông báo MVVM), BTVN Tuần 04 (Thêm mới MVVM), UTH SmartTasks, Bài 1 (Auth) và Bài 2 (Realtime DB).
2. **`SmartTasksScreen.kt`**:
    - Chứa kiến trúc **MVVM** (`SmartTasksViewModel`) cho ứng dụng `UTH SmartTasks`.
    - **`ThucHanh1Screen` & `SmartTasksNotificationScreen`**: Thực hiện chức năng thông báo, tự động xóa danh sách sau 10 giây để hiển thị `emptyView`.
    - **`AddTaskScreen` & `Btvn4Screen`**: Màn hình Thêm mới công việc theo mô hình MVVM (Hủy, Lưu, Nhóm, Ngày, Giờ, Nhắc tôi, Mức ưu tiên).
    - Quản lý các màn hình Trang chủ (`SmartTasksHomeScreen`), Danh sách (`SmartTasksListScreen`) và Chi tiết công việc (`TaskDetailScreen`).
3. **`baitap1.kt`**:
    - Chứa logic xử lý **Firebase Authentication** (Đăng nhập / Đăng ký / Hồ sơ).
4. **`baitap2.kt`**:
    - Chứa logic xử lý **Firebase Realtime Database**.

---

## HÌNH ẢNH MINH HỌA (OUTPUT)

### 1. Thực hành 1: Thông báo theo MVVM (Sau 10s về rỗng -> EmptyView)
- *(Chèn ảnh chụp màn hình Thông báo và EmptyView vào đây)*

### 2. BTVN Tuần 04: Màn hình Thêm mới công việc (MVVM)
- *(Chèn ảnh chụp màn hình màn hình Việc mới vào đây)*

### 3. UTH SmartTasks & Firebase
- *(Chèn ảnh chụp màn hình Trang chủ và Firebase vào đây)*

# MÔ TẢ MÃ NGUỒN (SOURCE CODE)

Dự án được xây dựng bằng Android Studio, sử dụng ngôn ngữ Kotlin và thư viện Jetpack Compose.

## Cấu trúc các file chính:
1. **`MainActivity.kt`**:
    - Chứa hàm `onCreate` khởi tạo giao diện chính.
    - Quản lý việc điều hướng (Navigation) giữa Menu chính, Bài tập 1 và Bài tập 2 thông qua biến trạng thái `currentScreen`.
2. **`baitap1.kt`**:
    - Chứa logic xử lý **Firebase Authentication**.
    - Các hàm chính: `Baitap1Screen` (quản lý trạng thái đăng nhập), `LoginScreen` (giao diện đăng nhập), `HomeScreen` (giao diện trang chủ todo), `ProfileScreen` (giao diện cá nhân).
    - Sử dụng `FirebaseAuth.getInstance()` để đăng nhập, đăng ký và đăng xuất.
3. **`baitap2.kt`**:
    - Chứa logic xử lý **Firebase Realtime Database**.
    - Hàm chính: `Baitap2Screen`.
    - Sử dụng `addValueEventListener` để lắng nghe dữ liệu thời gian thực từ nhánh `tasks` trên Firebase.

---

## HÌNH ẢNH MINH HỌA (OUTPUT)

### 1. Bài tập 1: Firebase Authentication
- **Màn hình đăng nhập:**
  *(Chèn ảnh chụp màn hình Login vào đây)*

- **Đăng nhập thất bại (Thông báo lỗi):**
  *(Chèn ảnh chụp màn hình Toast thông báo lỗi vào đây)*

- **Đăng nhập thành công (Trang chủ):**
  *(Chèn ảnh chụp màn hình HomeScreen vào đây)*

- **Màn hình thông tin cá nhân:**
  *(Chèn ảnh chụp màn hình Profile vào đây)*

### 2. Bài tập 2: Firebase Realtime Database
- **Dữ liệu hiển thị từ Firebase:**
  *(Chèn ảnh chụp màn hình Baitap2 vào đây)*
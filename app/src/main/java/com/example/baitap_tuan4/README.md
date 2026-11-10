# Bài tập về nhà tuần 4
1. MỤC TIÊU CHUNG
- Hiểu và áp dụng thành thạo Navigation Compose để điều hướng giữa các màn hình.
- Áp dụng Lập trình hướng đối tượng (OOP) với 4 đặc trưng: Trừu tượng, Kế thừa, Đa hình, Đóng gói.
- Biết cách Truyền dữ liệu (Data Flow) giữa các màn hình thông qua Navigation.



2. CHI TIẾT CÁC BÀI TẬP

Bài tập 1: UTH Todo (Navigation cơ bản)
- Mô tả: Xây dựng màn hình chào (Splash) tự động chuyển sang màn hình Onboarding gồm 3 trang giới thiệu tính năng. Có nút "Bỏ qua" và "Bắt đầu ngay" để về Menu chính.
- Giải thích hàm chính:
    + StartScreen(navController): Sử dụng LaunchedEffect và delay() để tự động điều hướng sau 1.5 giây.
    + OnboardingScreen(): Sử dụng HorizontalPager và rememberPagerState để tạo hiệu ứng vuốt ngang. Nút bấm dùng animateScrollToPage để chuyển trang mượt mà.

Bài tập 2: Hệ thống Quản lý Thư viện (OOP)
- Mô tả: Cho phép chọn sinh viên, hiển thị danh sách sách dưới dạng checkbox và thực hiện chức năng mượn sách. Sách đã mượn sẽ bị vô hiệu hóa.
- Ứng dụng OOP:
    + Trừu tượng & Kế thừa: abstract class Person -> class Student, open class Book -> class Ebook.
    + Đa hình: Ghi đè hàm getDisplayInfo() ở lớp Ebook (in ra kèm dung lượng file).
    + Đóng gói: Biến _borrowedBooks trong Student là private, chỉ có thể thêm sách thông qua hàm borrowBook().
- Giải thích hàm chính:
    + LibraryManager: Object chứa dữ liệu tĩnh (danh sách sinh viên, sách).
    + LazyColumn: Hiển thị danh sách sách tối ưu bộ nhớ.

Bài tập 3: Data Flow (Truyền dữ liệu)
- Mô tả: Luồng khôi phục mật khẩu gồm 4 màn hình: Nhập Email -> Nhập OTP -> Tạo mật khẩu mới -> Xác nhận. Cuối cùng hiển thị lại toàn bộ dữ liệu đã nhập.
- Giải thích hàm chính:
    + Truyền dữ liệu: Sử dụng navController.navigate("route/{param}") để đẩy dữ liệu sang màn hình mới. Tại màn hình nhận, dùng backStackEntry.arguments?.getString("param") để lấy dữ liệu ra.
    + Xóa lịch sử: Tại màn hình Confirm, nút Submit sử dụng popUpTo("bt3_forget") { inclusive = true } để xóa các màn hình trung gian và quay về màn hình đầu tiên.



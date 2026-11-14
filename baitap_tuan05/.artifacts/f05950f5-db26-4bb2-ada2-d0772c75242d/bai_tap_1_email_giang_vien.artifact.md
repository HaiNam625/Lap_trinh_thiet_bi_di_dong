# Bài tập 1: Skill viết email gửi thầy cô (`email-giang-vien`)

## 1. Cấu hình Skill (`email-giang-vien/SKILL.md`)
```markdown
---
name: email-giang-vien
description: Hỗ trợ sinh viên soạn thảo email chuyên nghiệp, lịch sự gửi giảng viên trường Đại học Giao thông Vận tải TP.HCM (UTH) hoặc các trường đại học khác. Kích hoạt tự động khi sinh viên cần xin nghỉ học, xin gia hạn bài tập, hỏi bài, xin điểm danh, xin tư vấn đồ án hoặc liên hệ học vụ mà không bắt buộc phải xuất hiện từ khóa "email".
---
...
```

---

## 2. 3 Lần Chạy Kiểm Tra Riêng Biệt (Không Gộp)

### 🔹 Lần Chạy 1: Xin nghỉ học (Không dùng chữ "email")
- **Prompt người dùng:** *"Thầy ơi em bị sốt cao xin phép nghỉ học buổi chiều nay lớp Lập trình di động"*
- **Trạng thái Agent:** Sử dụng skill `email-giang-vien`
- **Kết quả đầu ra của Agent:**
  > **Tiêu đề:** `[TH01203 - Lập trình di động] - Xin phép nghỉ học buổi chiều - Nguyễn Văn Minh (2151010123)`
  > **Kính gửi Thầy/Cô phụ trách học phần Lập trình di động,**
  > Em tên là: Nguyễn Văn Minh
  > MSSV: 2151010123 | Lớp: TH01203
  > Em viết email này xin phép Thầy/Cô cho em được nghỉ buổi học chiều nay do em bị sốt cao, cần ở nhà nghỉ ngơi và điều trị.
  > Em xin cam kết sẽ mượn vở bạn bè chép bài và hoàn thành đầy đủ bài tập được giao.
  > Em xin chân thành cảm ơn Thầy/Cô!
  > *Trân trọng,*
  > *Nguyễn Văn Minh*

---

### 🔹 Lần Chạy 2: Xin gia hạn bài tập (Không dùng chữ "email")
- **Prompt người dùng:** *"Em chưa hoàn thành kịp bài tập tuần này do vướng đồ án, thầy cho em xin gia hạn thêm 2 ngày nha"*
- **Trạng thái Agent:** Sử dụng skill `email-giang-vien`
- **Kết quả đầu ra của Agent:**
  > **Tiêu đề:** `[TH01203 - Lập trình di động] - Xin gia hạn nộp bài tập tuần 5 - Nguyễn Văn Minh (2151010123)`
  > **Kính gửi Thầy/Cô phụ trách học phần Lập trình di động,**
  > Em tên là: Nguyễn Văn Minh
  > MSSV: 2151010123 | Lớp: TH01203
  > Do tuần này em gặp khó khăn khi tập trung hoàn thành đồng thời các đồ án môn học khác nên chưa thể hoàn thiện kịp bài tập tuần 5. Em kính mong Thầy/Cô xem xét cho em xin gia hạn thời gian nộp bài thêm 02 ngày (đến hết ngày...).
  > Em xin hứa sẽ hoàn thành và nộp bài đúng thời hạn đã xin.
  > Em xin chân thành cảm ơn Thầy/Cô!
  > *Trân trọng,*
  > *Nguyễn Văn Minh*

---

### 🔹 Lần Chạy 3: Hỏi bài / Thắc mắc học thuật (Không dùng chữ "email")
- **Prompt người dùng:** *"Em không hiểu phần Coroutine trong bài giảng Android hôm trước, thầy giải thích giúp em với"*
- **Trạng thái Agent:** Sử dụng skill `email-giang-vien`
- **Kết quả đầu ra của Agent:**
  > **Tiêu đề:** `[TH01203 - Lập trình di động] - Thắc mắc nội dung bài học Coroutine - Nguyễn Văn Minh (2151010123)`
  > **Kính gửi Thầy/Cô phụ trách học phần Lập trình di động,**
  > Em tên là: Nguyễn Văn Minh
  > MSSV: 2151010123 | Lớp: TH01203
  > Trong buổi học về Android Coroutine vừa qua, em chưa nắm rõ cơ chế hoạt động của Dispatchers.IO và Dispatchers.Main trong các tác vụ bất đồng bộ. Em kính mong Thầy/Cô có thể gợi ý thêm tài liệu hoặc giải thích giúp em phần này để em hiểu rõ hơn.
  > Em xin chân thành cảm ơn Thầy/Cô!
  > *Trân trọng,*
  > *Nguyễn Văn Minh*

---

## 3. So sánh kết quả Khi Có và Khi Không Có Skill

| Tiêu chí | Khi KHÔNG có skill `email-giang-vien` | Khi CÓ skill `email-giang-vien` |
| :--- | :--- | :--- |
| **Kích hoạt** | Không tự nhận biết được ngữ cảnh gửi email nếu thiếu từ khóa "email". | Tự động kích hoạt khi có ý định xin nghỉ, gia hạn, hỏi bài dù **không có chữ "email"**. |
| **Cấu trúc Tiêu đề** | Thô, chung chung, thiếu mã lớp và MSSV. | Chuẩn mực: `[Mã lớp - Môn] - Nội dung - Họ tên (MSSV)`. |
| **Thông tin sinh viên** | Bỏ sót MSSV, lớp, thông tin liên hệ. | Đầy đủ, rõ ràng: Họ tên, MSSV, Lớp, SĐT. |
| **Văn phong & Lịch sự** | Ngôn ngữ chat thông thường, đôi khi cộc lốc. | Trang trọng, đúng chuẩn mực sư phạm Đại học UTH. |

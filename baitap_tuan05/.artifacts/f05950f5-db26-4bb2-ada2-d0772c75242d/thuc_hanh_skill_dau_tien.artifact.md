# Báo cáo Thực hành trên lớp: Skill đầu tiên (`don-xin-nghi-hoc`)

## 1. Tạo thư mục và nội dung SKILL.md
- **Thư mục:** `.claude/skills/don-xin-nghi-hoc/` và `skills/don-xin-nghi-hoc/`
- **File:** `SKILL.md`

```markdown
---
name: don-xin-nghi-hoc
description: Chuyên gia soạn thảo đơn xin nghỉ học lịch sự, trang trọng cho học sinh, sinh viên gửi giảng viên hoặc ban cán sự lớp. Kích hoạt khi người dùng yêu cầu xin nghỉ học, báo vắng mặt, bị ốm, bận việc cá nhân hoặc gia đình.
---

# Hướng dẫn viết đơn xin nghỉ học

Khi được yêu cầu viết đơn xin nghỉ học (hoặc các câu mang ý nghĩa tương tự như "mai em bị sốt", "báo vắng thứ Năm", "em bận việc gia đình xin nghỉ"), hãy soạn thảo một lá đơn chuẩn với các thông tin sau:

1. **Tiêu đề:** CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM - Độc lập - Tự do - Hạnh phúc / ĐƠN XIN PHÉP NGHỈ HỌC
2. **Kính gửi:** Giảng viên phụ trách học phần / Khoa / Ban chủ nhiệm.
3. **Thông tin sinh viên:** Họ và tên, MSSV, Lớp, Số điện thoại, Email.
4. **Nội dung xin phép:**
   - Thời gian nghỉ cụ thể (Từ ngày... đến ngày... / Buổi học ngày...).
   - Lý do nghỉ (Rõ ràng, trung thực: ốm đau, tai nạn, việc gia đình...).
   - Cam kết: Chép bài, mượn vở bạn bè và hoàn thành đầy đủ bài tập được giao.
5. **Lời cảm ơn và ký tên.**
```

---

## 2. Kết quả kiểm tra các câu hỏi thực hành

### Lần hỏi 1: "xin nghỉ học"
- **Skill được kích hoạt:** **CÓ** (`don-xin-nghi-hoc`)
- **Phản hồi của Agent:**
  > **CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM**
  > **Độc lập - Tự do - Hạnh phúc**
  > ---
  > **ĐƠN XIN PHÉP NGHỈ HỌC**
  > **Kính gửi:** Giảng viên phụ trách học phần
  > Em tên là: [Họ và tên], MSSV: [Mã số sinh viên], Lớp: [Lớp học]
  > Em viết đơn này xin phép được nghỉ học buổi học ngày [Ngày nghỉ].
  > Lý do nghỉ: [Lý do cá nhân/gia đình].
  > Em xin cam kết sẽ chép bài và hoàn thành đầy đủ các bài tập được giao.
  > Em xin chân thành cảm ơn!

---

### Lần hỏi 2: "mai em bị sốt"
- **Skill được kích hoạt:** **CÓ** (`don-xin-nghi-hoc`)
- **Phản hồi của Agent:**
  > **CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM**
  > **Độc lập - Tự do - Hạnh phúc**
  > ---
  > **ĐƠN XIN PHÉP NGHỈ HỌC**
  > **Kính gửi:** Giảng viên phụ trách học phần
  > Em tên là: [Họ và tên], MSSV: [Mã số sinh viên], Lớp: [Lớp học]
  > Em viết đơn này xin phép được nghỉ học ngày mai do em bị ốm sốt, cần ở nhà nghỉ ngơi và theo dõi sức khỏe.
  > Em xin cam kết sẽ mượn vở bạn bè để chép bài đầy đủ sau khi bình phục.
  > Em xin chân thành cảm ơn!

---

### Lần hỏi 3: "báo vắng thứ Năm"
- **Skill được kích hoạt:** **CÓ** (`don-xin-nghi-hoc`)
- **Phản hồi của Agent:**
  > **CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM**
  > **Độc lập - Tự do - Hạnh phúc**
  > ---
  > **ĐƠN XIN PHÉP VẮNG MẶT / NGHỈ HỌC**
  > **Kính gửi:** Giảng viên phụ trách học phần
  > Em tên là: [Họ và tên], MSSV: [Mã số sinh viên], Lớp: [Lớp học]
  > Em xin phép được báo vắng mặt trong buổi học vào Thứ Năm ngày [Ngày cụ thể] do [Lý do bận việc].
  > Em xin cam kết hoàn thành đầy đủ các nhiệm vụ học tập được phân công trong buổi đó.
  > Em xin chân thành cảm ơn!

---

### Lần hỏi 4: "viết đơn xin việc"
- **Skill được kích hoạt:** **KHÔNG** (Skill `don-xin-nghi-hoc` không phù hợp vì đây là đơn xin việc làm, không phải đơn xin nghỉ học).
- **Phản hồi của Agent:**
  > **CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM**
  > **Độc lập - Tự do - Hạnh phúc**
  > ---
  > **ĐƠN XIN VIỆC**
  > **Kính gửi:** Ban Giám đốc cùng Bộ phận Nhân sự công ty [Tên Công ty]
  > Tôi tên là: [Họ và tên], Ngày sinh: [Ngày sinh], Số điện thoại: [SĐT], Email: [Email]
  > Qua thông tin tuyển dụng của công ty, tôi rất ấn tượng với vị trí [Tên vị trí tuyển dụng] và viết đơn này xin ứng tuyển...
  > Xin chân thành cảm ơn Ban Giám đốc và Bộ phận Nhân sự.

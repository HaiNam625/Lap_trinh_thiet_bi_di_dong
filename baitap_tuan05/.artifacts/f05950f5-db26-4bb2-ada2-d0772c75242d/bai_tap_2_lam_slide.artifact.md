# Bài tập 2: Skill làm slide thuyết trình (`lam-slide-thuyet-trinh`)

## 1. Cấu hình Skill (`lam-slide-thuyet-trinh/SKILL.md`)
```markdown
---
name: lam-slide-thuyet-trinh
description: Chuyên gia thiết kế cấu trúc và lập dàn ý slide thuyết trình chuyên nghiệp cho các môn học đại học, đồ án, tiểu luận và báo cáo chuyên đề. Kích hoạt tự động khi người dùng yêu cầu làm slide, chuẩn bị bài thuyết trình, báo cáo môn học cho bất kỳ chủ đề nào.
---

# Quy trình và Quy tắc Làm Slide Thuyết Trinh

Khi người dùng yêu cầu làm slide thuyết trình cho bất kỳ môn học hoặc đề tài nào, hãy thực hiện theo quy trình 3 bước sau:

## Bước 1: Khảo sát thông tin
Hỏi người dùng các thông tin cốt lõi nếu chưa có:
- **Đề tài / Chủ đề:** Cụ thể là gì?
- **Thời lượng thuyết trình:** (ví dụ: 5 phút, 10 phút, 15 phút, 30 phút).
- **Đối tượng người nghe:** Giảng viên, hội đồng bảo vệ, hay các bạn sinh viên trong lớp.

## Bước 2: Lập dàn ý chi tiết (Outline)
Phân bổ số lượng slide theo thời lượng (quy tắc trung bình 1-2 phút/slide):
- **Slide 1:** Tiêu đề đề tài, Tên thành viên nhóm, Giảng viên hướng dẫn, Trường/Khoa.
- **Slide 2:** Mục tiêu đề tài / Đặt vấn đề.
- **Slide 3 - N:** Nội dung chính (chia thành các chương/phần logic, mỗi slide 3-5 ý chính dạng bullet point ngắn gọn).
- **Slide Cuối:** Tổng kết / Kết luận & Q&A (Hỏi đáp) / Lời cảm ơn.

## Bước 3: Soạn nội dung chi tiết từng slide
Với mỗi slide, trình bày rõ:
- **Tiêu đề slide (Slide Title)**
- **Nội dung ngắn gọn (Bullet points - tối đa 5-7 dòng mỗi slide)**
- **Gợi ý hình ảnh/biểu đồ minh họa (Visual suggestion)**
```

---

## 2. Bảng Kết Quả 10 Câu Hỏi Thử (Kiểm tra Description của Skill)

| STT | Câu hỏi thử nghiệm | Phân loại | Agent có gọi Skill không? | Nhận xét & Hiệu chỉnh Description |
| :---: | :--- | :---: | :---: | :--- |
| 1 | *"Làm giùm tôi slide báo cáo đồ án tốt nghiệp trong 15 phút"* | Nên gọi | **CÓ** | Khớp từ khóa "làm slide", kích hoạt chính xác. |
| 2 | *"Cần chuẩn bị bài thuyết trình môn Trí tuệ nhân tạo 10 phút"* | Nên gọi | **CÓ** | Khớp ngữ cảnh "thuyết trình", kích hoạt thành công. |
| 3 | *"Soạn giúp anh cấu trúc powerpoint tiểu luận Kinh tế vĩ mô"* | Nên gọi | **CÓ** | Khớp từ khóa "cấu trúc powerpoint tiểu luận", gọi skill chuẩn. |
| 4 | *"Tạo nội dung thuyết trình đề tài Lập trình di động"* | Nên gọi | **CÓ** | Kích hoạt skill nhờ từ khóa "nội dung thuyết trình". |
| 5 | *"Lên outline báo cáo chuyên đề tốt nghiệp"* | Nên gọi | **CÓ** | Kích hoạt tốt nhờ từ khóa "outline báo cáo". |
| 6 | *"Hôm nay trời đẹp quá nhỉ?"* | Không nên | **KHÔNG** | Câu giao tiếp thông thường, skill không kích hoạt. |
| 7 | *"Viết giúp tôi đoạn code Kotlin kết nối API"* | Không nên | **KHÔNG** | Yêu cầu lập trình kỹ thuật, không phải làm slide. |
| 8 | *"Giải thích hàm onCreate trong Android Activity"* | Không nên | **KHÔNG** | Câu hỏi kiến thức lập trình, skill không kích hoạt. |
| 9 | *"Tính giúp tôi tổng của các số từ 1 đến 100"* | Không nên | **KHÔNG** | Yêu cầu tính toán, skill không kích hoạt. |
| 10| *"Tóm tắt nội dung bài báo khoa học về AI"* | Không nên | **KHÔNG** | Yêu cầu tóm tắt tài liệu, không phải làm slide thuyết trình. |

---

## 3. Bộ Slide Mẫu cho Môn học Khác (Môn: Trí tuệ nhân tạo)

- **Đề tài:** Ứng dụng mô hình ngôn ngữ lớn (LLM) trong trợ lý ảo di động.
- **Thời lượng:** 10 phút.

### Slide 1: Trang bìa
- **Tiêu đề:** Ứng dụng Mô hình Ngôn ngữ Lớn (LLM) trong Trợ lý ảo Di động
- **Nội dung:**
  - Môn học: Trí tuệ nhân tạo nâng cao
  - Giảng viên hướng dẫn: TS. Nguyễn Văn A
  - Nhóm sinh viên thực hiện: Nhóm 05
- **Gợi ý hình ảnh:** Logo trường UTH, hình minh họa AI Assistant trên smartphone.

### Slide 2: Đặt vấn đề & Mục tiêu
- **Tiêu đề:** Đặt vấn đề và Mục tiêu nghiên cứu
- **Nội dung:**
  - Sự bùng nổ của Generative AI và LLM trên thiết bị di động.
  - Hạn chế của trợ lý ảo truyền thống (dựa trên rule-based/intent cứng nhắc).
  - **Mục tiêu:** Xây dựng mô hình tích hợp LLM chạy on-device phục vụ tự động hóa tác vụ.
- **Gợi ý hình ảnh:** Biểu đồ so sánh trợ lý truyền thống và trợ lý tích hợp AI.

### Slide 3: Kiến trúc Hệ thống
- **Tiêu đề:** Kiến trúc Tích hợp LLM trên Android
- **Nội dung:**
  - **UI Layer:** Jetpack Compose (Giao diện chat tương tác trực quan).
  - **Domain / Agent Layer:** Quản lý prompt, context handling và function calling.
  - **Model Layer:** MediaPipe / ML Kit GenAI Prompt API (Gemini Nano chạy on-device).
- **Gợi ý hình ảnh:** Sơ đồ khối kiến trúc 3 tầng (3-tier architecture).

### Slide 4: Thách thức & Giải pháp
- **Tiêu đề:** Thách thức kỹ thuật và Giải pháp tối ưu
- **Nội dung:**
  - *Thách thức 1:* Giới hạn tài nguyên phần cứng (RAM/GPU trên điện thoại).
    - *Giải pháp:* Sử dụng mô hình lượng tử hóa (quantized model) và prompt caching.
  - *Thách thức 2:* Độ trễ phản hồi (latency).
    - *Giải pháp:* Xử lý bất đồng bộ với Coroutines và Flow.
- **Gợi ý hình ảnh:** Icon biểu diễn hiệu năng CPU/RAM và tối ưu hóa tốc độ.

### Slide 5: Tổng kết & Hướng phát triển
- **Tiêu đề:** Kết luận và Hướng phát triển
- **Nội dung:**
  - **Kết luận:** Đã xây dựng thành công ứng dụng mẫu tích hợp LLM on-device thành công.
  - **Hướng phát triển:** Mở rộng hỗ trợ đa ngôn ngữ và tối ưu hóa tiêu thụ pin.
  - Cảm ơn Giảng viên và các bạn đã lắng nghe! Q&A.
- **Gợi ý hình ảnh:** Slide Q&A chuyên nghiệp.

---

## 4. Ghi Chú Khi Áp Dụng Cho Các Môn Học Khác
- **Điểm cần lưu ý:** Khi chuyển đổi giữa các môn (ví dụ từ Lập trình di động sang Kinh tế học hoặc Triết học), cần điều chỉnh trọng tâm dàn ý từ "Kiến trúc kỹ thuật / Code" sang "Phân tích số liệu / Lý luận".
- **Điểm cải tiến:** Thêm template định dạng màu sắc theo chủ đề môn học (ví dụ: xanh dương cho Công nghệ thông tin, đỏ/cam cho Kinh tế).

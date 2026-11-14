# Bộ Slide Mẫu: Ứng dụng Mô hình Ngôn ngữ Lớn (LLM) trong Trợ lý ảo Di động
(Được tạo bằng skill `lam-slide-thuyet-trinh`)

- **Môn học:** Trí tuệ nhân tạo nâng cao
- **Thời lượng:** 10 phút

---

## Slide 1: Trang bìa
- **Tiêu đề:** Ứng dụng Mô hình Ngôn ngữ Lớn (LLM) trong Trợ lý ảo Di động
- **Nội dung:**
  - Môn học: Trí tuệ nhân tạo nâng cao
  - Giảng viên hướng dẫn: TS. Nguyễn Văn A
  - Nhóm sinh viên thực hiện: Nhóm 05
- **Gợi ý hình ảnh:** Logo trường UTH, hình minh họa AI Assistant trên smartphone.

---

## Slide 2: Đặt vấn đề & Mục tiêu
- **Tiêu đề:** Đặt vấn đề và Mục tiêu nghiên cứu
- **Nội dung:**
  - Sự bùng nổ của Generative AI và LLM trên thiết bị di động.
  - Hạn chế của trợ lý ảo truyền thống (dựa trên rule-based/intent cứng nhắc).
  - **Mục tiêu:** Xây dựng mô hình tích hợp LLM chạy on-device phục vụ tự động hóa tác vụ.
- **Gợi ý hình ảnh:** Biểu đồ so sánh trợ lý truyền thống và trợ lý tích hợp AI.

---

## Slide 3: Kiến trúc Hệ thống
- **Tiêu đề:** Kiến trúc Tích hợp LLM trên Android
- **Nội dung:**
  - **UI Layer:** Jetpack Compose (Giao diện chat tương tác trực quan).
  - **Domain / Agent Layer:** Quản lý prompt, context handling và function calling.
  - **Model Layer:** MediaPipe / ML Kit GenAI Prompt API (Gemini Nano chạy on-device).
- **Gợi ý hình ảnh:** Sơ đồ khối kiến trúc 3 tầng (3-tier architecture).

---

## Slide 4: Thách thức & Giải pháp
- **Tiêu đề:** Thách thức kỹ thuật và Giải pháp tối ưu
- **Nội dung:**
  - *Thách thức 1:* Giới hạn tài nguyên phần cứng (RAM/GPU trên điện thoại).
    - *Giải pháp:* Sử dụng mô hình lượng tử hóa (quantized model) và prompt caching.
  - *Thách thức 2:* Độ trễ phản hồi (latency).
    - *Giải pháp:* Xử lý bất đồng bộ với Coroutines và Flow.
- **Gợi ý hình ảnh:** Icon biểu diễn hiệu năng CPU/RAM và tối ưu hóa tốc độ.

---

## Slide 5: Tổng kết & Hướng phát triển
- **Tiêu đề:** Kết luận và Hướng phát triển
- **Nội dung:**
  - **Kết luận:** Đã xây dựng thành công ứng dụng mẫu tích hợp LLM on-device thành công.
  - **Hướng phát triển:** Mở rộng hỗ trợ đa ngôn ngữ và tối ưu hóa tiêu thụ pin.
  - Cảm ơn Giảng viên và các bạn đã lắng nghe! Q&A.
- **Gợi ý hình ảnh:** Slide Q&A chuyên nghiệp.

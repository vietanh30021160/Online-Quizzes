function navigateToSection(sectionId) {
    event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết
    window.location.href = "/#" + sectionId;

    // Cuộn xuống phần có id được chỉ định sau khi chuyển hướng
    setTimeout(function() {
        var section = document.getElementById(sectionId);
        if (section) {
            section.scrollIntoView({ behavior: 'smooth' });
        }
    }, 500); // Đợi 0.5 giây trước khi cuộn để đảm bảo trang đã được tải đầy đủ
}

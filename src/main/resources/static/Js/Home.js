
    function searchBySubject(subjectName) {

    // Tạo URL với tham số ?keyword= và subjectName
    var url = '/?keyword=' + encodeURIComponent(subjectName) ;

    // Chuyển hướng trang web đến URL
    window.location.href = url;
}

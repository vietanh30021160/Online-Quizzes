const dropArea = document.querySelector(".drop_box"),
            button = dropArea.querySelector("button"),
            dragText = dropArea.querySelector("header"),
            input = dropArea.querySelector("input");
        
        button.onclick = () => {
            input.click();
        };
        
        input.addEventListener("change", function (e) {
    const file = e.target.files[0];
    const fileName = file.name;
    const fileExtension = fileName.split('.').pop().toLowerCase();
    if (fileExtension !== 'xlsx') {
        alert('Please select a valid XLSX file.');
        return;
    }
    const filedata = `
        <form id="uploadForm" th:action="@{/quizzes/uploaduiz-data}" method="post" enctype="multipart/form-data">
            <div class="form">
                <h4>${fileName}</h4>
                <button class="btn" type="submit">Upload</button>
            </div>
        </form>`;
    dropArea.innerHTML = filedata;
    
    // Gán lại sự kiện click cho nút "Upload"
    const uploadForm = document.getElementById("uploadForm");
    const uploadButton = uploadForm.querySelector("button");
    uploadButton.addEventListener("click", () => {
        uploadForm.submit(); // Gửi form khi nút "Upload" được nhấp
    });
        });
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
                <form th:action="@{/uploadquizdata}" method="post" enctype="multipart/form-data">
                    <div class="form">
                        <h4>${fileName}</h4>
                        <button class="btn" type="submit">Upload</button>
                    </div>
                </form>`;
            dropArea.innerHTML = filedata;
        });
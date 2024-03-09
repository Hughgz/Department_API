$(document).ready(function() {
                $("#userSearchForm").submit(function(event) {
                    event.preventDefault();

                    $.ajax({
                        type: "GET", // Sử dụng phương thức GET hoặc POST tùy thuộc vào cài đặt của bạn
                        url: "/user/search",
                        data: $(this).serialize(),
                        success: function(response) {
                            // Xử lý kết quả thành công
                        },
                        error: function(xhr, status, error) {
                            var errors = JSON.parse(xhr.responseText);
                            // Hiển thị lỗi trong #errorContainer
                            $("#errorContainer").html(errors.name).show();
                        }
                    });
                });
            });
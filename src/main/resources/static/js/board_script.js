$(document).ready(function(){
    // 글쓰기
    var board = {
        init: function () {
            var _this = this;
            $("#btn_write").on("click", function () {
                _this.getWrite();
            });
            $("#btn_update").on("click", function () {
                _this.getUpdate();
            });
            $("#btn_delete").on("click", function () {
                _this.getDelete();
            });
            $('#pagenation').bootpag({
                total: $("#totalPages").val(), // total pages
                page: $("#currentPage").val(), // default page
                maxVisible: 10, // visible pagination
                leaps: false,
                firstLastUse: true,
                next: '<img src="/images/paging/next_1.gif" alt="뒤로버튼" />',
                prev: '<img src="/images/paging/prev_2.gif" alt="맨앞으로버튼" />',
                first: '<img src="/images/paging/prev_1.gif" alt="맨앞으로버튼" />',
                last: '<img src="/images/paging/next_2.gif" alt="맨뒤로버튼" />'
            }).unbind("page").bind("page").on("page", function (event, pageNo) {
                window.location.replace("/board/list?pageNo=" + pageNo + "&pageSize=10");
            });

            // init page 1
            $("#btn_list").on('click', function(){
                window.location.replace("/board/list?pageNo=1&pageSize=10");
            });

            return this;
        },
        getWrite: function () {
            // 답글의 경우 url 변경
            var groupNo = $("#groupNo").val() ? $("#groupNo").val() : 0;
            var parentNo =  $("#parentNo").val() ? $("#parentNo").val() : 0;
            var url = (groupNo) ? "/board/writeReply" : "/board/write";
            var data = {
                title: $("#title").val(),
                content: $("#content").val(),
                groupNo: groupNo,
                parentNo: parentNo
            };
            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data)
            }).done(function () {
                alert("등록 되었습니다.");
                window.location.replace("/board/list?pageNo=1&pageSize=10");
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        getUpdate: function () {
            var data = {
                bNo: $("#bNo").val(),
                writer: $("#writer").val(),
                title: $("#title").val(),
                content: $("#content").val(),
            };
            $.ajax({
                type: "PUT",
                url: "/board/update/" + data.bNo,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data)
            }).done(function (result) {
                console.log(result);
                if(result.bNo > 0){
                    alert("수정되었습니다.");
                    location.href= "/board/detail/" + result.bNo;
                }else{
                    alert("자신이 쓴 글만 수정 할 수 있습니다.");
                    history.go(0);
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        getDelete: function () {
            var bNo =  $("#bNo").val();
            $.ajax({
                type: "DELETE",
                url: "/board/delete/" + bNo,
            }).done(function (result) {
                if(result === true){
                    alert("삭제되었습니다.");
                    window.location.replace("/board/list?pageNo=1&pageSize=10");
                }else{
                    alert("자신이 쓴 글만 삭제 할 수 있습니다.");
                    history.go(0);
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },

    };

    board.init();

});



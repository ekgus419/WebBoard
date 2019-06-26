$(document).ready(function(){
    // 글쓰기
    var board = {
        init: function () {
            var _this = this;
            $("#btn_list").on("click", function () {
                _this.getList();
            });
            $("#btn_write").on("click", function () {
                _this.getWrite();
            });
            $("#btn_update").on("click", function () {
                _this.getUpdate();
            });
            $("#btn_delete").on("click", function () {
                alert("test");
                _this.getDelete();
            });
        },
        getList: function () {
            $.ajax({
                type: "GET",
                url: "/board/list",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
            }).done(function () {
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        getWrite: function () {
            var data = {
                title: $("#title").val(),
                content: $("#content").val()
            };
            $.ajax({
                type: "POST",
                url: "/board/write",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data)
            }).done(function () {
                alert("등록 되었습니다.");
                location.href= "/board/list";
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        getUpdate: function () {
            var data = {
                bNo: $("#bNo").val(),
                title: $("#title").val(),
                content: $("#content").val(),
            };
            $.ajax({
                type: "PUT",
                url: "/board/update/" + data.bNo,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data)
            }).done(function () {
                alert("수정되었습니다.");
                location.href= "/board/detail/" + data.bNo;
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
                // dataType: "json",
                // contentType: "application/json; charset=utf-8",
            }).done(function () {
                alert("삭제되었습니다.");
                location.href= "/board/list";
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },

    };
    board.init();
});



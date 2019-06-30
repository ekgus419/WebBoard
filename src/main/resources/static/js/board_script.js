$(document).ready(function(){
    var pagenationConf = {
        leaps: false,
        firstLastUse: true,
        next: '<img src="/images/paging/next_1.gif" alt="뒤로버튼" />',
        prev: '<img src="/images/paging/prev_2.gif" alt="맨앞으로버튼" />',
        first: '<img src="/images/paging/prev_1.gif" alt="맨앞으로버튼" />',
        last: '<img src="/images/paging/next_2.gif" alt="맨뒤로버튼" />'
    };
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
                _this.getDelete();
            });
            $('#pagenation').bootpag({}).on('page', function (event, num) {
                board.getList(num);
            });
            return this;
        },
        getList: function (pageNo) {
            // var data = $("#form_list").serializeArray();
            // data.push({name: 'page', value: pageNo || 1});
            // data.push({name: 'pagesize', value: 10});
            // data.push({name: 'pagesize', value: pageSize || 10});
            // http://localhost:8080/board/list/1?pageSize=3
            var pageSize=10;
            if(typeof pageNo == "undefined"){
                pageNo=1;
            }
            $.ajax({
                type: "GET",
                url: "/board/list/" + pageNo +"/?pageSize=" + pageSize,
                // dataType: "json",
                // contentType: "application/json; charset=utf-8",
                // data: data
            }).done(function (data) {
                location.href= "/board/list//" + pageNo +"/?pageSize=" + pageSize;

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
                location.href= "/board/list/1";
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                // console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
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
            }).done(function (data) {
                if(data.content == null){
                    alert("자신이 쓴 글만 수정 가능합니다.");
                    history.go(-1);
                }else{
                    alert("수정되었습니다.");
                    location.href= "/board/detail/" + data.bno;
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
            }).done(function (data) {
                alert("삭제되었습니다.");
                location.href= "/board/list/1";
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR.reponseJSON.path);
                console.log(textStatus);
                console.log(errorThrown);
                // console.log("sss : " + responseJSON);
                // console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },

    };
    board.init();
});



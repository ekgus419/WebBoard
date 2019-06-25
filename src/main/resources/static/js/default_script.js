$(document).ready(function(){

    $("input:text[name=user_email]").focus();

    // 회원가입시 form 데이터 검증
    $("#signUpForm").validate({
        focusCleanup: true, //true이면 잘못된 필드에 포커스가 가면 에러를 지움
        focusInvalid:false, //유효성 검사후 포커스를 무효필드에 둠 꺼놓음
        onclick: false, //클릭시 발생됨 꺼놓음
        onfocusout:false, //포커스가 아웃되면 발생됨 꺼놓음
        onkeyup:false, //키보드 키가 올라가면 발생됨 꺼놓음
        rules: {
            user_name: { required: true, minlength: 2},
            user_pwd: { required: true, minlength: 4 },
            user_repwd: { equalTo: "#user_pwd"},
            user_email : { required: true, email: true}
        },
        messages: {
            user_name: {
                required: "닉네임을 입력하세요.",
                minlength: "닉네임은 2자 이상 입력해주세요."
            },
            user_pwd: {
                required: "비밀번호를 입력하세요." ,
                minlength: "비밀번호는 4자 이상 입력해주세요."
            },
            user_repwd: {
                equalTo: "입력하신 비밀번호가 다릅니다."
            },
            user_email: {
                required: "이메일을 입력하세요.",
                user_email: "잘못된 이메일 형식입니다."
            }
        },
        errorPlacement: function (){ //validator는 기본적으로 validation 실패시 메세지를 우측에 표시하게 되어있다 원치않으면 입력해놓을것 ★안쓰면 에러표시됨★
            console.log("errorPlacement")
        },
        invalidHandler: function(form, validator){ //입력값이 잘못된 상태에서 submit 할때 호출
            var errors = validator.numberOfInvalids();
            if (errors) {
                alert(validator.errorList[0].message);
                validator.errorList[0].element.focus();
            }
        }
    });

    // 회원 가입
    var signUp = {
        init: function () {
            var _this = this;
            $("#btn_signup").on("click", function () {
                _this.save();
            });
        },
        save: function () {
            var data = {
                userEmail: $("#user_email").val(),
                userPwd: $("#user_pwd").val(),
                userName: $("#user_name").val(),
            };
            $.ajax({
                type: "POST",
                url: "/signup",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data)
            }).done(function () {
                alert("가입되었습니다.");
                location.href= "/login";
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        }

    };

    signUp.init();

});



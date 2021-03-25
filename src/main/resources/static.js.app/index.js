var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#btn-login').on('click', function () {
            _this.login();
        });
        $('#btn-signup').on('click', function () {
            _this.signup();
        });
        $('#rec_update').on('click', function () {
            _this.rec_update();
        })

    },
    save: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = "/";
        }).fail(function () {
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()

        };
        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    login: function () {
        var data = {
            userId: $('#userId').val(),
            password: $('#password').val()
        };
        $.ajax({
            type: 'POST',
            url: '/login',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('로그인 성공');
            window.location.href = '/';
        }).fail(function (error) {
            alert('로그인 실패');
            alert(JSON.stringify(error));
            window.location.href = "/login";
        });
    },
    signup: function () {
        var data = {
            name: $('#name').val(),
            userId: $('#userId').val(),
            password: $('#password').val()
        };
        $.ajax({
            type: 'POST',
            url: '/signup',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('회원가입 완료.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
            window.location.href = "/";
        });
    },
    rec_update: function () {
        var data = {
            no: $('#posts.id').val(),
            id: $('#userName.id').val()

        };
        $.ajax({
            type: 'POST',
            url: '/RecUpdate.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('회원가입 완료.');
        });
    },
    recCount: function () {
        var data = {
            no: $('#posts').val()
        }
        $.ajax({
            url: "/RecCount.do",
            type: "POST",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (count) {
            $(".rec_count");

        });
    }

};
main.init();
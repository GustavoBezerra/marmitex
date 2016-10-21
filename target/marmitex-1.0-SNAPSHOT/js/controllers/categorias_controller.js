angular.module("app").controller("gerenciarCategoriaCtrl", function ($scope) {
    $scope.titulo = "Gerenciar Categoria";

    $scope.sair = function () {
        window.sessionStorage.clear();
        window.location.href = "http://localhost:8084/marmitex/login.html";
    };

    $scope.opAlterar = false;

    $scope.consultar = function () {
        $.ajax({
            url: "/marmitex/categoria",
            type: "POST",
            async: false,
            data: {
                operacao: "CONSULTAR"
            },
            success: function (data) {
                console.log("consultou");
                $scope.categorias = JSON.parse(data);
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.excluir = function (categoria) {
        $.ajax({
            url: "/marmitex/categoria",
            type: "POST",
            async: false,
            data: {
                operacao: "EXCLUIR",
                id: categoria.id
            },
            success: function () {
                $scope.consultar();
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.salvar = function (categoria) {
        $.ajax({
            url: "/marmitex/categoria",
            type: "POST",
            async: false,
            data: {
                operacao: "SALVAR",
                nome: categoria.nome
            },
            success: function () {
                $scope.consultar();
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.alterar = function () {
        var newNome = document.getElementById("txtNome").value;
        $.ajax({
            url: "/marmitex/categoria",
            type: "POST",
            async: false,
            data: {
                operacao: "ALTERAR",
                id: cat.id,
                nome: newNome
            },
            success: function () {
                $scope.consultar();
                alert("Categoria alterada!");
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
        $scope.cancelar();
    };
    var cat;
    $scope.visualizar = function (categoria) {
        cat = categoria;
        document.getElementById("txtNome").value = categoria.nome;
        $scope.opAlterar = true;
    };

    $scope.cancelar = function () {
        document.getElementById("txtNome").value = "";
        $scope.opAlterar = false;
    };
});
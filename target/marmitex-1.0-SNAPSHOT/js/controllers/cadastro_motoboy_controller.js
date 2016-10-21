angular.module("app").controller("cadastroMotoboyCtrl", function ($scope) {
    $scope.titulo = "Cadastro Motoboy";

    $scope.sair = function () {
        window.sessionStorage.clear();
        window.location.href = "http://localhost:8084/marmitex/login.html";
    };

    $scope.opAlterar = false;

    $scope.consultar = function () {
        $.ajax({
            url: "/marmitex/entregador",
            type: "POST",
            async: false,
            data: {
                operacao: "CONSULTAR"
            },
            success: function (data) {
                console.log("consultou");
                $scope.entregadores = JSON.parse(data);
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.excluir = function (motoboy) {
        $.ajax({
            url: "/marmitex/entregador",
            type: "POST",
            async: false,
            data: {
                operacao: "EXCLUIR",
                id: motoboy.id
            },
            success: function () {
                motoboy.nome = "";
                $scope.consultar();
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.salvar = function (motoboy) {
        $.ajax({
            url: "/marmitex/entregador",
            type: "POST",
            async: false,
            data: {
                operacao: "SALVAR",
                nome: motoboy.nome
            },
            success: function () {
                $scope.cancelar();
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
            url: "/marmitex/entregador",
            type: "POST",
            async: false,
            data: {
                operacao: "ALTERAR",
                id: mot.id,
                nome: newNome
            },
            success: function () {
                $scope.consultar();
                alert("Motoboy alterado!");
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
        $scope.cancelar();
    };
    var mot;
    $scope.visualizar = function (motoboy) {
        mot = motoboy;
        document.getElementById("txtNome").value = motoboy.nome;
        $scope.opAlterar = true;
    };

    $scope.cancelar = function () {
        document.getElementById("txtNome").value = "";
        $scope.opAlterar = false;
    };
});
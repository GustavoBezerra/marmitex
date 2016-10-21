angular.module("app").controller("MeusDadosCtrl", function ($scope) {
    $scope.titulo = "Meus Dados";
    $scope.usuario = parseObj;

    $scope.sair = function () {
        window.sessionStorage.clear();
        window.location.href = "http://localhost:8084/marmitex/login.html";
    };

    $scope.getCreditos = function () {
        return $scope.usuario.credito.valor.toFixed(2);
    };

    $scope.alterar = function (usuario) {
        if (usuario.usuario.senha === document.getElementById("txtConfirmarSenha").value) {
            $.ajax({
                url: "/marmitex/cliente",
                type: "POST",
                async: false,
                data: {
                    operacao: "ALTERAR",
                    id: usuario.id,
                    nome: usuario.nome,
                    email: usuario.usuario.login,
                    senha: usuario.usuario.senha,
                    telefone: usuario.telefone
                },
                success: function (data) {
                    window.sessionStorage.setItem("usuario", data);
                    alert("Dados alterados!");
                },
                error: function (err) {
                    console.log(err.statusText);
                }
            });
        } else {
            alert("Senhas diferentes!");
        }
    };

    $scope.excluir = function (usuario) {
        var decisao = confirm("Deseja mesmo excluir sua conta?");
        if (decisao) {
            $.ajax({
                url: "/marmitex/cliente",
                type: "POST",
                async: false,
                data: {
                    operacao: "EXCLUIR",
                    id: usuario.id
                },
                success: function () {
                    alert("Conta excluída!");
                    $scope.sair();
                },
                error: function (err) {
                    console.log(err.statusText);
                }
            });
        }
    };
});
angular.module("app").controller("loginCtrl", function ($scope) {
    $scope.titulo = "Login";
    $scope.redirecionar = function (data) {
        window.sessionStorage.setItem("usuario", data);
        var usuario = JSON.parse(data);
        if (usuario.id === 1) { // é o admin?
            window.location.href = "http://localhost:8084/marmitex/pedidos_abertos.html";
        } else {
            var marmitex = window.sessionStorage["marmitex"];
            if (marmitex !== undefined) {
                var marmitex = JSON.parse(window.sessionStorage["marmitex"]);
                if (marmitex.length !== 0) {
                    window.location.href = "http://localhost:8084/marmitex/escolhas_pedido.html";
                } else {
                    window.location.href = "http://localhost:8084/marmitex/meus_pedidos.html";
                }
            } else {
                window.location.href = "http://localhost:8084/marmitex/meus_pedidos.html";
            }
        }
    };

    $scope.consultar = function (login) {
        $.ajax({
            url: "/marmitex/cliente",
            type: "POST",
            async: false,
            data: {
                operacao: "CONSULTAR",
                login: login.email,
                senha: login.senha
            },
            success: function (data) {
                if (data === "[]") {
                    alert("Nenhum usuário encontrado com o login e a senha informados!");
                } else {
                    $scope.redirecionar(data);
                }
            },
            error: function () {
                alert("[ERRO] Nenhum usuário encontrado com o login e a senha informados!");
            }
        });
    };
    $scope.salvar = function (usuario) {
        if (usuario.confirmar_senha !== usuario.senha) {
            alert("Senhas diferentes!");
        } else {
            $.ajax({
                url: "/marmitex/cliente",
                type: "POST",
                async: false,
                data: {
                    operacao: "SALVAR",
                    login: usuario.email,
                    senha: usuario.senha,
                    telefone: usuario.telefone,
                    nome: usuario.nome
                },
                success: function (data) {
                    var resultado = JSON.parse(data);
                    if (resultado.mensagem !== undefined && resultado.mensagem !== null) {
                        alert(resultado.mensagem);
                    } else {
                        $scope.redirecionar(JSON.stringify(resultado.entidades[0]));
                    }

                },
                error: function () {
                    alert("Erro ao cadastrar!");
                }
            });
        }
    };
});
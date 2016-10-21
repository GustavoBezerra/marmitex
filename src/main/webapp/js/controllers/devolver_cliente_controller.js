angular.module("app").controller("DadosPedidoCtrl", function ($scope) {
    $scope.titulo = "Meus Dados";
    $scope.usuario = parseObj;
    $scope.pedido = parsePedido;

    $scope.sair = function () {
        window.sessionStorage.clear();
        window.location.href = "http://localhost:8084/marmitex/login.html";
    };

    $scope.getPedido = function () {
        $.ajax({
            url: "/marmitex/pedido",
            type: "POST",
            async: false,
            data: {
                operacao: "CONSULTAR",
                id_pedido: $scope.pedido.id
            },
            success: function (data) {
                var aux = JSON.parse(data);
                $scope.pedido = aux[0];
            },
            error: function (err) {
                alert("Erro ao consultar o pedido!\n" + err);
            }
        });
    };

    $scope.getCreditos = function () {
        $.ajax({
            url: "/marmitex/cliente",
            type: "POST",
            async: false,
            data: {
                operacao: "CONSULTAR",
                login: $scope.usuario.usuario.login,
                senha: $scope.usuario.usuario.senha
            },
            success: function (data) {
                $scope.usuario = JSON.parse(data);
                window.sessionStorage.setItem("usuario", data);
            },
            error: function () {
                alert("[ERRO] Nenhum usuário encontrado com o login e a senha informados!");
            }
        });
    };

    $scope.getValorTotal = function () {
        return $scope.pedido.valorTotal.toFixed(2);
    };

    $scope.devolver = function (pedido) {
        $.ajax({
            url: "/marmitex/pedido",
            type: "POST",
            async: false,
            data: {
                operacao: "ALTERAR",
                valor_total: pedido.valorTotal,
                id_pedido: pedido.id,
                status: "Devolvido",
                cliente: JSON.stringify(pedido.cliente)
            },
            success: function (data) {
                $scope.usuario.credito.valor = ($scope.usuario.credito.valor + pedido.valorTotal);
                window.sessionStorage.setItem("usuario", JSON.stringify($scope.usuario));
                $scope.getCreditos();
                window.location.href = "http://localhost:8084/marmitex/meus_pedidos.html"
            },
            error: function (err) {
                alert("Erro ao alterar o pedido!\n" + err);
            }
        });
    };

    $scope.cancelar = function (pedido) {
        alert("Entrou no cancelar");
        //                    $.ajax({
        //                        url: "/marmitex/pedido",
        //                        type: "POST",
        //                        async: false,
        //                        data: {
        //                            operacao: "ALTERAR",
        //                            valor_total: pedido.valorTotal,
        //                            id_pedido: pedido.id,
        //                            status: "Cancelado",
        //                            cliente: JSON.stringify(pedido.cliente)
        //                        },
        //                        success: function (data) {
        //                            $scope.usuario.credito.valor = ($scope.usuario.credito.valor + pedido.valorTotal);
        //                            window.sessionStorage.setItem("usuario", JSON.stringify($scope.usuario));
        //                            $scope.getCreditos();
        //                            window.location.href = "http://localhost:8084/marmitex/meus_pedidos.html"
        //                        },
        //                        error: function (err) {
        //                            alert("Erro ao alterar o pedido!\n" + err);
        //                        }
        //                    });
    };

    $scope.devolverMarmitex = function (marmitex) {
        $.ajax({
            url: "/marmitex/marmitex",
            type: "POST",
            async: false,
            data: {
                operacao: "EXCLUIR",
                marmita: JSON.stringify(marmitex),
                status: "Devolvido"
            },
            success: function (data) {
                alert("Marmitex devolvida!");
                $scope.getCreditos();
                $scope.getPedido();
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.cancelarMarmitex = function (marmitex) {
        $.ajax({
            url: "/marmitex/marmitex",
            type: "POST",
            async: false,
            data: {
                operacao: "EXCLUIR",
                marmita: JSON.stringify(marmitex),
                status: "Cancelado"
            },
            success: function (data) {
                alert("Marmitex cancelada!");
                $scope.getCreditos();
                $scope.getPedido();
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.getPagamento = function (pedido) {
        var txt = "";
        for (var i = 0; i < pedido.pagamento.length; i++) {
            txt += pedido.pagamento[i] + ", ";
        }
        return txt.slice(0, -2);
    };

    $scope.getEndereco = function (pedido) {
        return pedido.endereco.rua + ", " + pedido.endereco.numero + " - " + pedido.endereco.bairro;
    };

    $scope.getDescricao = function (pedido) {
        var desc = "";
        for (var j = 0; j < pedido.ingredientes.length; j++) {
            desc += pedido.ingredientes[j].nome + ", ";
        }
        return desc.slice(0, -2);
    };

    $scope.dataAtualFormatada = function () {
        var data = new Date();
        var dia = data.getDate();
        if (dia.toString().length === 1)
            dia = "0" + dia;
        var mes = data.getMonth() + 1;
        if (mes.toString().length === 1)
            mes = "0" + mes;
        var ano = data.getFullYear();
        return dia + "/" + mes + "/" + ano;
    };
});
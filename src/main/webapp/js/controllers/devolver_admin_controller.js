angular.module("app").controller("DadosPedidoCtrl", function ($scope) {
    $scope.usuario = parseObj;
    $scope.pedido = parsePedido;

    $scope.sair = function () {
        window.sessionStorage.clear();
        window.location.href = "http://localhost:8084/marmitex/login.html";
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
                window.location.href = "http://localhost:8084/marmitex/pedidos_abertos.html"
            },
            error: function (err) {
                alert("Erro ao alterar o pedido!\n" + err);
            }
        });
    };

    $scope.devolverMarmitex = function (marmitex) {
        //                    $.ajax({
        //                        url: "/marmitex/marmitex",
        //                        type: "POST",
        //                        async: false,
        //                        data: {
        //                            operacao: "EXCLUIR",
        //                            marmita: marmitex
        //                        },
        //                        success: function () {
        //                            alert("Marmitex devolvida!");
        //                        },
        //                        error: function (err) {
        //                            console.log(err.statusText);
        //                        }
        //                    });
        alert("Marmitex devolvida!");
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
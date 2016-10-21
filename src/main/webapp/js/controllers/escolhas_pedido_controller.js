angular.module("app").controller("DadosCtrl", function ($scope) {
    $scope.titulo = "Meus Dados";
    $scope.usuario = parseObj;
    $scope.marmitex = m;
    $scope.preco = p;
    $scope.entrega = 2;
    $scope.enderecos;
    $scope.outroEndereco = false;
    $scope.troco = false;
    var endereco;

    $scope.sair = function () {
        window.sessionStorage.clear();
        window.location.href = "http://localhost:8084/marmitex/login.html";
    };

    $scope.getCreditos = function () {
        return $scope.usuario.credito.valor.toFixed(2);
    };

    $scope.buscarCEP = function (pd) {
        if (pd.cep !== null && pd.cep !== undefined && pd.cep !== "") {
            $.ajax({
                url: "https://viacep.com.br/ws/" + pd.cep + "/json/",
                async: false,
                data: {
                },
                success: function (data) {
                    pd.cidade = data.localidade;
                    pd.rua = data.logradouro;
                    pd.bairro = data.bairro;
                },
                error: function (err) {
                    console.log(err);
                }
            });
        }
    };

    $scope.consultarEndereco = function () {
        $.ajax({
            url: "/marmitex/endereco",
            type: "POST",
            data: {
                operacao: "CONSULTAR",
                id: $scope.usuario.id
            },
            success: function (data) {
                $scope.enderecos = JSON.parse(data);
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.consultarTudo = function () {
        $scope.consultarEndereco();
    };

    $scope.cancelar = function () {
        var decisao = confirm("Deseja mesmo cancelar o pedido?");
        if (decisao) {
            window.sessionStorage.clear();
            alert("Pedido cancelado!");
            $scope.sair();
        }
    };

    $scope.novoEndereco = function () {
        if ($scope.outroEndereco === true) {
            $scope.outroEndereco = false;
        } else {
            $scope.outroEndereco = true;
        }
    };

    $scope.pagamentoDinheiro = function (obj) {
        if (obj.dinheiro === true) {
            if (confirm("Precisa de troco?")) {
                obj.troco = "";
                $scope.troco = true;
            } else {
                obj.troco = 0;
                $scope.troco = false;
            }
        } else {
            $scope.troco = false;
        }
    };

    $scope.pagamentoCreditos = function (obj) {
        if (obj.credito === true) {
            if ($scope.usuario.credito.valor < $scope.total()) {
                alert("Você não possui a quantidade necessária de créditos!\n" +
                    "Favor escolher mais uma forma de pagamento.");
            }
        }
    };

    $scope.finalizar = function (data) {
        if ($scope.pedido.endereco === undefined || $scope.pedido.endereco === "") {
            window.sessionStorage.setItem("endereco", JSON.stringify(data.outro_endereco));
            endereco = JSON.stringify(data.outro_endereco);
        } else { // escolheu um endereço cadastrado
            for (var i = 0; i < $scope.enderecos.length; i++) {
                if ($scope.enderecos[i].id === parseInt($scope.pedido.endereco)) {
                    $scope.enderecos[i].dtCriacao = null;
                    window.sessionStorage.setItem("endereco", JSON.stringify($scope.enderecos[i]));
                    endereco = JSON.stringify($scope.enderecos[i]);
                    break;
                }
            }
        }
        if (data.pagamento.credito === true &&
            (data.pagamento.dinheiro === false || data.pagamento.dinheiro === undefined) &&
            (data.pagamento.cartao === false || data.pagamento.cartao === undefined)) {
            if ($scope.usuario.credito.valor < $scope.total()) {
                alert("Você não possui a quantidade necessária de créditos!\n" +
                    "Favor escolher mais uma forma de pagamento.");
                return;
            }
        }
        var testeJson = "[{'ingredientes':'" + $scope.marmitex[0].toString() + "'}]";
        window.sessionStorage.setItem("pedido", JSON.stringify(data));
        window.sessionStorage.setItem("total", JSON.stringify($scope.total()));
        $.ajax({
            url: "/marmitex/pedido",
            type: "POST",
            data: {
                operacao: "SALVAR",
                endereco: endereco,
                pedido: data,
                total: $scope.total(),
                ingredientes: JSON.stringify($scope.marmitex),
                dinheiro: data.pagamento.dinheiro,
                troco: data.pagamento.troco,
                cartao: data.pagamento.cartao,
                credito: data.pagamento.credito,
                id_cliente: $scope.usuario.id
            },
            success: function (data) {
                window.location.href = "http://localhost:8084/marmitex/resumo_pedido.html";
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.total = function () {
        return (parseFloat($scope.preco) + parseFloat($scope.entrega)).toFixed(2);
    };

});
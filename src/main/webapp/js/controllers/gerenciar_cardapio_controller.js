var ingredientes = document.getElementsByName("ingredientes");
var ingredientesCheckados = new Array();

angular.module("app").controller("gerenciarCardapioCtrl", function ($scope) {
    $scope.titulo = "Gerenciar Cardapio";
    $scope.preco = 0;

    $scope.sair = function () {
        window.sessionStorage.clear();
        window.location.href = "http://localhost:8084/marmitex/login.html";
    };

    $scope.consultarTudo = function () {
        $scope.consultarCategorias();
        $scope.consultarIngredientes();
    };

    $scope.consultarCategorias = function () {
        $.ajax({
            url: "/marmitex/categoria",
            type: "POST",
            async: false,
            data: {
                operacao: "CONSULTAR"
            },
            success: function (data) {
                $scope.categorias = JSON.parse(data);
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.consultarIngredientes = function () {
        $.ajax({
            url: "/marmitex/ingrediente",
            type: "POST",
            async: false,
            data: {
                operacao: "CONSULTAR"
            },
            success: function (data) {
                $scope.recursos = JSON.parse(data);
            },
            error: function (err) {
                console.log(err.statusText);
            }
        });
    };

    $scope.clicou = function (recurso) {
        var r = document.getElementById(recurso.nome);
        if (r.checked === true) {
            ingredientesCheckados.push(recurso);
            $scope.preco += recurso.valor;
        } else {
            for (var i = ingredientesCheckados.length - 1; i >= 0; i--) {
                if (ingredientesCheckados[i] === recurso) {
                    ingredientesCheckados.splice(i, 1);
                    break;
                }
            }
            $scope.preco -= recurso.valor;
        }
    };
});
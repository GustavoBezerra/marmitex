angular.module("app").controller("marmitexQtde", function ($scope) {
    $scope.quantidade = 1;
    $scope.marmitexs = [
        { id: "1" },
        { id: "2" },
        { id: "3" }
    ];

    $scope.proximo = function () {
        for (var i = 0; i < $scope.quantidade - 1; i++) {
            var novo = "";
            qtde.push(novo);
        }
        window.sessionStorage.setItem("qtde", JSON.stringify(qtde));
        window.location.href = "http://localhost:8084/marmitex/marmitex.html";
    };

    $scope.acrescentar = function () {
        $scope.quantidade++;
    };

    $scope.retirar = function () {
        if ($scope.quantidade > 1) {
            $scope.quantidade--;
        } else
            alert("Quantidade minima de marmitex por pedido é 1!");
    };

});
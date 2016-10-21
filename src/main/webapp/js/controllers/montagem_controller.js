angular.module("app").controller("marmitexCtrl", function ($scope) {                
                $scope.preco = 0;
                $scope.qtdeMarmita = JSON.parse(qtdeMarmitex);
                for (var i = 0; i < $scope.qtdeMarmita.length; i++) {
                    $scope.qtdeMarmita[i] = (i + 1);
                }
                $scope.marmitas = new Array();
                for (var i = 0; i < $scope.qtdeMarmita.length; i++) {
                    $scope.marmitas.push(new Array());
                }


                $scope.consultarTudo = function () {
                    $scope.consultarCategorias();
                    $scope.consultarIngredientes();
                };

                $scope.acrescentar = function (recurso, id) {
                    var r = document.getElementById(recurso.nome + "_" + id);
                    for (var i = 0; i < $scope.bkp_recursos.length; i++) {
                        if ($scope.bkp_recursos[i].id === recurso.id) {
                            if (recurso.medida === "Unidade(s)" && $scope.bkp_recursos[i].quantidade === 1) {
                                alert("Quantidade máxima de " + recurso.nome + " atingida!");
                            } else if (recurso.medida !== "Unidade(s)" && $scope.bkp_recursos[i].quantidade <= 0.150) {
                                alert("Quantidade máxima de " + recurso.nome + " atingida!");
                            } else {
                                r.value++;
                                $scope.preco += recurso.valor;
                                $scope.marmitas[id - 1].push(angular.copy(recurso));
                                if (recurso.medida === "Unidade(s)") {
                                    $scope.bkp_recursos[i].quantidade -= 1;
                                } else {
                                    $scope.bkp_recursos[i].quantidade -= 0.150;
                                }
                            }
                        }
                    }
                };

                $scope.retirar = function (recurso, id) {
                    var r = document.getElementById(recurso.nome + "_" + id);
                    if (r.value > 0) {
                        for (var i = $scope.marmitas[id - 1].length; i >= 0; i--) {
                            for (var j = 0; j < $scope.marmitas[id - 1].length; j++) {
                                if ($scope.marmitas[id - 1][j].id === recurso.id) {
                                    $scope.marmitas[id - 1].splice(j, 1);
                                    r.value--;
                                    $scope.preco -= recurso.valor;
                                    for (var i = 0; i < $scope.bkp_recursos.length; i++) {
                                        if ($scope.bkp_recursos[i].id === recurso.id) {
                                            if (recurso.medida === "Unidade(s)") {
                                                $scope.bkp_recursos[i].quantidade += 1;
                                            } else {
                                                $scope.bkp_recursos[i].quantidade += 0.150;
                                            }
                                            return;
                                        }
                                    }                                    
                                }
                            }
                        }
                    }
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
                            $scope.bkp_recursos = angular.copy($scope.recursos);
                        },
                        error: function (err) {
                            console.log(err.statusText);
                        }
                    });
                };

                $scope.verificar = function () {
                    if ($scope.marmitas.length !== 0) {
                        window.sessionStorage.clear();
                        window.sessionStorage.setItem("marmitex", JSON.stringify($scope.marmitas));
                        window.sessionStorage.setItem("preco", angular.copy($scope.preco));
                    }
                    window.location.href = "http://localhost:8084/marmitex/login.html";
                };

                $scope.clicou = function (recurso) {
                    var r = document.getElementById(recurso.nome);
                    if (r.value !== 0) {
                        ingredientesCheckados.push(recurso);
                        $scope.preco += (recurso.valor * r.value);
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

                $scope.verificarMarmitas = function () {
                    for (var i = 0; i < $scope.marmitas.length; i++) {
                        if ($scope.marmitas[i].length === 0) {
                            return false;
                        }
                    }
                    return true;
                };
            });
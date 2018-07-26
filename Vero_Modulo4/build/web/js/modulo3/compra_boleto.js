var app = angular.module('appCompra_boleto', []);

app.controller('ctrlCompra_boleto', ($scope, $http) => {
    $scope.usuario = {};
    $scope.pagosRestantesList = {};

    $scope.validateLogin = function () {
        let params = "?action=authlogin";
        $http({
            method: 'POST',
            url: 'auth' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }

            let data = response.data;

            if (data.nombre) {
                $scope.usuario = data;
                console.log($scope.usuario);
            } else {
                location.replace("/LibraryLocal/index.jsp");
            }
        });
    };

    $scope.logout = function () {
        let params = "?action=logout";
        $http({
            method: 'POST',
            url: 'auth' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            console.log(response.data);
            $scope.usuario = response.data;
            location.replace("/LibraryLocal/index.jsp");
        });
    };

    $scope.getPagosRestantes = function () {
        var action = 'getPagosRestantesByUsuario';
        $http(
                {
                    method: 'POST',
                    url: 'compra_boleto',
                    data: 'action=' + action,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
                }).then(function successCallback(response) {
            console.log(response);
            $scope.pagosRestantesList = response.data;
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.pagar = function (folio) {
        var action = 'pagar';
        $http(
                {
                    method: 'POST',
                    url: 'compra_boleto',
                    data: 'action=' + action + '&folio=' + folio,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
                }).then(function successCallback(response) {
            console.log(response);
            if (response.data === 'success') {
                swal('Finalizado !', 'Pago realizado con exito.', 'success');
                location.replace('comprobantepagoboleto?folio=' + folio);
            } else {
                swal('Oops !', response.data, 'error');
            }

        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.getComprobantePago = function (folio) {
        var action = 'pagar';
        $http(
                {
                    method: 'POST',
                    url: 'comprobantepagoboleto',
                    data: 'action=' + action + '&folio=' + folio,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
                }).then(function successCallback(response) {
            console.log(response);
            $scope.getPagosRestantes();
        }, function errorCallback(response) {
            $scope.getPagosRestantes();
            console.log(response);
        });
    };

    $scope.validateLogin();
    $scope.getPagosRestantes();
});


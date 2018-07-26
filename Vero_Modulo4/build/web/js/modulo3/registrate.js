var app = angular.module("appRegistrate", []);

app.controller("ctrlRegistrate", function ($scope, $http) {

    $scope.create = function () {
        var action = 'createCliente';
        $http({
            method: 'POST',
            url: 'usuario',
            data: 'action=' + action + '&correo=' + $scope.correo + '&password=' + $scope.password + '&nombre=' + $scope.nombre + '&apaterno=' + $scope.apaterno + '&amaterno=' + $scope.amaterno +
                    '&edad=' + $scope.edad + '&sexo=' + $scope.sexo + '&telefono=' + $scope.telefono + '&calle=' + $scope.calle + '&colonia=' + $scope.colonia + '&municipio=' + $scope.municipio +
                    '&estado=' + $scope.estado + '&credito=' + $scope.credito,
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }).then(function successCallback(response) {
            console.log(response);
            swal("Bienvenido !", 'Haz creado una cuenta satisfactoriamente.', 'success');
            location.replace('index.jsp');
        }, function errorCallback(response) {
            console.log(response);
        });
    };
});
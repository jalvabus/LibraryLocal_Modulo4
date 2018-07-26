var app = angular.module('appMiPerfil', []);

app.controller('ctrlMiPerfil', ($scope, $http) => {
    $scope.usuario = [];

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
                $scope.getUsuarioData();
                console.log($scope.usuario);
            } else {
                location.replace("/LibraryLocal/index.jsp");
            }
        });
    };

    $scope.getUsuarioData = function () {
        var action = 'getUsuarioData';
        $http({
            method: 'POST',
            url: 'usuario',
            data: 'action=' + action + '&id_usuario=' + $scope.usuario.id_Usuario,
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }).then(function successCallback(response) {
            console.log(response);
            $scope.usuario = response.data;

            $scope.correo = $scope.usuario.correo;
            $scope.nombre = $scope.usuario.nombre;
            $scope.apaterno = $scope.usuario.apaterno;
            $scope.amaterno = $scope.usuario.amaterno;
            $scope.sexo = $scope.usuario.sexo;
            $scope.calle = $scope.usuario.calle;
            $scope.colonia = $scope.usuario.colonia;
            $scope.municipio = $scope.usuario.municipio;
            $scope.estado = $scope.usuario.estado;
            $scope.telefono = Number($scope.usuario.telefono);
            $scope.edad = $scope.usuario.edad;

        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.update = function () {
        var action = 'update';
        $http({
            method: 'POST',
            url: 'usuario',
            data: 'action=' + action + '&correo=' + $scope.correo + '&password=' + $scope.password + '&nombre=' + $scope.nombre + '&apaterno=' + $scope.apaterno + '&amaterno=' + $scope.amaterno +
                    '&edad=' + $scope.edad + '&sexo=' + $scope.sexo + '&telefono=' + $scope.telefono + '&calle=' + $scope.calle + '&colonia=' + $scope.colonia + '&municipio=' + $scope.municipio +
                    '&estado=' + $scope.estado + '&id_usuario=' + $scope.usuario.id_Usuario,
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }).then(function successCallback(response) {
            console.log(response);
            swal("Actualizado !", 'Se han actualizado tus datos satisfactoriamente.', 'success');
            $scope.getUsuarioData();
        }, function errorCallback(response) {
            console.log(response);
            swal("Error !", 'Error al actualizar tus datos.', 'error');
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

    $scope.validateLogin();

});


var app = angular.module("appTarjeta", []);

app.controller("ctrlTarjeta", function ($scope, $http) {
    let usuario = [];
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
            console.log(data);
            if (data.nombre) {
                $scope.usuario = data;
            } else {
                location.replace("index.jsp");
            }
            $scope.getCompras();
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
            location.replace("index.jsp");
        });
        $scope.limpiarOrden();
    };

    $scope.listTarjetas = {};

    $scope.getAll = function () {
        var action = 'getAll';
        $http(
                {
                    method: 'POST',
                    url: 'tarjeta',
                    data: 'action=' + action,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
                }).then(function successCallback(response) {
            console.log(response);
            $scope.listTarjetas = response.data;
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.getAll();


    $scope.sendTxtFile = function () {
        var formData = new FormData(document.getElementById("form_file_txt"));
        $.ajax({
            url: "txt_tarjeta",
            type: "post",
            dataType: "html",
            data: formData,
            cache: false,
            contentType: false,
            processData: false
        }).done(function (res) {
            console.log(res);
            $scope.bitacoraTarjetas = JSON.parse(res);
            $('#modal_bitacora').modal('show');
            $scope.getAll();
        });
    };
    $scope.validateLogin();
});
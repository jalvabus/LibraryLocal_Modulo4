var app = angular.module("appTickets", []);

app.controller("ctrlTickets", function ($scope, $http) {

    $scope.listTickets = {};

    $scope.getAll = function () {
        var action = 'getAll';
        $http({
            method: 'POST',
            url: 'tickets',
            data: 'action=' + action,
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }).then(function successCallback(response) {
            console.log(response);
            $scope.listTickets = response.data;
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.getAll();


    $scope.sendTxtFile = function () {
        var formData = new FormData(document.getElementById("form_file_txt"));
        $.ajax({
            url: "txt_ticket",
            type: "post",
            dataType: "html",
            data: formData,
            cache: false,
            contentType: false,
            processData: false
        }).done(function (res) {
            console.log(res);
            $scope.bitacoraTickets = JSON.parse(res);
            $('#modal_bitacora').modal('show');
            $scope.getAll();
        });
    };

});
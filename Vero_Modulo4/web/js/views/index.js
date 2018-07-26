/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app = angular.module('indexApp', []);

app.controller('indexController', ($scope, $http) => {
    $scope.viewLogin = false;
    $scope.usaurio = [];

    $scope.alert = function () {
        $scope.viewLogin = true;
    };

    $scope.cancelar = function () {
        $scope.viewLogin = false;
    };

    $scope.login = function () {
        let usuario = $('#usuario').val();
        let password = $('#password').val();
        let params = "?action=login&usuario=" + usuario + "&password=" + password + "";
        console.log(params);
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
                $scope.usuario = response.data;
                if (data.tipo === 'Admin') {
                    location.replace("PrincipalGerente.jsp");
                } else if (data.tipo === 'Cliente') {
                    location.replace("PrincipalUsuario.jsp");
                }
            } else {
                return alert('Usuario no encontrado');
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
            $scope.usuario = response.data;
            location.replace("index.jsp");
        });
    };

    $scope.eventos = [];

    $scope.getEventos = function () {
        let params = "?action=getEventos";
        $http({
            method: 'POST',
            url: 'evento' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log(data);
            if (data.length > 0) {
                $scope.eventos = data;
            }
        });
    };
    $scope.getEventos();

    $scope.libros = [];

    $scope.getLibros = function () {
        let params = "?action=getLibros";
        $http({
            method: 'POST',
            url: 'libro' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log(data);
            if (data.length > 0) {
                $scope.libros = data;
            }
        });
    };

    $scope.searchLibro = null;

    $scope.getLibrosSearch = function () {
        let params = "?action=busqueda&nombre=" + $scope.searchLibro;
        $http({
            method: 'POST',
            url: 'libro' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            if (data.length > 0) {
                $scope.libros = data;
            }
        });
    };

    $scope.showDetails = false;

    $scope.libro = [];

    $scope.showDetailsBook = function (book) {
        $scope.libro = book;
        $scope.showDetails = true;
    };

    $scope.hideDetailsBook = function (book) {
        $scope.showDetails = false;
    };

    $scope.getLibros();


    $scope.getPassword = function () {
        swal({
            text: 'Ingrese correo para enviar la contraseña',
            content: "input",
            icon: "warning",
            button: {
                text: "Search!",
                closeModal: false,
            }
        }).then(name => {
            console.log(name);
            let params = "?action=getPassword&correo=" + name;
            $http({
                method: 'POST',
                url: 'usuario' + params
            }).then((response, err) => {
                return true;
            });
        }).then(results => {
            swal({
                title: "Password Enviado!",
                text: "Se envio un correo con la contraseña",
                icon: "success",
                button: "Aceptar!",
            });
        })
    };

});
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app = angular.module('miscomprasApp', []);

app.controller('miscomprasController', ($scope, $http) => {

    $scope.usuario = [];
    $scope.compras = [];
    $scope.compra = [];
    $scope.detalles = [];
    $scope.detalleVenta = false;

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

    $scope.getCompras = function () {
        let params = "?action=verMisCompras";
        params += "&id_usuario=" + $scope.usuario.id_Usuario;
        console.log(params);
        $http({
            method: 'POST',
            url: 'usuario' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log(data);
            $scope.compras = data;
        });
    };

    $scope.getDetalleCompras = function (compra) {
        $scope.detalleVenta = true;
        $scope.compra = compra;
        let params = "?action=verDetalleCompra";
        params += "&id_usuario=" + $scope.usuario.id_Usuario
                + "&id_venta=" + compra.codigo_compra;
        console.log(params);
        $http({
            method: 'POST',
            url: 'usuario' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }

            let data = response.data;
            console.log(data);
            $scope.detalles = data;
        });
    };

    $scope.detalle = [];

    $scope.regresar = function () {
        $scope.detalleVenta = false;
    };

    $scope.validateLogin();

    /*
     * Devolucion de libro
     * Aqui haz tu desmadre queso para devolver el libro
     * Que apareza en el modal eso de si es por maltrato o la verga xD
     */

    $scope.libro = [];

    $scope.regresarLibro = function (libro) {
        $scope.libro = libro;
    };

    $scope.finalizarDevolucion = function (detalle, compra, usuario) {
        detalle = $scope.libro;
        let tipoD = $("#tipoDevolucion").val();
        let motivo = $("#motivo").val();
        var fechaActual = new Date();
        var dd = fechaActual.getDate();
        var mm = fechaActual.getMonth() + 1; //hoy es 0!
        var yyyy = fechaActual.getFullYear();
        if (dd < 10) {
            dd = '0' + dd
        }
        if (mm < 10) {
            mm = '0' + mm
        }
        fechaActual = yyyy + '-' + mm + '-' + dd;
        if (tipoD == "" && motivo == "") {
            swal("Completa la informacion que se solicita", {
                icon: "warning",
            });
        } else {
            let params = "?action=finalizarDevolucion&tipoD=" + tipoD
                    + "&motivo=" + motivo
                    + "&fecha=" + fechaActual
                    + "&subtotal=" + detalle.subtotal
                    + "&tipo_pago=" + compra.tipo_pago
                    + "&codigo=" + compra.codigo_compra
                    + "&id_usuario=" + usuario.id_Usuario
                    + "&id_detalle=" + detalle.id_detalleVenta
                    + "&id_libro=" + detalle.id_libro
                    + "&nombre=" + detalle.nombre
                    + "&correo=" + usuario.correo
                    + "&cantidad=" + compra.cantidadLibros
                    + "&cantidadC=" + detalle.cantidad;
            $http({
                method: 'POST',
                url: 'devoluciones' + params
            }).then((response, err) => {
                console.log(response.data);
                let data = response.data;
                if (Number(response.data) == 1) {
                    swal("Ocurrio un ERROR al realizar la Devolucion", {
                        icon: "warning",
                    });
                } else {
                    swal(data, {
                        icon: "success",
                    });
                    $scope.getDetalleCompras(compra);
                    $scope.getCompras();
                    $scope.limpiarDevolucion();
                }
            });
        }
    };
    
    $scope.limpiarDevolucion = function () {
        let tipoD = $("#tipoDevolucion").val("");
        let motivo = $("#motivo").val("");
    };

});
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global swal */

let app = angular.module('carritoApp', []);

app.controller('carritoController', ($scope, $http) => {

    $scope.usuario = [];
    $scope.showTable = false;
    $scope.datosCorrectos = false;
    $scope.correcto = false;
    $scope.incorrecto = false;
    $scope.tipopago = null;
    $scope.tipoCompra = null;
    $scope.datosRegalo = [];
    $scope.saldo = 0;
    $scope.tarjeta = null;
    $scope.end = false;
    $scope.sugerencia = false;
    $scope.orden = [];
    $scope.precio = [];

    $scope.resetVariables = function () {
        $scope.showTable = false;
        $scope.datosCorrectos = false;
        $scope.correcto = false;
        $scope.incorrecto = false;
        $scope.tipopago = null;
        $scope.tipoCompra = null;
        $scope.datosRegalo = [];
        $scope.saldo = 0;
        $scope.tarjeta = "0";
        $scope.end = false;
        $scope.sugerencia = false;
        $scope.orden = [];
        $scope.precio = [];
    };

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

    $scope.cargarDatos = function () {

        if (!localStorage.getItem("orden")) {
            localStorage.setItem("orden", JSON.stringify({libros: []}));
        }

        var total = 0;

        let books = JSON.parse(localStorage.getItem("orden"));
        $scope.orden = books.libros;

        $scope.orden.forEach(function (p) {
            total += p.cantidadCarrito * p.precio;
        });

        $scope.precio.total = total.toFixed(2);
        console.log($scope.orden);
    };

    $scope.limpiarOrden = function () {
        localStorage.setItem("orden", JSON.stringify({libros: []}));
        $scope.orden = [];
        $scope.precio.total = 0.0;
    };
    
    $scope.reCalcular = function (libro) {

        let valor = document.getElementById(libro.id_libro + 'libro').value;
        var total = 0;
        var ordenActual = JSON.parse(localStorage.getItem("orden"));

        ordenActual.libros.forEach((elemento, i) => {
            if (libro.id_libro === elemento.id_libro) {
                ordenActual.libros[i].cantidadCarrito = Number(valor);
            }
        });

        ordenActual.libros.forEach(function (p) {
            total += p.precio * parseInt(p.cantidadCarrito);
        });

        localStorage.setItem("orden", JSON.stringify(ordenActual));

        $scope.orden = ordenActual.libros;

        total1 = total.toFixed(2);

        $scope.precio.total = total1;

    };

    $scope.eliminar = function (libro) {

        if (!localStorage.getItem("orden")) {
            localStorage.setItem("orden", JSON.stringify({libros: []}));
        }

        var total = 0;

        var ordenActual = JSON.parse(localStorage.getItem("orden"));

        let nuevaOrden = {
            libros: []
        };

        ordenActual.libros.forEach(function (p) {
            if (p.id_libro !== libro.id_libro) {
                total += p.cantidadCarrito * p.precio;
                nuevaOrden.libros.push(p);
            }
        });

        localStorage.setItem("orden", JSON.stringify(nuevaOrden));

        $scope.orden = nuevaOrden.libros;
        total1 = total.toFixed(2);

        $scope.precio.total = total1;
    };

    $scope.finalizarCompra = function () {
        let compra = null;
        swal({
            title: "Tipo de compra",
            text: "¿Que tipo de compra realzará?",
            icon: "warning",
            buttons: {
                cancel: "Propia",
                catch : {
                    text: "Regalo",
                    value: "catch"
                }
            },
            dangerMode: true
        }).then((value) => {
            switch (value) {
                case "catch":
                    compra = 'regalo';
                    swal("Gracias!", "Se realizara una compra para regalo", "success");
                    return true;
                    break;
                default:
                    compra = 'propia';
                    swal("Gracias!", "Se realizara una compra propia", "success");
                    return true;
            }
        }).then(() => {
            if ($scope.precio.total < 600) {

                $scope.precio.envio = 100;

                let total = $scope.precio.total;
                let envio = $scope.precio.envio;

                $scope.precio.total = (Number(total));
                $scope.precio.iva = ((Number(total)) + Number(envio)) * 0.16;
                $scope.precio.totalCompra = (Number(total)) + Number(envio) + Number($scope.precio.iva);

                return true;
            } else {
                $scope.precio.envio = 0;

                let total = $scope.precio.total;
                let envio = $scope.precio.envio;

                $scope.precio.total = (Number(total));
                $scope.precio.iva = ((Number(total)) + Number(envio)) * 0.16;
                $scope.precio.totalCompra = (Number(total)) + Number(envio) + Number($scope.precio.iva);

                return true;
            }
        }).then(() => {
            $scope.verTabla(compra);
        });
    };

    $scope.cancelar = function () {
        $scope.showTable = false;
        $scope.end = false;
    };

    $scope.verTabla = function (compra) {
        $scope.showTable = true;
        $scope.tipoCompra = compra;
        $scope.$apply();
    };

    $scope.compraConcretada = function () {
        $scope.correcto = true;
    };

    $scope.backCarrito = function () {
        $scope.correcto = false;
    };

    $scope.verificacionTarjeta = false;

    $scope.getSaldoTarjeta = function () {
        let noTarjeta = document.getElementById('saldoTarjeta').value;

        let e = document.getElementById("tipopago");
        let tipoPago = e.options[e.selectedIndex].value;
        $scope.tipopago = tipoPago;
        $scope.tarjeta = noTarjeta;

        let action = null;
        console.log(tipopago);

        if (tipoPago === 'credito') {
            action = "getSaldoCredito";
        }
        if (tipoPago === 'prepago') {
            action = "getSaldoPrepago";
        }

        let params = "?action=" + action + "&id_usuario= " + $scope.usuario.id_Usuario + "&no_tarjeta=" + noTarjeta;
        console.log(params);
        $http({
            method: 'POST',
            url: 'tarjetas' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }

            let data = response.data;
            console.log(data);
            if (tipoPago === 'prepago') {
                if (data.saldo) {
                    $scope.saldo = data.saldo;
                    $scope.end = true;
                    if (data.saldo < $scope.precio.totalCompra) {
                        swal({
                            title: "No centa con saldo suficiente!",
                            text: "",
                            icon: "warning",
                            button: "Ok!"
                        });
                        $scope.verificacionTarjeta = false;
                    } else {
                        swal({
                            title: "Verifique sus datos y confirme la compra!",
                            text: "¿Datos correctos?",
                            icon: "warning",
                            button: "Ok!"
                        });
                        $scope.verificacionTarjeta = true;
                    }
                } else {
                    swal({
                        title: "Error en la tajeta!",
                        text: "No se encontraron resultados!",
                        icon: "warning",
                        button: "Ok!"
                    });
                }
            }
            if (tipoPago === 'credito') {
                if (data.saldo) {
                    $scope.verificacionTarjeta = true;
                    $scope.saldo = data.saldo;
                    $scope.end = true;
                    swal({
                        title: "Verifique sus datos y confirme la compra!",
                        text: "El pago se cargara a su tarjeta de credito",
                        icon: "warning",
                        button: "Ok!"
                    });
                } else {
                    swal({
                        title: "Error en la tajeta!",
                        text: "No se encontraron resultados!",
                        icon: "warning",
                        button: "Ok!"
                    });
                }
            }
        });
    };

    $scope.verSugerencia = function () {
        $scope.sugerencia = true;
    };

    $scope.resetIndex = function () {
        swal({
            title: "Gracias por su sugerencia !",
            text: "Siga visitando nuestro sitio web !",
            icon: "success",
            button: "OK"
        }).then((value) => {
            location.replace('PrincipalUsuario.jsp');
        });
    };

    $scope.sug = [];

    $scope.registrarSugerencia = function () {
        let params = "?action=create";
        params += "&libro=" + $scope.sug.nombre
                + "&autor=" + $scope.sug.autor
                + "&editorial=" + $scope.sug.editorial
                + "&id_usuario=" + $scope.usuario.id_Usuario;
        console.log(params);
        $http({
            method: 'POST',
            url: 'sugerencia' + params
        }).then((response, err) => {
            if (err) {
                console.log(err);
            } else {
                let data = response.data;
                console.log(data);
                $scope.resetIndex();
            }
        });
    };

    $scope.cargarDatos();
    $scope.validateLogin();

    /**
     * 
     * @returns {undefined}
     * CAMBIOS V3.1
     */
    $scope.getDatosTarjeta = function () {
        console.log("Obteniendo datos de la tarjeta");

        let e = document.getElementById("tipopago");
        let tipoPago = e.options[e.selectedIndex].value;
        $scope.tipopago = tipoPago;
        console.log(tipoPago);
        let action = "getTarjeta";
        $http({
            method: 'POST',
            url: 'tarjetas',
            data: 'action=' + action + "&tipo=" + tipoPago + "&id_usuario=" + $scope.usuario.id_Usuario,
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }).then((response, err) => {
            if (err) {
                console.log(err);
            } else {
                let data = response.data;
                console.log(data);
                //$scope.tarjeta = data;

                if (tipoPago === 'prepago') {
                    if (data.saldo) {
                        $scope.saldo = data.saldo;
                        $scope.end = true;
                        $scope.tarjeta = data.codigo_tarjeta;
                        if (data.saldo < $scope.precio.totalCompra) {
                            swal({
                                title: "No centa con saldo suficiente!",
                                text: "",
                                icon: "warning",
                                button: "Ok!"
                            });
                            $scope.verificacionTarjeta = false;
                        } else {
                            swal({
                                title: "Verifique sus datos y confirme la compra!",
                                text: "¿Datos correctos?",
                                icon: "warning",
                                button: "Ok!"
                            });
                            $scope.verificacionTarjeta = true;
                        }
                    } else {
                        swal({
                            title: "Error en la tajeta!",
                            text: "No se encontraron resultados!",
                            icon: "warning",
                            button: "Ok!"
                        });
                        $scope.verificacionTarjeta = false;
                    }
                }
                if (tipoPago === 'credito') {
                    if (data.saldo) {
                        $scope.verificacionTarjeta = true;
                        $scope.saldo = data.saldo;
                        $scope.end = true;
                        $scope.tarjeta = data.codigo_tarjetacredito;
                        swal({
                            title: "Verifique sus datos y confirme la compra!",
                            text: "El pago se cargara a su tarjeta de credito",
                            icon: "warning",
                            button: "Ok!"
                        });
                        $scope.verificacionTarjeta = true;
                    } else {
                        swal({
                            title: "Error en la tajeta!",
                            text: "No se encontraron resultados!",
                            icon: "warning",
                            button: "Ok!"
                        });
                        $scope.verificacionTarjeta = false;
                    }
                }
            }
        });
    };

    $scope.registrarCompra = function () {

        let tarjeta = $scope.tarjeta;
        let nuevoSaldo = Number($scope.saldo) - Number($scope.precio.totalCompra);
        let params = "?action=finalizarCompra&id_usuario=" + $scope.usuario.id_Usuario
                + "&tipoCompra=" + $scope.tipoCompra
                + "&total=" + $scope.precio.totalCompra
                + "&tipotarjeta=" + $scope.tipopago
                + "&notarjeta=" + tarjeta
                + "&nuevoSaldo=" + nuevoSaldo
                + "&cantidadLibros=" + $scope.orden.length;
        if ($scope.tipoCompra === 'regalo') {
            params += "&tarjetaFelicitacion=" + $scope.datosRegalo.tarjeta
                    + "&envoltura=" + $scope.datosRegalo.envoltura
                    + "&correo=" + $scope.datosRegalo.correo
                    + "&direccion=" + $scope.datosRegalo.direccion
                    + "&mensaje=" + $scope.datosRegalo.mensaje;
        }
        let paramsBook = "";
        $scope.orden.forEach((librito, i) => {
            paramsBook += "&idBook" + i + "=" + librito.id_libro + "&cantidad" + i + "=" + librito.cantidadCarrito;
        });
        params += paramsBook;
        console.log(params);
        $http({
            method: 'POST',
            url: 'usuario' + params
        }).then((response, err) => {
            let data = response.data;
            console.log(data);
            swal({
                title: "Compra concretada",
                text: "Espere en lo que se genera su ticket de compra",
                icon: "success",
                button: "OK"
            });
            $scope.limpiarOrden();
            $scope.cancelar();
            $scope.verSugerencia();
            $scope.resetVariables();
            $scope.sugerencia = true;
        });
    };

});

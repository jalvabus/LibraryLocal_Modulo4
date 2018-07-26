var app = angular.module('appWishlist', []);

app.controller('ctrlWishlist', ($scope, $http) => {
    $scope.usuario = {};
    $scope.listlibros = {};
    $scope.wishlist = {};
    $scope.sharedwishlist = {};
    $scope.sharedwishlistbyusuario = {};

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

    $scope.update = function () {
        var action = 'update';
        $http(
                {
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

    $scope.getListLibros = function () {
        var action = 'getLibros';
        $http(
                {
                    method: 'POST',
                    url: 'libro',
                    data: 'action=' + action,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
                }).then(function successCallback(response) {
            console.log(response);
            $scope.listlibros = response.data;
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.getWishList = function () {
        var action = 'getWishlist';
        $http(
                {
                    method: 'POST',
                    url: 'wishlist',
                    data: 'action=' + action,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
                }).then(function successCallback(response) {
            console.log(response);
            $scope.wishlist = response.data;
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.addToWishlist = function (id_libro) {
        var action = 'addToWishlist';
        if (Number($scope.wishlist.length) <= 10) {
            $http({
                method: 'POST',
                url: 'wishlist',
                data: 'action=' + action + '&id_libro=' + id_libro,
                headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
            }).then(function successCallback(response) {
                console.log(response);
                swal({
                    title: "Agreagado a tu lista",
                    text: "Se agrego el libro a tu listas",
                    icon: "success",
                    button: "Aceptar"
                });
                $scope.wishlist = response.data;
                $scope.getWishList();
                $scope.reload();
            }, function errorCallback(response) {
                console.log(response);
            });
        } else {
            swal('Oops !', "No puede agregar a la wishlist, el maximo son 10 libros", 'error');
        }
    };

    $scope.getSharedWishList = function () {
        var action = 'getSharedWishlist';
        $http(
                {
                    method: 'POST',
                    url: 'wishlist',
                    data: 'action=' + action,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
                }).then(function successCallback(response) {
            console.log(response);
            $scope.sharedwishlist = response.data;
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.shareWishlist = function () {
        var action = 'shareWishlist';
        if (Number($scope.wishlist.length) !== 10) {
            swal('Oops !', "No puede compartir la wishlist, debe tener 10 libros", 'error');
        } else {
            $http({
                method: 'POST',
                url: 'wishlist',
                data: 'action=' + action + '&correo=' + $scope.share.correo,
                headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
            }).then(function successCallback(response) {
                console.log(response);
                if (response.data === 'success') {
                    swal('Finalizado !', 'Se ha compartido exitosamente tu lista de deseos con ' + $scope.share.correo + '.', 'success');
                } else {
                    swal('Oops !', response.data, 'error');
                }
            }, function errorCallback(response) {
                console.log(response);
            });
        }
    };

    $scope.getSharedWishListByIdUsuario = function (id_usuario) {
        $scope.sharedwishlistbyusuario = {};
        var action = 'SharedWishListByIdUsuario';
        $http(
                {
                    method: 'POST',
                    url: 'wishlist',
                    data: 'action=' + action + '&id_usuario=' + id_usuario,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
                }).then(function successCallback(response) {
            console.log(response);
            $scope.sharedwishlistbyusuario = response.data;
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    /**
     * New Options
     */

    $scope.removeToWishList = function (libro) {
        var action = 'removeToWishlist';
        $http({
            method: 'POST',
            url: 'wishlist',
            data: 'action=' + action + '&id_libro=' + libro.id_libro,
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }).then(function successCallback(response) {
            console.log(response);
            swal({
                title: "Removido de tu lista",
                text: "Se removio el libro de tu lista",
                icon: "success",
                button: "Aceptar"
            });
            $scope.getWishList();
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    /**
     * Compra de libro
     */
    $scope.libro = [];
    $scope.datosRegalo = [];

    $scope.comprarLibro = function (opcion, libro) {
        if (opcion === "comprar") {

            let tarjeta = $scope.tarjeta;
            let nuevoSaldo = Number($scope.saldo) - Number($scope.libro.precio);


            var action = 'compraWishList';
            var params = '?action=' + action
                    + '&id_libro=' + $scope.libro.id_libro
                    + "&notarjeta=" + tarjeta
                    + "&nuevoSaldo=" + nuevoSaldo
                    + "&tipotarjeta=" + $scope.tipopago;

            if ($scope.datosRegalo.opcion === 'si') {
                params += "&tipoCompra=regalo"
                        + "&tarjetaFelicitacion=" + $scope.datosRegalo.tarjeta
                        + "&envoltura=" + $scope.datosRegalo.envoltura
                        + "&correo=" + $scope.datosRegalo.correo
                        + "&direccion=" + $scope.datosRegalo.direccion
                        + "&mensaje=" + $scope.datosRegalo.mensaje;
            } else {
                params += "&tipoCompra=null";
            }
            console.log(params);

            $http({
                method: 'POST',
                url: 'usuario' + params
            }).then(function successCallback(response) {
                console.log(response);
                swal({
                    title: "Libro comprado",
                    text: "Ve a la seccion de tus compras",
                    icon: "success",
                    button: "Aceptar"
                });
                $scope.getWishList();
                $scope.reload();
            }, function errorCallback(response) {
                console.log(response);
            });
        }
        if (opcion === "modal") {
            $scope.libro = libro;
            console.log(libro);
        }
    };

    /**
     * 
     * @returns {undefined}
     * CAMBIOS V3.1
     */
    $scope.verificacionTarjeta = false;
    $scope.tipopago = null;
    $scope.tarjeta = [];
    $scope.saldo = null;

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
                        $scope.tarjeta = data.codigo_tarjeta;
                        if (data.saldo < $scope.libro.precio) {
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
                        $scope.end = true;
                        $scope.tarjeta = data.codigo_tarjetacredito;
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
                        $scope.verificacionTarjeta = false;
                    }
                }
            }
        });
    };

    $scope.whatClassIsIt = function (libro) {
        let cont = 0;
        $scope.mislibros.forEach((librito) => {
            if (libro.nombre === librito.nombre)
                cont++;
        });
        if (cont > 0) {
            return "list-group-item ClassA";
        } else {
            return "list-group-item";
        }
    };

    $scope.mislibros = [];

    $scope.getBooksBuy = function () {
        var action = 'getLibrosComprados';
        $http({
            method: 'POST',
            url: 'libro',
            data: 'action=' + action,
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }).then(function successCallback(response) {
            console.log("Mis libros comprados");
            console.log(response);
            console.log("Mis libros comprados");
            $scope.mislibros = response.data;
        }, function errorCallback(response) {
            console.log(response);
        });
    };


    $scope.getBooksBuy();
    $scope.validateLogin();
    $scope.getListLibros();
    $scope.getWishList();
    $scope.getSharedWishList();

    $scope.reload = function () {
        $scope.getBooksBuy();
        $scope.validateLogin();
        $scope.getListLibros();
        $scope.getWishList();
        $scope.getSharedWishList();
    };
    
    $scope.seeBook = function (libro) {
        $scope.libro = libro;
    };

});




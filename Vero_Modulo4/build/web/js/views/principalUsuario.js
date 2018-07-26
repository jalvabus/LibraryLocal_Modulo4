/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global swal */

let app = angular.module('principalApp', []);

app.controller('principalController', ($scope, $http) => {
    $scope.usuario = [];
    $scope.precio = [];

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
                $scope.getMisEventos();
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
    };

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

    $scope.hideDetailsBook = function () {
        $scope.showDetails = false;
    };

    $scope.agregarCarrito = function (libro) {
        console.log(libro);
        let existe = -1;
        if (!localStorage.getItem("orden")) {
            localStorage.setItem('orden', JSON.stringify({libros: []}));
        }

        var ordenActual = JSON.parse(localStorage.getItem("orden"));

        ordenActual.libros.forEach((elemento, i) => {
            if (libro.id_libro === elemento.id_libro) {
                existe = i;
            }
        });

        if (existe === -1) {
            libro.cantidadCarrito = 1;
            ordenActual.libros.push(libro);
        } else {
            ordenActual.libros[existe].cantidadCarrito++;
        }

        localStorage.setItem("orden", JSON.stringify(ordenActual));
        console.log(ordenActual);

        swal("Agregaste '" + libro.nombre + "' a tu carrito !", "", "success");
    };

    $scope.limpiarOrden = function () {
        localStorage.setItem("orden", JSON.stringify({libros: []}));
    };

    $scope.validateLogin();
    $scope.getLibros();
    // $scope.limpiarOrden();

    /**
     * Gestion del modulo 4
     */
    $scope.premios = [];

    $scope.getPremios = function () {
        let params = "?action=getPremiosByStatus";
        $http({
            method: 'POST',
            url: 'premio' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log(data);
            if (data.length > 0) {
                $scope.premios = data;
            }
        });
    };

    $scope.showDetailsP = false;

    $scope.premio = [];

    $scope.showDetailsPrize = function (prize) {
        $scope.premio = prize;
        $scope.showDetailsP = true;
    };

    $scope.hideDetailsPrize = function (prize) {
        $scope.showDetailsP = false;
    };

    $scope.getPremios();

    $scope.mypremios = [];

    $scope.getMyPremios = function () {
        let params = "?action=getMyPremios";
        $http({
            method: 'POST',
            url: 'premio' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log("**** MIS PREMIOS **");
            console.log(data);
            if (data.length > 0) {
                $scope.mypremios = data;
            }
        });
    };

    $scope.mypremio = [];

    $scope.getMyPremios();

    $scope.cambiarPremio = function (premio, usuario) {
        swal({
            title: "Desea Obtener el premio " + premio.nombre + "?",
            text: "Premios en existencia: " + premio.cantidad,
            icon: "info",
            buttons: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                var cantidad = parseInt(premio.cantidad);
                var tot = cantidad - 1;
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
                let params = "?action=changePrize&id=" + premio.id_premio
                        + "&cantidad=" + tot
                        + "&puntos=" + premio.puntos
                        + "&correo=" + usuario.correo
                        + "&fecha=" + fechaActual
                        + "&nombreP=" + premio.nombre
                        + "&desc=" + premio.descripcion;
                $http({
                    method: 'POST',
                    url: 'premio' + params
                }).then((response, err) => {
                    console.log(response.data);
                    let data = response.data;
                    if (Number(response.data) == 1) {
                        swal({
                            text: "El premio se ha AGOTADO",
                            icon: "warning",
                        });
                    } else if (Number(response.data) == 2) {
                        swal({
                            text: "Puntos Insuficientes",
                            icon: "warning",
                        });
                    } else if (Number(response.data) == 3) {
                        swal({
                            text: "Ocurrio un error al canjear el premio",
                            icon: "warning",
                        });
                    } else {
                        swal(data, {
                            icon: "success",
                        });
                        $scope.getMyPremios();
                        $scope.getPremios();
                    }
                });
            } else {
                swal("Premio no canjeado");
            }
        });
    };

    $scope.registrarTicket = function (usuario) {
        let codigo = $("#codigo").val();

        let params = "?action=getMontoPuntos&codigo=" + codigo
                + "&correo=" + usuario.correo;
        $http({
            method: 'POST',
            url: 'premio' + params
        }).then((response, err) => {
            console.log(response.data);
            let data = response.data;
            if (Number(response.data) === 1) {
                swal({
                    text: "El ticket No existe",
                    icon: "warning",
                });
            } else if (Number(response.data) === 2) {
                swal({
                    text: "El ticket ya fue registrado",
                    icon: "warning",
                });
            } else {
                swal(data, {
                    icon: "success",
                });
            }
        });
    };

    $scope.tarjetas = [];
    $scope.mostrarTarjetas = true;
    $scope.mostrarTarjetas2 = false;

    $scope.getTarjetas = function () {
        let params = "?action=getTarjetas";
        $http({
            method: 'POST',
            url: 'tarjeta' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            if (response.data === "") {
                $scope.mostrarTarjetas2 = true;
                $scope.mostrarTarjetas = false;
                $scope.getTarjetaPrepago();
                $scope.getTarjetaCredito();
            } else {
                let data = response.data;
                console.log(data);
                if (data.length > 0) {
                    $scope.tarjetas = data;
                }
            }

        });
    };

    $scope.getTarjetas();

    $scope.tarjetasP = [];

    $scope.getTarjetaPrepago = function () {
        let params = "?action=getTarjetaPrepago";
        $http({
            method: 'POST',
            url: 'tarjeta' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log(data);
            if (data.length > 0) {
                $scope.tarjetasP = data;
            }

        });
    };

    $scope.getTarjetaPrepago();

    $scope.tarjetasC = [];

    $scope.getTarjetaCredito = function () {
        let params = "?action=getTarjetaCredito";
        $http({
            method: 'POST',
            url: 'tarjeta' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log(data);
            if (data.length > 0) {
                $scope.tarjetasC = data;
            }

        });
    };

    $scope.getTarjetaCredito();

    $scope.comprarTarjeta = function (usuario, tarjeta) {

        let params = "?action=buyTarjeta&id=" + usuario.id_Usuario
                + "&idTarjeta=" + tarjeta.idTarjeta
                + "&codigo_tarjeta=" + tarjeta.noTarjeta
                + "&saldo=" + tarjeta.costo;
        console.log(params);
        $http({
            method: 'POST',
            url: 'tarjeta' + params
        }).then((response, err) => {
            console.log(response.data);
            let data = response.data;
            if (response.data === "") {
                swal({
                    title: "Compra No realizada!",
                    text: "No se pudo realizar la compra!",
                    icon: "warning",
                });
            } else if (Number(response.data) === 1) {
                swal({
                    title: "Compra No realizada!",
                    text: "Su saldo es insuficiente",
                    icon: "warning",
                });
            } else {
                swal({
                    title: "Compra Realizada!",
                    text: data,
                    icon: "success",
                });
                $scope.mostrarTarjetas = false;
                $scope.mostrarTarjetas2 = true;
                $scope.getTarjetaPrepago();
                $scope.getTarjetaCredito();
            }
        });
    };

    $scope.recargarSaldoYo = function (usuario, tarjetaP) {
        swal({
            title: 'Selecciona para quien es la recarga de saldo',
            buttons: {
                cancel: "Cancelar",
                catch : {
                    text: "Regalo",
                    value: "regalo",
                },
                Yo: true,
            },
        }).then((value) => {
            switch (value) {
                case "Yo":
                    swal({
                        title: "Ingresa la Informacion",
                        content: {
                            element: "input",
                            attributes: {
                                placeholder: "Cantidad de Saldo",
                                type: "number",
                                min: "1",
                            },
                        },
                        icon: "info",
                        buttons: true,
                        closeModal: false,
                    }).then((cantidad) => {
                        if (cantidad > 0) {
                            let params = "?action=recargarSaldoParaMi&id=" + usuario.id_Usuario
                                    + "&saldo=" + tarjetaP.saldo
                                    + "&recarga=" + cantidad;
                            $http({
                                method: 'POST',
                                url: 'tarjeta' + params
                            }).then((response, err) => {
                                console.log(response.data);
                                let data = response.data;
                                if (Number(response.data) == 1) {
                                    swal({
                                        text: "El saldo de tu tarjeta es insuficiente",
                                        icon: "warning",
                                    });
                                } else if (Number(response.data) == 2) {
                                    swal({
                                        text: "Ocurrio un error al realizar el servicio",
                                        icon: "warning",
                                    });
                                } else {
                                    swal({
                                        title: "Recarga completada",
                                        text: data,
                                        icon: "warning",
                                    });
                                    $scope.getTarjetaPrepago();
                                    $scope.getTarjetaCredito();
                                }
                            });
                        } else {
                            swal({
                                text: "Ingresa una cantidad mayor a 0",
                                icon: "warning",
                            });
                        }
                    });
                    break;

                case "regalo":
                    swal({
                        title: "Ingresa la Informacion",
                        content: {
                            element: "input",
                            attributes: {
                                placeholder: "Cantidad de Saldo",
                                type: "number",
                                min: "1",
                            },
                        },
                        icon: "info",
                        buttons: true,
                    }).then((cantidad) => {
                        if (cantidad > 0) {
                            swal({
                                title: "Ingresa la Informacion",
                                content: {
                                    element: "input",
                                    attributes: {
                                        placeholder: "Correo de la persona",
                                        type: "email",
                                    },
                                },
                                icon: "info",
                                buttons: true,
                            }).then((mail) => {
                                if (mail == "") {
                                    swal({
                                        text: "Ingresa un email para notificarle a la otra persona",
                                        icon: "warning",
                                    });
                                } else {
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
                                    let params = "?action=recargarSaldoRegaloP&id=" + usuario.id_Usuario
                                            + "&saldo=" + tarjetaP.saldo
                                            + "&recarga=" + cantidad
                                            + "&correo=" + mail
                                            + "&nombre=" + usuario.nombre + " " + usuario.apaterno + " " + usuario.amaterno
                                            + "&fecha=" + fechaActual;
                                    $http({
                                        method: 'POST',
                                        url: 'tarjeta' + params
                                    }).then((response, err) => {
                                        console.log(response.data);
                                        let data = response.data;
                                        if (Number(response.data) == 3) {
                                            swal({
                                                text: "El saldo de tu tarjeta es insuficiente",
                                                icon: "warning",
                                            });
                                        } else if (Number(response.data) == 4) {
                                            swal({
                                                text: "El correo que ingresaste no esta registrado",
                                                icon: "warning",
                                            });
                                        } else if (Number(response.data) == 5) {
                                            swal({
                                                text: "Ocurrio un error al realizar el servicio",
                                                icon: "warning",
                                            });
                                        } else if (Number(response.data) == 6) {
                                            swal({
                                                text: "No puedes registrar tu correo para recibir una tarjeta de regalo",
                                                icon: "warning",
                                            });
                                        } else if (Number(response.data) == 7) {
                                            swal({
                                                text: "El usuario al que quiere enviarle la tarjeta de regalo\nNo tiene Tarjeta Prepago",
                                                icon: "warning",
                                            });
                                        } else {
                                            swal({
                                                title: "Recarga completada",
                                                text: data,
                                                icon: "success",
                                            });
                                            $scope.getTarjetaPrepago();
                                        }
                                    });
                                }
                            });
                        } else {
                            swal({
                                text: "Ingresa una cantidad mayor a 0",
                                icon: "warning",
                            });
                        }
                    });
                    break;

                default:
                    swal.close();
            }
        });
    };

    $scope.recargarSaldoRegalo = function (usuario, tarjetaC) {
        swal({
            title: "Ingresa la Informacion",
            content: {
                element: "input",
                attributes: {
                    placeholder: "Cantidad de Saldo",
                    type: "number",
                    min: "1",
                },
            },
            icon: "info",
            buttons: true,
        }).then((cantidad) => {
            if (cantidad > 0) {
                swal({
                    title: "Ingresa la Informacion",
                    content: {
                        element: "input",
                        attributes: {
                            placeholder: "Correo de la persona",
                            type: "email",
                        },
                    },
                    icon: "info",
                    buttons: true,
                }).then((mail) => {
                    if (mail == "") {
                        swal({
                            text: "Ingresa un email para notificarle a la otra persona",
                            icon: "warning",
                        });
                    } else {
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
                        let params = "?action=recargarSaldoRegaloC&id=" + usuario.id_Usuario
                                + "&saldo=" + tarjetaC.saldo
                                + "&recarga=" + cantidad
                                + "&correo=" + mail
                                + "&nombre=" + usuario.nombre + " " + usuario.apaterno + " " + usuario.amaterno
                                + "&fecha=" + fechaActual;
                        $http({
                            method: 'POST',
                            url: 'tarjeta' + params
                        }).then((response, err) => {
                            console.log(response.data);
                            let data = response.data;
                            if (Number(response.data) == 3) {
                                swal({
                                    text: "El saldo de tu tarjeta es insuficiente",
                                    icon: "warning",
                                });
                            } else if (Number(response.data) == 4) {
                                swal({
                                    text: "El correo que ingresaste no esta registrado",
                                    icon: "warning",
                                });
                            } else if (Number(response.data) == 5) {
                                swal({
                                    text: "Ocurrio un error al realizar el servicio",
                                    icon: "warning",
                                });
                            } else if (Number(response.data) == 6) {
                                swal({
                                    text: "No puedes registrar tu correo para recibir una tarjeta de regalo",
                                    icon: "warning",
                                });
                            } else {
                                swal({
                                    title: "Recarga completada",
                                    text: data,
                                    icon: "success",
                                });
                                $scope.getTarjetaCredito();
                            }
                        });
                    }
                });
            } else {
                swal({
                    text: "Ingresa una cantidad mayor a 0",
                    icon: "warning",
                });
            }
        });
    };

    $scope.consultarCodigo = function () {
        let codigo = $("#codigoD").val();
        let params = "?action=consultarCodigo&codigo=" + codigo;
        $http({
            method: 'POST',
            url: 'devolucion' + params
        }).then((response, err) => {
            console.log(response.data);
            let data = response.data;
            if (response.data == "") {
                swal("Completa la siguiente Informacion", {
                    icon: "info",
                });
                $scope.mostrarSeccionDev = false;
                $scope.mostrarFormularioD = true;
            } else {
                swal(data, {
                    icon: "warning",
                });
            }
        });
    };

    $scope.mostrarFormularioD = false;
    $scope.mostrarSeccionDev = true;

    $scope.cancelD = function () {
        $scope.mostrarSeccionDev = true;
        $scope.mostrarFormularioD = false;
        $scope.mostrarLibrosCompra = false;
    };

    $scope.devolucionLibros = function () {
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
        if (tipoD == "" || motivo == "") {

        } else {
            let params = "?action=devolucionLibros&tipoD=" + tipoD
                    + "&motivo=" + motivo
                    + "&fecha=" + fechaActual;
            $http({
                method: 'POST',
                url: 'devolucion' + params
            }).then((response, err) => {
                console.log(response.data);
                let data = response.data;
                if (response.data == "") {
                    swal("Selecciona un libro", {
                        icon: "info",
                    });
                    $scope.mostrarLibrosCompra = true;
                    $scope.mostrarFormularioD = false;
                    $scope.getDetalleVenta();
                } else {
                    swal(data, {
                        icon: "success",
                    });
                    $scope.mostrarSeccionDev = true;
                    $scope.mostrarFormularioD = false;
                }
            });
        }
    };

    $scope.mostrarLibrosCompra = false;

    $scope.detalles = [];

    $scope.getDetalleVenta = function () {
        let params = "?action=getDetalleVenta";
        $http({
            method: 'POST',
            url: 'devolucion' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log(data);
            if (data.length > 0) {
                $scope.detalles = data;
            } else {
                swal("No se encontraron coincidencias");
            }
        });
    };

    $scope.getFoto = function (detalle) {
        const imageURL = "img/books/" + detalle.foto;
        swal({
            title: detalle.nombre + "|" + detalle.autor,
            icon: imageURL,
            text: "$  " + detalle.precio,
        })
    };

    $scope.devolucionLibro = function (detalle) {
        let params = "?action=devolucionLibro&monto=" + detalle.subtotal
                + "&idLibro=" + detalle.id_libro;
        $http({
            method: 'POST',
            url: 'devolucion' + params
        }).then((response, err) => {
            if (err) {
                return console.log(err);
            }
            let data = response.data;
            console.log(data);
            if (response.data == "") {
                swal("Ocurrio un ERROR al realizar la Devolucion", {
                    icon: "warning",
                });
            } else {
                swal(data, {
                    icon: "success",
                });
                $scope.mostrarSeccionDev = true;
                $scope.mostrarFormularioD = false;
                $scope.mostrarLibrosCompra = false;
            }
        });
    };
    /**
     * Modulo 2 version 3
     */

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
    $scope.evento = [];
    $scope.saldoSuficiente = false;
    $scope.eventoCompra = [];
    $scope.boletosAlanza = false;

    $scope.boletosEvento = function (evento) {
        console.log(evento);
        $scope.evento = evento;
    };

    $scope.calcularBoletos = function () {
        let cupo = Number($scope.evento.cupo);
        let boletos = Number($scope.eventoCompra.boletos);
        console.log(cupo);
        if (cupo >= boletos) {
            $scope.boletosAlanza = true;
            $scope.eventoCompra.total = Number($scope.evento.costo) * boletos;
        } else {
            $scope.boletosAlanza = false;
        }
    };

    $scope.tipopago = null;
    $scope.tarjeta = null;
    $scope.saldo = null;
    $scope.verificacionTarjeta = false;

    $scope.getSaldoTarjeta = function () {
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
                        if (data.saldo < $scope.eventoCompra.total) {
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

    /**
     * MODULO 2 V3.0
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
                    $scope.tarjeta = data.codigo_tarjeta;
                    if (data.saldo > 0) {
                        $scope.saldo = data.saldo;
                        $scope.end = true;
                        if (data.saldo < $scope.precio.totalCompra) {
                            swal({
                                title: "No cuenta con saldo suficiente para realizar la compra!",
                                text: "",
                                icon: "warning",
                                button: "Ok!"
                            });
                            $scope.verificacionTarjeta = false;
                        } else {
                            swal({
                                title: "No centa con saldo suficiente!",
                                text: "",
                                icon: "warning",
                                button: "Ok!"
                            });
                            $scope.verificacionTarjeta = true;
                        }
                    } else {
                        swal({
                            title: "No cuenta con saldo suficiente!",
                            text: "",
                            icon: "warning",
                            button: "Ok!"
                        });
                        $scope.verificacionTarjeta = false;
                    }
                }
                if (tipoPago === 'credito') {
                    $scope.tarjeta = data.codigo_tarjetacredito;
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

    $scope.comprarBoletos = function () {
        let params = "";
        if (Number($scope.eventoCompra.cantidadPagos) === 2) {
            params += "?action=createCompra&boletos=" + $scope.eventoCompra.boletos
                    + "&pagos=2&total=" + Number($scope.eventoCompra.total)
                    + "&restante=" + (Number($scope.eventoCompra.total) / 2)
                    + "&id_evento=" + $scope.evento.id_evento
                    + "&id_usuario=" + $scope.usuario.id_Usuario
                    + "&status=Pendiente";
        } else {
            params += "?action=createCompra&boletos=" + $scope.eventoCompra.boletos
                    + "&pagos=1&total=" + Number($scope.eventoCompra.total)
                    + "&restante=0&id_evento=" + $scope.evento.id_evento
                    + "&id_usuario=" + $scope.usuario.id_Usuario
                    + "&status=Pagado";
        }
        console.log(params);
        $http({
            method: 'POST',
            url: 'compra_boleto' + params
        }).then((response, err) => {
            swal({
                title: "Compra completa!",
                text: "Ve al apartado de tus eventos",
                icon: "success",
                button: "Aceptar"
            });
            $scope.evento = [];
            $scope.getMisEventos();
            $scope.getEventos();
            $scope.saldo;
            $scope.tipopago;
            $scope.tarjeta;
        });
    };

    /**
     * Mis eventos
     */

    $scope.miseventos = [];

    $scope.getMisEventos = function () {
        let params = "?action=getMisEventos&id_usuario=" + $scope.usuario.id_Usuario;
        $http({
            method: 'POST',
            url: 'evento' + params
        }).then((response, err) => {
            console.log("*** MIS EVENTOS ***");
            console.log(response.data);
            $scope.miseventos = response.data;
            $scope.eventoDeHoy(response.data);
        });
    };

    $scope.eventosHoy = [];

    $scope.eventoDeHoy = function (eventos) {

        eventos.forEach((evento, i) => {
            let diffDays = getDiffDays(getDate(), evento.fecha_evento);
            console.log('Day difference: ' + diffDays);
            if (Number(diffDays) === 0) {
                console.log("Evento hoy: " + i);
                $scope.eventosHoy[i] = evento;
            } else if (Number(diffDays) === 1) {
                sendRecordatorio(evento);
            }
        });
        console.log("Eventos de hoy");
        console.log($scope.eventosHoy);

        function sendRecordatorio(event) {
            let params = "?action=sendRecordatorio";
            params += "&evento=" + event.nombre;
            $http({
                method: 'POST',
                url: 'usuario' + params
            }).then((response, err) => {
                if (err) {
                    return console.log(err);
                }
                console.log(response.data);
            });
        }

        function getDate() {
            var x = new Date();
            var y = x.getFullYear().toString();
            var m = (x.getMonth() + 1).toString();
            var d = x.getDate().toString();
            if (m.length === 1) {
                m = '0' + m;
            }
            if (d.length === 1) {
                d = '0' + d;
            }
            var yyyymmdd = y + '-' + m + '-' + d;
            return yyyymmdd;
        }

        function getDiffDays(date1, date2) {
            var nowDate = new Date(date1);
            var dateReservation = new Date(date2);
            var diffDays = dateReservation.getDate() - nowDate.getDate();
            return diffDays;
        }
    };

    $scope.cancelarBoletosEvento = function (evento) {
        $scope.evento = evento;
    };

    /**
     * 
     * @param {type} evento
     * @returns {undefined}
     * 
     * tarjetasC
     * tarjetasP
     */

    $scope.cancelarCompra = function (evento) {
        let total = Number($scope.evento.cupo) * Number($scope.evento.costo);
        let medio = total / 2;

        let params = "?action=cancelarEvento"
                + "&id_usuario=" + $scope.usuario.id_Usuario
                + "&id_ventaBoleto=" + evento.id_evento
                + "&nombreEvento=" + evento.nombre
                + "&cantidadDevuelta=" + evento.cupo
                + "&totalDevuelto=" + medio;
        console.log(params);
        $http({
            method: 'POST',
            url: 'evento' + params
        }).then((response, err) => {
            console.log(response.data);
            $scope.getMisEventos();
            $scope.getTarjetaPrepago();
            $scope.getEventos();
            swal({
                title: "Cancelacion completa!",
                text: "Se ha devuelto un total de : " + medio + " a tu tarjeta Prepago",
                icon: "success",
                button: "Aceptar"
            });
        });
    };

});


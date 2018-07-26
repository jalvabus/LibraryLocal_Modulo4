/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global swal */

let app = angular.module('principalGerenteApp', []);

app.controller('principalGerenteController', ['$scope', '$http', '$rootScope', ($scope, $http, $rootScope) => {

        /**
         * Administracion de libros
         */
        $scope.usuario = [];
        $scope.mostrarLibros = true;
        $scope.mostrarFormulario = false;
        $scope.title = null;
        $scope.formVisivility = false;
        $scope.libros = [];
        $scope.libro = [];

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
        };

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
            console.log(params);
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

        $scope.saveBook = function () {
            let book = $scope.libro;
            let params = "?action=register&nombre=" + book.nombre
                    + "&autor=" + book.autor
                    + "&editorial=" + book.editorial
                    + "&precio=" + book.precio
                    + "&categoria=" + book.categoria
                    + "&anio=" + book.ano_publicacion
                    + "&desc=" + book.descripcion
                    + "&status=" + book.status
                    + "&cantidad=" + book.cantidad
                    + "&foto=" + book.foto;
            $http({
                method: 'POST',
                url: 'libro' + params
            }).then((response, err) => {
                if (err) {
                    return console.log(err);
                }
                swal(response.data);
                $scope.getLibros();
                $scope.libro = [];
            });
        };

        $scope.uploadFile = function (files) {
            $scope.libro.foto = files[0].name;
            console.log($scope.libro);
        };

        $scope.saveEditBook = function () {
            let book = $scope.libro;
            console.log(book);
            let params = "?action=update&nombre=" + book.nombre
                    + "&autor=" + book.autor
                    + "&editorial=" + book.editorial
                    + "&precio=" + book.precio
                    + "&categoria=" + book.categoria
                    + "&anio=" + book.ano_publicacion
                    + "&desc=" + book.descripcion
                    + "&status=" + book.status
                    + "&cantidad=" + book.cantidad
                    + "&foto=" + book.foto
                    + "&id=" + book.id_libro;
            $http({
                method: 'POST',
                url: 'libro' + params
            }).then((response, err) => {
                if (err) {
                    return console.log(err);
                }
                swal("Libro Actualizado !", "Actualizacion exitosa!", "success");
                $scope.getLibros();
                $scope.libro = [];
                $scope.mostrarLibros = true;
                $scope.mostrarFormulario = false;
            });
        };

        $scope.cancel = function () {
            $scope.libro = [];
            $scope.mostrarLibros = true;
            $scope.mostrarFormulario = false;
        };

        $scope.eliminar = function (book) {
            swal({
                title: "Eliminar libro " + book.nombre + "?",
                text: "Si borra este libro se perderan los ejemplares !",
                icon: "warning",
                buttons: true,
                dangerMode: true
            }).then((willDelete) => {
                if (willDelete) {
                    let params = "?action=eliminar&id=" + book.id_libro;
                    $http({
                        method: 'POST',
                        url: 'libro' + params
                    }).then((response, err) => {
                        console.log(response.data);
                        let data = response.data;
                        swal(data, {
                            icon: "success",
                        });
                        $scope.getLibros();
                    });
                } else {
                    swal("Libro no eliminado");
                }
            });
        };
        
        $scope.editar = function (editBook) {
            $scope.libro = editBook;
            $scope.mostrarLibros = false;
            $scope.mostrarFormulario = true;
            $scope.title = "Editar Libro";
            $scope.formVisivility = false;
        };

        $scope.showLibros = function () {
            $scope.mostrarLibros = true;
            $scope.mostrarFormulario = false;
        };

        $scope.showFormulario = function () {
            $scope.title = "Agregar Nuevo Libro";
            $scope.mostrarLibros = false;
            $scope.mostrarFormulario = true;
            $scope.libro = [];
            $scope.formVisivility = true;
        };

        $scope.validateLogin();
        $scope.getLibros();

        /**
         * Administracion de eventos
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
                console.log("Eventos");
                console.log(data);
                if (data.length > 0) {
                    $scope.eventos = data;
                }
            });
        };

        $scope.getEventos();

        $scope.mostrarEventos = true;
        $scope.mostrarFormularioEventos = false;
        $scope.titleEvento = null;
        $scope.formVisivilityEvento = false;
        $scope.evento = [];

        $scope.showEventos = function () {
            $scope.mostrarEventos = true;
            $scope.mostrarFormularioEventos = false;
        };

        $scope.showFormularioEventos = function () {
            $scope.titleEvento = "Agregar Nuevo Evento";
            $scope.mostrarEventos = false;
            $scope.mostrarFormularioEventos = true;
            $scope.evento = [];
            $scope.formVisivilityEvento = true;
        };

        $scope.eliminarEvento = function (evento) {
            swal({
                title: "Eliminar evento " + evento.nombre + "?",
                text: "Se eliminara el evento permanentemente !",
                icon: "warning",
                buttons: true,
                dangerMode: true
            }).then((willDelete) => {
                if (willDelete) {
                    let params = "?action=eliminar&id=" + evento.id_evento;
                    $http({
                        method: 'POST',
                        url: 'evento' + params
                    }).then((response, err) => {
                        console.log(response.data);
                        let data = response.data;
                        swal(data, {
                            icon: "success",
                        });
                        $scope.getLibros();
                    });
                } else {
                    swal("Evento no eliminado");
                }
            });
        };

        $scope.cancelEvento = function () {
            $scope.evento = [];
            $scope.mostrarEventos = true;
            $scope.mostrarFormularioEventos = false;
        };

        $scope.getEventosSearch = function () {
            let params = "?action=busqueda&nombre=" + $scope.searchEvento;
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

        $scope.uploadFileEvento = function (files) {
            $scope.evento.foto = files[0].name;
            console.log($scope.evento);
        };

        /**
         * Administracion de Premios
         * let infoPremio1 = premios[0].id_premio;
         * 
         * for (var i = 0; i < premios.lengh; i ++) {
         *      premios[i].nombe
         * 
         * };
         * 
         * 
         * premios.forEach((premio)=>{
         *  premio.id_premio;
         *  premio.idPremio;
         * })
         */
        
        $scope.premios = [];

        $scope.getPremios = function () {
            let params = "?action=getPremios";
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

        $scope.premio = [];

        $scope.savePremio = function () {
            var nombre = $("#nombre").val();
            var puntos = $("#puntos").val();
            var status = $("#status").val();
            var cantidad = $("#cantidad").val();
            var desc = $("#descripcion").val();
            var image = $("#image").val();
            if (nombre == "" || puntos == "" || status == "" || cantidad == "" || desc == "" || image == "") {

            } else if (puntos <= 0 || cantidad <= 0) {

            } else {
                let prize = $scope.premio;
                let params = "?action=register&nombre=" + prize.nombre
                        + "&puntos=" + prize.puntos
                        + "&status=" + prize.status
                        + "&cantidad=" + prize.cantidad
                        + "&descripcion=" + prize.descripcion
                        + "&fotopremio=" + prize.fotopremio;
                $http({
                    method: 'POST',
                    url: 'premio' + params
                }).then((response, err) => {
                    if (err) {
                        return console.log(err);
                    }
                    swal(response.data, {
                        icon: "success",
                    });
                    $scope.getPremios();
                    $scope.premio = [];
                });
            }
        };

        $scope.uploadFileP = function (files) {
            $scope.premio.fotopremio = files[0].name;
            console.log($scope.premio);
        };

        $scope.saveEditPremio = function () {
            var nombre = $("#nombre").val();
            var puntos = $("#puntos").val();
            var status = $("#status").val();
            var cantidad = $("#cantidad").val();
            var desc = $("#descripcion").val();
            if (nombre == "" || puntos == "" || status == "" || cantidad == "" || desc == "") {

            } else if (puntos <= 0 || cantidad <= 0) {

            } else {
                let prize = $scope.premio;
                console.log(prize);
                let params = "?action=update&nombre=" + prize.nombre
                        + "&puntos=" + prize.puntos
                        + "&status=" + prize.status
                        + "&cantidad=" + prize.cantidad
                        + "&descripcion=" + prize.descripcion
                        + "&fotopremio=" + prize.fotopremio
                        + "&id=" + prize.id_premio;
                console.log(params);
                $http({
                    method: 'POST',
                    url: 'premio' + params
                }).then((response, err) => {
                    if (err) {
                        return console.log(err);
                    }
                    swal(response.data, {
                        icon: "success",
                    });
                    $scope.getPremios();
                    $scope.premio = [];
                    $scope.mostrarPremios = true;
                    $scope.mostrarFormularioP = false;
                });
            }
        };

        $scope.cancelP = function () {
            $scope.premio = [];
            $scope.mostrarPremios = true;
            $scope.mostrarFormularioP = false;
        };

        $scope.cambiar = function (premio) {
            swal({
                title: "Cambiar Status del Premio " + premio.nombre + "?",
                text: "Se cambiara el status automaticamente!",
                icon: "warning",
                buttons: true,
                dangerMode: true
            }).then((willChange) => {
                if (willChange) {
                    let params = "?action=updateStatus&id=" + premio.id_premio
                            + "&status=" + premio.status;
                    $http({
                        method: 'POST',
                        url: 'premio' + params
                    }).then((response, err) => {
                        console.log(response.data);
                        let data = response.data;
                        swal(data, {
                            icon: "success",
                        });
                        $scope.getPremios();
                    });
                } else {
                    swal("Status no cambiado");
                }
            });
        };

        $scope.agregar = function (premio) {
            swal({
                title: "Ingresar mas unidades del Premio " + premio.nombre,
                text: "Premios en existencia: " + premio.cantidad,
                content: {
                    element: "input",
                    attributes: {
                        placeholder: "Cantidad de Premios",
                        type: "number",
                    },
                },
                icon: "info",
                buttons: true,
            }).then((value) => {
                if (value) {
                    var cant1 = parseInt(premio.cantidad);
                    var cant2 = parseInt(value);
                    var tot = cant1 + cant2;
                    swal({
                        title: "Agregar " + value + " unidades al premio ? ",
                        text: "La cantidad actualizada del premio serian: " + tot,
                        icon: "warning",
                        buttons: true,
                        dangerMode: true,
                    }).then((willUpdate) => {
                        if (willUpdate) {
                            let params = "?action=updateCantidad&id=" + premio.id_premio
                                    + "&cantidad=" + tot;
                            $http({
                                method: 'POST',
                                url: 'premio' + params
                            }).then((response, err) => {
                                console.log(response.data);
                                let data = response.data;
                                swal(data, {
                                    icon: "success",
                                });
                                $scope.getPremios();
                            });
                        } else {
                            swal("Cantidad no actualizada");
                        }
                    });
                } else {
                    swal.close();
                }

            });
        };

        $scope.getPremios();

        $scope.mostrarPremios = true;
        $scope.mostrarFormularioP = false;
        $scope.titleP = null;
        $scope.formVisivilityP = false;

        $scope.editarPremio = function (editPremio) {
            $scope.premio = editPremio;
            $scope.mostrarPremios = false;
            $scope.mostrarFormularioP = true;
            $scope.titleP = "Editar Premio";
            $scope.formVisivilityP = false;
        };

        $scope.showPremios = function () {
            $scope.mostrarPremios = true;
            $scope.mostrarFormularioP = false;
        };

        $scope.showFormularioP = function () {
            $scope.titleP = "Agregar Nuevo Premio";
            $scope.mostrarPremios = false;
            $scope.mostrarFormularioP = true;
            $scope.premio = [];
            $scope.formVisivilityP = true;
        };

        // Version 2 - Modulo 3

        $scope.saveEvento = function () {
            let event = $scope.evento;
            console.log(event);
            //var fecha = document.getElementById("fecha_evento").value;
            var fecha = $('#fecha_evento').val();

            let params = "?action=create&nombre=" + event.nombre
                    + "&calificacion=" + event.calificacion
                    + "&costo=" + event.costo
                    + "&cupo=" + event.cupo
                    + "&descripcion=" + event.descripcion
                    + "&fecha_evento=" + fecha
                    + "&foto=" + event.foto
                    + "&status=" + event.status
                    + "&tipo=" + event.tipo;
            console.log(params);

            $http({
                method: 'POST',
                url: 'evento' + params
            }).then((response, err) => {
                if (err) {
                    return console.log(err);
                }
                swal(response.data);
                $scope.getEventos();
                $scope.evento = [];
            });

        };

        function dateFormat(date) {
            var YYYY = date.getFullYear();
            var MM = date.getMonth() + 1;
            if (MM < 10) {
                MM = '0' + MM;
            }
            var DD = date.getDate();
            if (DD < 10) {
                DD = '0' + DD;
            }
            return YYYY + '-' + MM + '-' + DD;
        }

        $scope.saveEditEvento = function () {
            let event = $scope.evento;
            console.log(event);
            let params = "?action=update&nombre=" + event.nombre
                    + "&calificacion=" + event.calificacion
                    + "&costo=" + event.costo
                    + "&cupo=" + event.cupo
                    + "&descripcion=" + event.descripcion
                    + "&fecha_evento=" + event.fecha_evento
                    + "&foto=" + event.foto
                    + "&status=" + event.status
                    + "&tipo=" + event.tipo
                    + "&id_evento=" + event.id_evento;
            console.log(params);
            $http({
                method: 'POST',
                url: 'evento' + params
            }).then((response, err) => {
                if (err) {
                    return console.log(err);
                }
                swal(response.data);
                $scope.getEventos();
                $scope.evento = [];
            });
        };

        $scope.editarEvento = function (editEvento) {
            $scope.evento = editEvento;
            $('#fecha_evento').val(editEvento.fecha_evento);
            $scope.mostrarEventos = false;
            $scope.mostrarFormularioEventos = true;
            $scope.titleEvento = "Editar Evento";
            $scope.formVisivilityEvento = false;
        };

    }]);
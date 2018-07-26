<%-- 
    Document   : PrincipalUsuario
    Created on : 21-jun-2018, 20:57:42
    Author     : kitsu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="principalApp">
    <head>
        <title>Library-Local</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />

        <!-- AngularJS -->
        <script src="js/angular/angular.js"></script>
        <!-- SweetAlert -->
        <script src="js/sweetalert/sweetalert.js"></script>
        <!-- Iconos -->
        <link rel="stylesheet" href="font-awesome/css/font-awesome.css">

        <!-- Favicons -->
        <link href="img/favicon.png" rel="icon">
        <link href="img/apple-touch-icon.png" rel="apple-touch-icon">

        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,400i,600,700|Raleway:300,400,400i,500,500i,700,800,900" rel="stylesheet">

        <!-- Bootstrap CSS File -->
        <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Libraries CSS Files -->
        <link href="lib/nivo-slider/css/nivo-slider.css" rel="stylesheet">
        <link href="lib/owlcarousel/owl.carousel.css" rel="stylesheet">
        <link href="lib/owlcarousel/owl.transitions.css" rel="stylesheet">
        <link href="lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <link href="lib/venobox/venobox.css" rel="stylesheet">

        <!-- Nivo Slider Theme -->
        <link href="css/nivo-slider-theme.css" rel="stylesheet">

        <!-- Main Stylesheet File -->
        <link href="css/style.css" rel="stylesheet">

        <!-- Responsive Stylesheet File -->
        <link href="css/responsive.css" rel="stylesheet">

    </head>
    <body data-spy="scroll" data-target="#navbar-example" ng-controller="principalController">

        <div id="preloader"></div>

        <header>
            <!-- header-area start -->
            <div id="sticker" class="header-area">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <!-- Navigation -->
                            <nav class="navbar navbar-default">
                                <!-- Brand and toggle get grouped for better mobile display -->
                                <div class="navbar-header">
                                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".bs-example-navbar-collapse-1" aria-expanded="false">
                                        <span class="sr-only">Toggle navigation</span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                    </button>
                                    <!-- Brand -->
                                    <a class="navbar-brand page-scroll sticky-logo" href="index.html">
                                        <h1><span>L</span>ibraryLocal</h1>
                                        <!-- Uncomment below if you prefer to use an image logo -->
                                        <!-- <img src="img/logo.png" alt="" title=""> -->
                                    </a>
                                </div>
                                <!-- Collect the nav links, forms, and other content for toggling -->
                                <div class="collapse navbar-collapse main-menu bs-example-navbar-collapse-1" id="navbar-example">
                                    <ul class="nav navbar-nav navbar-right">
                                        <li class="active">
                                            <a class="page-scroll" href="miperfil.jsp">Mi Perfil</a>
                                        </li>
                                        <li>
                                            <a class="page-scroll" href="#portfolio">Libros</a>
                                        </li>
                                        <li>
                                            <a class="page-scroll" href="#services">Eventos</a>
                                        </li>
                                        <li>
                                            <a class="page-scroll" href="#team">Premios</a>
                                        </li>
                                        <li>
                                            <a class="page-scroll" href="wishlist.jsp">Mi wishlist</a>
                                        </li>
                                        <li>
                                            <a class="page-scroll" href="compras.jsp">Mis compras</a>
                                        </li>
                                        <li>
                                            <a class="page-scroll" href="carrito.jsp">Carrito</a>
                                        </li>
                                        <li>
                                            <a class="page-scroll" href="#" ng-click="logout();">Cerrar Sesion</a>
                                        </li>
                                    </ul>
                                </div>
                                <!-- navbar-collapse -->
                            </nav>
                            <!-- END: Navigation -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- header-area end -->
        </header>
        <!-- header end -->

        <!-- Start Slider Area -->
        <div id="home" class="about-area area-padding" style="margin-top: 30px;">
            <div class="bend niceties preview-2"> 
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="section-headline services-head text-center">
                            <h2>Bienvenido {{ usuario.nombre}}</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Todos los premios Area -->
        <div id="team" class="our-team-area area-padding">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="section-headline text-center">
                            <h2>Todos los Premios</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12" ng-hide="showDetailsP">
                        <div class="row container">
                            <div class="col-md-6 col-sm-6 col-xs-6">
                                <div class="single-blog-page">
                                    <!-- search option start -->

                                    <!-- search option end -->
                                </div>
                            </div>
                        </div>
                        <div class="team-top">
                            <div class="col-md-3 col-sm-3 col-xs-12" ng-repeat="premio in premios">
                                <div class="single-team-member">
                                    <div class="team-img">
                                        <a href="#" class="text-center">
                                            <img class="prize" src="img/prize/{{premio.fotopremio}}" height="250" alt="">
                                        </a>
                                        <div class="team-social-icon text-center">
                                            <ul>
                                                <li>
                                                    <a>
                                                        <i class="fa fa-eye" ng-click="showDetailsPrize(premio)"></i>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a ng-click="cambiarPremio(premio, usuario)">
                                                        <i class="fa fa-check-square-o"></i>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="team-content text-center">
                                        <h4>{{premio.nombre}}</h4>
                                        <p>{{premio.puntos}} Puntos</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 row" ng-hide="!showDetailsP">
                        <!-- single-well start-->
                        <div class="col-md-6 col-sm-6 col-xs-12 text-center">
                            <img src="img/prize/{{premio.fotopremio}}" width="250">
                        </div>

                        <!-- single-well end-->
                        <div class="col-md-6 col-sm-6 col-xs-12 text-left">
                            <div class="well-middle">
                                <div class="single-well">
                                    <a>
                                        <strong><h3>DETALLE DEL PREMIO</h3></strong
                                    </a>
                                    <strong><p>Nombre: {{premio.nombre}}</p></strong>
                                    <strong><p>Puntos: {{premio.puntos}}</p></strong>
                                    <strong><p>Status: {{premio.status}}</p></strong>
                                    <strong><p>Cantidad: {{premio.cantidad}}</p></strong>
                                    <strong><p>Descripcion del libro: {{premio.descripcion}}</p></strong>
                                </div>
                                <button ng-click="hideDetailsPrize();" class="sus-btn" style="margin-left: 0px;">
                                    Regresar 
                                </button>
                                <button ng-click="cambiarPremio(premio, usuario)" class="sus-btn">
                                    Canjear Premio
                                </button>
                            </div>                            
                        </div>
                        <!-- End col-->
                    </div>
                </div>
            </div>
        </div>
        <!-- Todos los premios Area -->


        <!-- Registrar ticket Area -->
        <div class="wellcome-area">
            <div class="well-bg">
                <div class="test-overly"></div>
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="wellcome-text">
                                <div class="well-text text-center">
                                    <h2>Registrar Ticket</h2>
                                    <p>
                                        Registra tu Ticket y obten puntos para conseguir premios <strong>GRATIS</strong>.
                                    </p>
                                    <div class="subs-feilds">
                                        <div class="suscribe-input">
                                            <input type="text" class="email form-control width-80" id="codigo" placeholder="Código de la compra">
                                            <button type="submit" class="add-btn width-20" ng-click="registrarTicket(usuario)">Registrar</button>
                                            <div id="msg_Submit" class="h3 text-center hidden"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Registrar ticket Area -->

        <!-- Start Mis Premios Area -->
        <div id="portfolio2" class="our-team-area area-padding">
            <div class="container">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="section-headline text-center">
                            <h2>Mis Premios</h2>
                        </div>
                    </div>
                </div>
                <div class="row text-center">
                    <div class="services-contents">
                        <%-- Start Left services --%>
                        <div class="col-md-4 col-sm-4 col-xs-12" ng-repeat="mypremio in mypremios">
                            <div class="about-move">
                                <div class="services-details">
                                    <div class="single-services">
                                        <img src="img/prize/{{ mypremio.fotopremio}}">
                                        <h4>{{ mypremio.nombre}}</h4>
                                        <p>
                                            {{ mypremio.descripcion}}
                                        </p>
                                    </div>
                                </div>
                                <%-- end about-details --%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- awesome-Mis Premios end -->

        <!-- Modal -->
        <div class="modal fade" id="modalCancelarEvento" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" style="text-align: center; padding-top: 10%;">
            <div class="modal-dialog" role="document" style="display: inline-block; vertical-align: middle; text-align: left; height: 100%;">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Cancelacion de Evento: <strong>"{{evento.nombre}}"</strong></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" style="margin: 20px;">
                        <div>
                            <div class="row">
                                <div clas="form-group">
                                    <h5 class="modal-title" id="">Fecha Evento: </h5>
                                    <input disabled name="" id="" ng-model="evento.fecha_evento" type="text" placeholder="" class="form-control">
                                </div>
                                <div clas="form-group">
                                    <h5 class="modal-title" id="">Cantidad de Boletos: </h5>
                                    <input disabled name="correo" ng-model="evento.cupo" id="usuario" type="text" placeholder="" class="form-control">
                                </div>
                                <div clas="form-group">
                                    <h5 class="modal-title" id="">Total: </h5>
                                    <input disabled name="pass" id="" value="{{evento.cupo * evento.costo}}" type="number" placeholder="" class="form-control">
                                </div>
                                <div clas="form-group">
                                    <h5 class="modal-title" id="">Se hara rembolzo de: </h5>
                                    <input disabled name="pass" id="" value="{{ (evento.cupo * evento.costo) / 2}}" type="number" placeholder="" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal" ng-click="cancelarCompra(evento)">Completar Cancelacion</button>
                    </div>
                </div>
            </div>
        </div>

        <%-- Devolucion de Libros Area
        <div>
            <div id="devolucion" class="reviews-area hidden-xs" ng-if="mostrarSeccionDev">
                <div class="work-us">
                    <div class="work-left-text">
                        <a href="#">
                            <img src="img/slider/library.jpeg" alt="">
                        </a>
                    </div>
                    <div class="work-right-text text-center">
                        <h2>Devoluci&oacute;n de Libros</h2>
                        <h5>Registra tu codigo de compra para proceder con la devoluci&oacute;n</h5>
                        <div class="subs-feilds">
                            <div class="suscribe-input">
                                <input type="text" class="email form-control width-80" id="codigoD" placeholder="Código de la compra">
                                <button type="submit" class="add-btn width-20" ng-click="consultarCodigo()">Buscar</button>
                                <div id="msg_Submit" class="h3 text-center hidden"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="about-area area-padding" ng-if="mostrarFormularioD">
                <div class="container">
                    <div class="row">
                        <div class="row">
                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="section-headline text-center">
                                    <h2>Devoluci&oacute;n de Libros</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="event-content head-team" >
                                <div>
                                    <form class="row">
                                        <div class="form-group col-lg-6">
                                            <h5>Tipo devoluci&oacute;n</h5>
                                            <select id="tipoDevolucion" class="form-control" required="required">
                                                <option value="">Selecciona una opci&oacute;n...</option>
                                                <option value="Defecto">Defecto</option>
                                                <option value="Equivocacion">Equivocacion</option>
                                            </select>                                                     
                                        </div>
                                        <div class="form-group col-lg-6">
                                            <textarea class="form-control" maxlength="250" id="motivo" rows="5" data-rule="required" placeholder="Motivo por el cual quiere devolucion" required="required"></textarea>
                                        </div>
                                        <div class="text-center col-lg-6">
                                            <button ng-click="devolucionLibros()" class="ready-btn page-scroll" >Continuar</button>
                                        </div>
                                        <div class="text-center col-lg-6">
                                            <button ng-click="cancelD()" class="ready-btn page-scroll">Cancelar</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container" ng-if="mostrarLibrosCompra">
                <div class="row">
                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="section-headline text-center">
                                <h2>Listado de Libros</h2>
                            </div>
                        </div>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane active">
                            <div class="tab-inner">
                                <div class="event-content head-team">
                                    <div class="row">
                                        <div class="col-md-6 col-sm-6 col-xs-6">
                                            <div class="single-blog-page">
                                                <!-- search option start -->

                                                <!-- search option end -->
                                            </div>
                                        </div>
                                    </div>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th scope="col">Codigo</th>
                                                <th scope="col">Nombre</th>
                                                <th scope="col">Autor</th>
                                                <th scope="col">Cantidad</th>
                                                <th scope="col">Precio</th>
                                                <th scope="col">Subtotal</th>
                                                <th scope="col">Opciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr ng-repeat="detalle in detalles">
                                                <th scope="row">{{ detalle.codigo_compra}}</th>
                                                <td>{{detalle.nombre}}</td>
                                                <td>{{detalle.autor}}</td>
                                                <td>{{detalle.cantidad}}</td>
                                                <td>{{detalle.precio}}</td>
                                                <td>{{detalle.subtotal}}</td>
                                                <td>
                                                    <button class="btn btn-default" ng-click="getFoto(detalle)"><i class="fa fa-eye" ></i></button>
                                                    <button class="btn btn-default" ng-click="devolucionLibro(detalle)"><i class="fa fa-cart-arrow-down"></i></button>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button ng-click="cancelD()" class="ready-btn page-scroll">Cancelar</button>
                </div>
            </div>
        </div>
        <%-- Devolucion de Libros Area --%>

        <!-- Tarjeta puntos area -->
        <div id="pricing" class="our-team-area area-padding">
            <div class="container" ng-if="mostrarTarjetas">
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="section-headline text-center">
                            <h2>Tarjetas Prepago</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-sm-4 col-xs-12" ng-repeat="tarjeta in tarjetas">
                        <div class="pri_table_list active">
                            <span class="saleon">No {{$index + 1}}</span>
                            <h3><i class="fa fa-credit-card"></i><br/> <span>$ {{tarjeta.costo}}</span></h3>
                            <ol>
                                <li class="check">N&uacute;mero de Tarjeta:  {{tarjeta.noTarjeta}}</li>
                                <li class="check">Vigencia:  {{tarjeta.vigencia}}</li>
                            </ol>
                            <button ng-click="comprarTarjeta(usuario, tarjeta)">Comprar</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container" ng-if="mostrarTarjetas2">
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="section-headline text-center">
                            <h2>Recargar Saldo</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 col-sm-6 col-xs-12" ng-repeat="tarjetaP in tarjetasP">
                        <div class="pri_table_list active">
                            <span class="saleon">No {{$index + 1}}</span>
                            <h3><i class="fa fa-credit-card"></i><br/> <span>Tarjeta de Prepago</span></h3>
                            <ol>
                                <li class="check">N&uacute;mero de Tarjeta:  {{tarjetaP.codigo_tarjeta}}</li>
                                <li class="check">Estado:  {{tarjetaP.estado}}</li>
                                <li class="check">Saldo:  $ {{tarjetaP.saldo}}</li>
                            </ol>
                            <button ng-click="recargarSaldoYo(usuario, tarjetaP)">Recargar Saldo</button>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 col-xs-12" ng-repeat="tarjetaC in tarjetasC">
                        <div class="pri_table_list active">
                            <span class="saleon">No {{$index + 1}}</span>
                            <h3><i class="fa fa-credit-card"></i><br/> <span>Tarjeta de Credito</span></h3>
                            <ol>
                                <li class="check">N&uacute;mero de Tarjeta:  {{tarjetaC.codigo_tarjetacredito}}</li>
                                <li class="check">Estado:  {{tarjetaC.estado}}</li>
                                <li class="check">Saldo:  $ {{tarjetaC.saldo}}</li>
                            </ol>
                            <button ng-click="recargarSaldoRegalo(usuario, tarjetaC)">Recargar Saldo</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Tarjeta puntos area -->

        <!-- Start Footer bottom Area -->
        <footer>
            <div class="footer-area">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4 col-sm-4 col-xs-12">
                            <div class="footer-content">
                                <div class="footer-head">
                                    <div class="footer-logo">
                                        <h2><span>L</span>ibraryLocal</h2>
                                    </div>
                                    <ul ng-if="usuario">
                                        <li ng-if="usuario.tipo === 'Admin'">
                                            <a href="PrincipalGerente.jsp">
                                                <i class="fa fa-check"></i> GestionarEventos
                                            </a>
                                        </li>
                                        <li ng-if="usuario.tipo === 'Admin'">
                                            <a href="PrincipalGerente.jsp">
                                                <i class="fa fa-check"></i> GestionarLibros 
                                            </a>
                                        </li>
                                        <li ng-if="usuario.tipo === 'Admin'">
                                            <a href="tarjeta.jsp">
                                                <i class="fa fa-check"></i> SubirTXT Tarjetas 
                                            </a>
                                        </li>
                                        <li ng-if="usuario.tipo === 'Cliente'">
                                            <a href="pagoBoletos.jsp">
                                                <i class="fa fa-check"></i> Pagar Boleto 
                                            </a>
                                        </li>
                                        <li ng-if="usuario.tipo === 'Cliente'">
                                            <a href="PrincipalGerente.jsp">
                                                <i class="fa fa-check"></i> Devolucion 
                                            </a>
                                        </li>
                                        <li ng-if="usuario.tipo === 'Cliente'">
                                            <a href="wishlist.jsp">
                                                <i class="fa fa-check"></i> MiWishList 
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- end single footer -->
                        <div class="col-md-4 col-sm-4 col-xs-12">
                            <div class="footer-content">
                                <div class="footer-head">
                                    <h4>Acerca de Nosotros</h4>
                                    <p>
                                        <strong>CodeLabs</strong> es una empresa que se dedica al desarrollo de sotware ajustado a tu medida
                                        Puedes localizarnos llamando o escribiendo a:
                                    </p>
                                    <div class="footer-contacts">
                                        <p><span>Tel:</span> +52 55 16 36 56 15</p>
                                        <p><span>Email:</span> codelabsoftware@example.com</p>
                                        <p><span>Horas de trabajo:</span> 9am-5pm</p>
                                    </div>
                                    <p>O en nuestras redees sociales</p>
                                    <div class="footer-icons">
                                        <ul>
                                            <li>
                                                <a href="http://www.facebook.com/CodeLabs-1901295480009037/" target="_blank"><i class="fa fa-facebook"></i></a>
                                            </li>
                                            <li>
                                                <a href="#"><i class="fa fa-twitter"></i></a>
                                            </li>
                                            <li>
                                                <a href="#"><i class="fa fa-google"></i></a>
                                            </li>
                                            <li>
                                                <a href="#"><i class="fa fa-pinterest"></i></a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- end single footer -->
                        <div class="col-md-4 col-sm-4 col-xs-12">
                            <div class="footer-content">
                                <div class="footer-head">
                                    <h4>Fotos</h4>
                                    <div class="flicker-img">
                                        <a href="#"><img src="img/books/01.png" alt=""></a>
                                        <a href="#"><img src="img/books/02.png" alt=""></a>
                                        <a href="#"><img src="img/books/03.png" alt=""></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="footer-area-bottom">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="copyright text-center">
                                <p>
                                    &copy; Copyright <strong>CodeLabs</strong>. All Rights Reserved
                                </p>
                            </div>
                            <div class="credits">
                                <!--
                                  All the links in the footer should remain intact.
                                  You can delete the links only if you purchased the pro version.
                                  Licensing information: https://bootstrapmade.com/license/
                                  Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/buy/?theme=eBusiness
                                -->
                                Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a> editedBy <strong>CodeLabs</strong>.
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </footer>


        <a href="#" class="back-to-top"><i class="fa fa-chevron-up"></i></a>

        <!-- JavaScript Libraries -->
        <script src="lib/jquery/jquery.min.js"></script>
        <script src="lib/bootstrap/js/bootstrap.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="lib/venobox/venobox.min.js"></script>
        <script src="lib/knob/jquery.knob.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/parallax/parallax.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/nivo-slider/js/jquery.nivo.slider.js" type="text/javascript"></script>
        <script src="lib/appear/jquery.appear.js"></script>
        <script src="lib/isotope/isotope.pkgd.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD8HeI8o-c1NppZA-92oYlXakhDPYR7XMY"></script>

        <!-- Contact Form JavaScript File -->
        <script src="contactform/contactform.js"></script>

        <script src="js/main.js"></script>
        <script src="js/shortcuts.js"></script>
        <script src="js/views/principalUsuario.js"></script>
    </body>
</html>

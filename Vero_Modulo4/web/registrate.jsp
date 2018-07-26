<%-- 
    Document   : index
    Created on : 19-jun-2018, 19:24:02
    Author     : kitsu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="appRegistrate" ng-controller="ctrlRegistrate">
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
    <body data-spy="scroll" data-target="#navbar-example">

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
                                    <a ng-if="usuario.tipo === 'Cliente'" class="navbar-brand page-scroll sticky-logo" href="index.jsp">
                                        <h1><span>L</span>ibraryLocal</h1>
                                    </a>
                                    <a ng-if="usuario.tipo === 'Admin'" class="navbar-brand page-scroll sticky-logo" href="index.jsp">
                                        <h1><span>L</span>ibraryLocal</h1>
                                    </a>
                                </div>
                                <!-- Collect the nav links, forms, and other content for toggling -->
                                <div class="collapse navbar-collapse main-menu bs-example-navbar-collapse-1" id="navbar-example">
                                    <ul class="nav navbar-nav navbar-right">
                                        <li>
                                            <a class="page-scroll" href="index.jsp">Regresar</a>
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

        <!-- Start About area -->
        <div id="about" class="about-area area-padding" style="">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="section-headline text-center">
                            <h2>Registrate</h2>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <h5>Correo</h5>
                            <input type="text" class="form-control" placeholder="ejemplo@ejemplo.com" ng-model="correo"/>
                        </div>
                        <div class="form-group">
                            <h5>Contraseña</h5>
                            <input type="password" class="form-control" ng-model="password"/>
                        </div>
                        <div class="form-group">
                            <h5>Nombre</h5>
                            <input type="text" class="form-control" ng-model="nombre"/>
                        </div>

                        <div class="col-md-6" style="padding-left: 0">
                            <div class="form-group">
                                <h5>Apellido Paterno</h5>
                                <input type="text" class="form-control" ng-model="apaterno"/> 
                            </div>
                        </div>
                        <div class="col-md-6" style="padding-right: 0" >
                            <div class="form-group">
                                <h5>Apellido Materno</h5>
                                <input type="text" class="form-control" ng-model="amaterno"/>
                            </div>
                        </div>
                        <div class="col-md-6" style="padding-left: 0">
                            <div class="form-group">
                                <h5>Edad</h5>
                                <input type="number" min="10" max="90" value="10" class="form-control" ng-model="edad"/>
                            </div>
                        </div>
                        <div class="col-md-6" style="padding-right: 0">
                            <div class="form-group">
                                <h5>Sexo</h5>
                                <select class="form-control" ng-model="sexo">
                                    <option value="M">M</option>
                                    <option value="F">F</option>
                                </select>
                            </div>
                        </div>
                         <div class="form-group">
                            <h5>No Tarjeta de Credito</h5>
                            <input type="number" class="form-control" ng-model="credito"/>
                        </div>
                    </div>   
                    <div class="col-md-6">
                        <div class="form-group">
                            <h5>Telefono</h5>
                            <input type="number" class="form-control" ng-model="telefono"/>
                        </div>
                        <div class="form-group">
                            <h5>Calle</h5>
                            <input type="text" class="form-control" ng-model="calle"/>
                        </div>
                        <div class="form-group">
                            <h5>Colonia</h5>
                            <input type="text" class="form-control" ng-model="colonia"/>
                        </div>
                        <div class="form-group">
                            <h5>Municipio</h5>
                            <input type="text" class="form-control" ng-model="municipio"/>
                        </div>
                        <div class="form-group">
                            <h5>Estado</h5>
                            <input type="text" class="form-control" ng-model="estado"/>
                        </div>
                        <button class="btn btn-secondary btn-block btn-lg" ng-disabled="!correo || !password || !nombre || !apaterno || !amaterno || !edad || !sexo
                                            || !telefono || !calle || !colonia || !municipio || !estado || !credito || credito.length < 16" ng-click="create();"> Crear una cuenta</button>
                    </div>

                </div>


            </div>
        </div>
        <!-- End About area -->


        <!-- Start Footer bottom Area -->
        <footer>
            <div class="footer-area">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4 col-sm-4 col-xs-12">
                            <div class="footer-content">
                                <div class="footer-head">
                                    <div class="footer-logo">
                                        <h2><span>e</span>Business</h2>
                                    </div>

                                    <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis.</p>
                                    <div class="footer-icons">
                                        <ul>
                                            <li>
                                                <a href="#"><i class="fa fa-facebook"></i></a>
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
                                    <h4>information</h4>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.
                                    </p>
                                    <div class="footer-contacts">
                                        <p><span>Tel:</span> +123 456 789</p>
                                        <p><span>Email:</span> contact@example.com</p>
                                        <p><span>Working Hours:</span> 9am-5pm</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- end single footer -->
                        <div class="col-md-4 col-sm-4 col-xs-12">
                            <div class="footer-content">
                                <div class="footer-head">
                                    <h4>Instagram</h4>
                                    <div class="flicker-img">
                                        <a href="#"><img src="img/portfolio/1.jpg" alt=""></a>
                                        <a href="#"><img src="img/portfolio/2.jpg" alt=""></a>
                                        <a href="#"><img src="img/portfolio/3.jpg" alt=""></a>
                                        <a href="#"><img src="img/portfolio/4.jpg" alt=""></a>
                                        <a href="#"><img src="img/portfolio/5.jpg" alt=""></a>
                                        <a href="#"><img src="img/portfolio/6.jpg" alt=""></a>
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
                                    &copy; Copyright <strong>eBusiness</strong>. All Rights Reserved
                                </p>
                            </div>
                            <div class="credits">
                                <!--
                                  All the links in the footer should remain intact.
                                  You can delete the links only if you purchased the pro version.
                                  Licensing information: https://bootstrapmade.com/license/
                                  Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/buy/?theme=eBusiness
                                -->
                                Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
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
        <script src="js/modulo3/registrate.js" type="text/javascript"></script>
        <script
    </body>

</html>

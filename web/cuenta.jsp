<%-- 
    Document   : index
    Created on : 22/05/2014, 09:14:33 AM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.iveloper.entidades.Cuenta" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="Alex">
        <link rel="shortcut icon" href="img/favicon.png">

        <title>InvoiceHUB - Portal</title>



        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-reset.css" rel="stylesheet">
        <!--external css-->
        <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />

        <!-- Custom styles for this template -->
        <link href="css/style.css" rel="stylesheet">
        <link href="css/style-responsive.css" rel="stylesheet" />

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
        <!--[if lt IE 9]>
          <script src="js/html5shiv.js"></script>
          <script src="js/respond.min.js"></script>
        <![endif]-->       

    </head>

    <body>
        <% Cuenta cuentaSesion = (Cuenta) session.getAttribute("cuentaSesion"); %>
        <% if (cuentaSesion != null) { %>       
        <section id="container" class="">
            <!--header start-->
            <header class="header white-bg">
                <div class="sidebar-toggle-box">
                    <div data-original-title="Toggle Navigation" data-placement="right" class="fa fa-bars tooltips"></div>
                </div>
                <!--logo start-->
                <a href="index.jsp" class="logo" >INVOICE<span>HUB</span></a>
                <!--logo end-->

                <div class="top-nav ">
                    <ul class="nav pull-right top-menu">
                        <!-- user login dropdown start-->
                        <li class="dropdown">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="username">usuario</span>
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu extended logout">
                                <div class="log-arrow-up"></div>
                                <li><a href="CuentaCtrl?op=cerrar_sesion"><i class="fa fa-key"></i> Cerrar sesión</a></li>
                            </ul>
                        </li>
                        <!-- user login dropdown end -->
                    </ul>
                </div>
            </header>
            <!--header end-->
            <!--sidebar start-->
            <aside>
                <div id="sidebar"  class="nav-collapse ">
                    <!-- sidebar menu start-->
                    <ul class="sidebar-menu" id="nav-accordion">                        
                        <li class="sub-menu">
                            <a href="javascript:;">
                                <i class="fa fa-tasks"></i>
                                <span>Documentos Electrónicos</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="index.jsp">Facturas</a></li>                                                                
                            </ul>
                        </li>
                        <% if (cuentaSesion.getRoles().contains("usuario")) {%>
                        <li class="sub-menu">
                            <a href="javascript:;" class="active">
                                <i class=" fa fa-cogs"></i>
                                <span>Preferencias</span>
                            </a>
                            <ul class="sub">
                                <li class="active"><a href="cuenta.jsp">Cuenta</a></li>
                            </ul>
                        </li>
                        <%}%>  
                        <% if (cuentaSesion.getRoles().contains("admin")) {%>
                        <li class="sub-menu">
                            <a href="javascript:;" >
                                <i class=" fa fa-cogs"></i>
                                <span>Configuración</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="basedatos.jsp">Servidor</a></li>
                                <li><a  href="usuarios.jsp">Usuarios</a></li>
                                <li><a  href="#">Servicios</a></li>
                            </ul>
                        </li>
                        <%}%>                        
                        <% if (cuentaSesion.getRoles().contains("admin") || cuentaSesion.getRoles().contains("query")) {%>
                        <li class="sub-menu">
                            <a href="javascript:;" >
                                <i class=" fa fa-cogs"></i>
                                <span>Documentos</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="consultar_documentos.jsp">Consultar Documentos</a></li>
                                    <% if (cuentaSesion.getRoles().contains("admin")) {%>
                                <li><a  href="documentos_anulados.jsp">Documentos Anulados</a></li>
                                    <%}%>                                                                                           
                            </ul>
                        </li>
                        <%}%>
                    </ul>
                    <!-- sidebar menu end-->
                </div>
            </aside>
            <!--sidebar end-->
            <!--main content start-->
            <section id="main-content">
                <section class="wrapper">
                    <!-- page start-->
                    <!--multiple select start-->
                    <div class="row">
                        <div class="col-md-6">
                            <section class="panel col-md-12">
                                <header class="panel-heading">
                                    Preferencias de cuenta - Cambios
                                </header>
                                <div id="ModificarCuenta" name="ModificarCuenta">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label for="clave">Clave</label>
                                            <div>
                                                <input id="clave" name="clave" class="form-control" type="password" placeholder="clave" >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="mail">Correo</label>
                                            <div>
                                                <input id="mail" name="mail" class="form-control" type="text" placeholder="correo" >
                                            </div>
                                        </div>                                                                        
                                        <button type="button" class="btn btn-info" onclick="modificarCuenta()">Guardar</button>
                                        <label id="resultado"></label>
                                    </div> 
                                </div>
                            </section>                            
                        </div>                        
                        <div class="col-md-6">
                            <section class="panel col-md-12">
                                <header class="panel-heading">
                                    Información General
                                </header>
                                <div id="ModificarCuenta" name="ModificarCuenta">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Cliente</label>
                                            <span id="nombre_cliente">
                                                <%= cuentaSesion.getDetallesCuenta().getNombre_cliente() %>
                                            </span>
                                        </div>
                                        <div class="form-group">
                                            <label>Identificación</label>
                                            <span id="idcliente">
                                                <%= cuentaSesion.getDetallesCuenta().getIdcliente()%>
                                            </span>
                                        </div>                                        
                                        <div class="form-group">
                                            <label>Correo</label>
                                            <span id="mail">
                                                <%= cuentaSesion.getMail() %>
                                            </span>
                                        </div>                                        
                                        <div class="form-group">
                                            <label>Fecha de creación</label>
                                            <span id="fechacreacion">
                                                <%= cuentaSesion.getFechacreacion() %>
                                            </span>
                                        </div>                                        
                                        <div class="form-group">
                                            <label>Último Acceso</label>
                                            <span id="ultimoacceso">
                                                <%= cuentaSesion.getUltimoacceso()%>
                                            </span>
                                        </div>                                        
                                    </div> 
                                </div>
                            </section>                            
                        </div>                        
                    </div>
                    <!--multiple select end-->

                    <!-- page end-->
                </section>
            </section>
            <!--main content end-->
            <!--footer start-->
            <footer class="site-footer">
                <div class="text-center">
                    2014 &copy; notariaApp by Iveloper.
                    <a href="#" class="go-top">
                        <i class="fa fa-angle-up"></i>
                    </a>
                </div>
            </footer>
            <!--footer end-->
        </section>

        <!-- js placed at the end of the document so the pages load faster -->
        <!--<script src="js/jquery.js"></script>-->
        <script type="text/javascript" language="javascript" src="assets/advanced-datatable/media/js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script class="include" type="text/javascript" src="js/jquery.dcjqaccordion.2.7.js"></script>
        <script src="js/jquery.scrollTo.min.js"></script>
        <script src="js/jquery.nicescroll.js" type="text/javascript"></script>
        <script src="js/respond.min.js" ></script>
     

        <!--this page plugins-->
        

        <!--common script for all pages-->
        <script src="js/common-scripts.js"></script>
        <!--this page  script only-->
        
        <script src="js/ajax.js"></script>
        <script src="js/usuario_sesion.js"></script>
        <script src="js/cuenta_preferencias.js"></script>
        <%}%>
    </body>
</html>
